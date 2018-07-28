package de.htwsaar.tictactoe.connection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

import de.htwsaar.tictactoe.CommandFactory;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePackException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.core.buffer.ArrayBufferInput;
import org.zeromq.ZContext;
import org.zeromq.ZMQException;
import org.zeromq.ZMQ.Socket;

public class ServerConnection<C> implements AutoCloseable {
    private final ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
    private final ArrayBufferInput inputBuffer = new ArrayBufferInput(new byte[0]);
    private final ZContext context;
    private final Socket socket;
    private final MessagePacker packer;
    private final MessageUnpacker unpacker;
    private final CommandFactory<? extends C> commandFactory;
    private boolean closed;

    public ServerConnection(int port, int timeout, CommandFactory<? extends C> commandFactory) {
        if (port <= 1023) {
            throw new IllegalArgumentException("Portnummer zu niedrig! (Siehe https://de.wikipedia.org/wiki/Transmission_Control_Protocol#Allgemeines)");
        } else if (port >= 65535) {
            throw new IllegalArgumentException("Portnummer zu groß! (Siehe https://de.wikipedia.org/wiki/Transmission_Control_Protocol#Allgemeines");
        } else {
            this.commandFactory = (CommandFactory)Objects.requireNonNull(commandFactory);
            this.unpacker = MessagePack.newDefaultUnpacker(this.inputBuffer);
            this.packer = MessagePack.newDefaultPacker(this.outputBuffer);
            this.context = new ZContext();
            this.socket = this.context.createSocket(6);
            this.socket.setReceiveTimeOut(timeout);
            this.socket.setRouterMandatory(true);
            this.socket.bind(String.format("tcp://*:%d", port));
        }
    }

    public void close() {
        if (!this.closed) {
            this.context.destroy();
            this.closed = true;
        }

    }

    private static int commId(byte[] identity) {
        if (identity.length != 5) {
            throw new IllegalArgumentException("Wrong ZMQ Identity!");
        } else {
            ByteBuffer buffer = ByteBuffer.wrap(identity, 1, 4);
            return buffer.getInt();
        }
    }

    private static byte[] zmqId(int value) {
        return new byte[]{0, (byte)(value >>> 24), (byte)(value >>> 16), (byte)(value >>> 8), (byte)value};
    }

    public final C nextCommand() {
        byte[] identity;
        try {
            identity = this.socket.recv();
        } catch (ZMQException var8) {
            if (var8.getErrorCode() == 65) {
                throw new CommException("Die andere Seite der Verbindung ist bereits geschlossen!", var8);
            }

            throw new CommException(String.format("Serverseitiger Commlibfehler %d! Bitte wenden Sie sich an Ihren Tutor!", var8.getErrorCode()), var8);
        }

        if (identity == null) {
            throw new TimeoutException();
        } else {
            byte[] data = this.socket.recv();
            this.inputBuffer.reset(data);

            try {
                int type = this.unpacker.unpackInt();
                String cry;
                int y;
                int x;
                Direction direction;
                switch(type) {
                    case 0:
                        cry = this.unpacker.unpackString();
                        MonsterType monsterType = MonsterType.valueOf(this.unpacker.unpackString());
                        String teamName = this.unpacker.unpackString();
                        return this.commandFactory.createRegister(commId(identity), cry, monsterType, teamName);
                    case 1:
                        return this.commandFactory.createWatch(commId(identity));
                    case 2:
                        direction = Direction.valueOf(this.unpacker.unpackString());
                        return this.commandFactory.createMove(commId(identity), direction);
                    case 3:
                        direction = Direction.valueOf(this.unpacker.unpackString());
                        return this.commandFactory.createStab(commId(identity), direction);
                    case 16:
                        cry = this.unpacker.unpackString();
                        return this.commandFactory.createWarCry(commId(identity), cry);
                    case 17:
                        return this.commandFactory.createDoneActing(commId(identity));
                    default:
                        throw new CommException("Unbekannter Commandtyp!");
                }
            } catch (MessagePackException | IOException var7) {
                throw new CommException("Fehler beim Lesen des nächsten Commands!", var7);
            }
        }
    }

    public final void sendRegistered(int commId, int playerId, String name) {
        try {
            this.packer.packInt(0);
            this.packer.packInt(playerId);
            this.packer.packString(name);
            this.packer.flush();
            this.socket.sendMore(zmqId(commId));
            this.socket.send(this.outputBuffer.toByteArray());
        } catch (MessagePackException | IOException var14) {
            throw new CommException("Ein 'Registered' Event konnte nicht ins Wire-Format übersetzt werden!", var14);
        } catch (ZMQException var15) {
            if (var15.getErrorCode() == 65) {
                throw new CommException("Die andere Seite der Verbindung ist bereits geschlossen!", var15);
            }

            throw new CommException(String.format("Serverseitiger Commlibfehler %d! Bitte wenden Sie sich an Ihren Tutor!", var15.getErrorCode()), var15);
        } finally {
            this.outputBuffer.reset();
        }

    }

    public final void sendRegistrationAborted(int commId) {
        try {
            this.packer.packInt(1);
            this.packer.flush();
            this.socket.sendMore(zmqId(commId));
            this.socket.send(this.outputBuffer.toByteArray());
        } catch (MessagePackException | IOException var7) {
            throw new CommException("Ein 'RegistrationAborted' Event konnte nicht ins Wire-Format übersetzt werden!", var7);
        } catch (ZMQException var8) {
            if (var8.getErrorCode() == 65) {
                throw new CommException("Die andere Seite der Verbindung ist bereits geschlossen!", var8);
            }

            throw new CommException(String.format("Serverseitiger Commlibfehler %d!", var8.getErrorCode()), var8);
        } finally {
            this.outputBuffer.reset();
        }

    }

    public final void sendMoved(int commId, int actorId, Direction direction) {
        try {
            this.packer.packInt(2);
            this.packer.packInt(actorId);
            this.packer.packString(direction.name());
            this.packer.flush();
            this.socket.sendMore(zmqId(commId));
            this.socket.send(this.outputBuffer.toByteArray());
        } catch (MessagePackException | IOException var9) {
            throw new CommException("Ein 'Moved' Event konnte nicht ins Wire-Format übersetzt werden!", var9);
        } catch (ZMQException var10) {
            if (var10.getErrorCode() == 65) {
                throw new CommException("Die andere Seite der Verbindung ist bereits geschlossen!", var10);
            }

            throw new CommException(String.format("Serverseitiger Commlibfehler %d! Bitte wenden Sie sich an Ihren Tutor!", var10.getErrorCode()), var10);
        } finally {
            this.outputBuffer.reset();
        }

    }

    public final void sendActNow(int commId, int monsterId) {
        try {
            this.packer.packInt(21);
            this.packer.packInt(monsterId);
            this.packer.flush();
            this.socket.sendMore(zmqId(commId));
            this.socket.send(this.outputBuffer.toByteArray());
        } catch (MessagePackException | IOException var8) {
            throw new CommException("Ein 'ActNow' Event konnte nicht ins Wire-Format übersetzt werden!", var8);
        } catch (ZMQException var9) {
            if (var9.getErrorCode() == 65) {
                throw new CommException("Die andere Seite der Verbindung ist bereits geschlossen!", var9);
            }

            throw new CommException(String.format("Serverseitiger Commlibfehler %d! Bitte wenden Sie sich an Ihren Tutor!", var9.getErrorCode()), var9);
        } finally {
            this.outputBuffer.reset();
        }

    }


    public final void sendDoneActing(int commId, int actorId) {
        try {
            this.packer.packInt(24);
            this.packer.packInt(actorId);
            this.packer.flush();
            this.socket.sendMore(zmqId(commId));
            this.socket.send(this.outputBuffer.toByteArray());
        } catch (MessagePackException | IOException var8) {
            throw new CommException("Ein 'DoneActing' Event konnte nicht ins Wire-Format übersetzt werden!", var8);
        } catch (ZMQException var9) {
            if (var9.getErrorCode() == 65) {
                throw new CommException("Die andere Seite der Verbindung ist bereits geschlossen!", var9);
            }

            throw new CommException(String.format("Serverseitiger Commlibfehler %d! Bitte wenden Sie sich an Ihren Tutor!", var9.getErrorCode()), var9);
        } finally {
            this.outputBuffer.reset();
        }

    }

    public final void sendWinner(int commId, String teamName) {
        try {
            this.packer.packInt(29);
            this.packer.packString(teamName);
            this.packer.flush();
            this.socket.sendMore(zmqId(commId));
            this.socket.send(this.outputBuffer.toByteArray());
        } catch (MessagePackException | IOException var8) {
            throw new CommException("Ein 'Winner' Event konnte nicht ins Wire-Format übersetzt werden!", var8);
        } catch (ZMQException var9) {
            if (var9.getErrorCode() == 65) {
                throw new CommException("Die andere Seite der Verbindung ist bereits geschlossen!", var9);
            }

            throw new CommException(String.format("Serverseitiger Commlibfehler %d! Bitte wenden Sie sich an Ihren Tutor!", var9.getErrorCode()), var9);
        } finally {
            this.outputBuffer.reset();
        }

    }

    static final class Commands {
        static final int REGISTER = 0;
        static final int WATCH = 1;
        static final int MOVE = 2;
        static final int STAB = 3;
        static final int SLASH = 4;
        static final int CUT = 5;
        static final int BITE = 6;
        static final int STARE = 7;
        static final int CLAW = 8;
        static final int CRUSH = 9;
        static final int SINGE = 10;
        static final int SHOOT = 11;
        static final int BURN = 12;
        static final int CAST = 13;
        static final int BLINK = 14;
        static final int SWAP = 15;
        static final int WARCRY = 16;
        static final int DONEACTING = 17;

        private Commands() {
        }
    }
}
