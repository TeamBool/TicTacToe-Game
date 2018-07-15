package de.htwsaar.tictactoe.connection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import de.htwsaar.tictactoe.EventFactory;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePackException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.core.buffer.ArrayBufferInput;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQException;
import org.zeromq.ZMQ.Socket;

public class ClientConnection<E> implements AutoCloseable {

    private final ZContext context;
    private final ZMQ.Socket socket;
    private final ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
    private final ArrayBufferInput inputBuffer = new ArrayBufferInput(new byte[0]);
    private final MessagePacker packer;
    private final MessageUnpacker unpacker;
    private final EventFactory<? extends E> eventFactory;
    private boolean closed;

    public ClientConnection(String host, int port, EventFactory<? extends E> eventFactory) {
        Objects.requireNonNull(host);
        if (port <= 1023) {
            throw new IllegalArgumentException("Portnummer zu niedrig! (Siehe https://de.wikipedia.org/wiki/Transmission_Control_Protocol#Allgemeines)");
        } else if (port >= 65535) {
            throw new IllegalArgumentException("Portnummer zu groß! (Siehe https://de.wikipedia.org/wiki/Transmission_Control_Protocol#Allgemeines");
        } else {
            this.eventFactory = (EventFactory) Objects.requireNonNull(eventFactory);
            this.unpacker = MessagePack.newDefaultUnpacker(this.inputBuffer);
            this.packer = MessagePack.newDefaultPacker(this.outputBuffer);
            this.context = new ZContext();
            this.socket = this.context.createSocket(5);
            this.socket.setReceiveTimeOut(-1); //TODO check if works?
            this.socket.connect(String.format("tcp://%s:%d", host, port));
        }
    }

    public void close() {
        if (!this.closed) {
            this.context.destroy();
            this.closed = true;
        }

    }

    public final E nextEvent() {
        byte[] data;
        try {
            data = this.socket.recv();
        } catch (ZMQException var11) {
            if (var11.getErrorCode() == 65) {
                throw new CommException("Die andere Seite der Verbindung ist bereits geschlossen!", var11);
            }

            throw new CommException(String.format("Clientseitiger Commlibfehler %d!", var11.getErrorCode()), var11);
        }

        if (data == null) {
            throw new TimeoutException();

        } else {
            this.inputBuffer.reset(data);

            try {
                int type = this.unpacker.unpackInt();
                int boarId;
                int victimId;
                int value;
                String teamName;
                String message;
                Direction direction;
                switch (type) {
                    case 0:
                        boarId = this.unpacker.unpackInt();
                        message = this.unpacker.unpackString();
                        MonsterType monsterType = MonsterType.valueOf(this.unpacker.unpackString());
                        String teamName = this.unpacker.unpackString();
                        int startNumber = this.unpacker.unpackInt();
                        int x = this.unpacker.unpackInt();
                        int y = this.unpacker.unpackInt();
                        return this.eventFactory.createRegistered(boarId, message, monsterType, teamName, startNumber, x, y);
                    case 1:
                        return this.eventFactory.createRegistrationAborted();
                    case 2:
                        boarId = this.unpacker.unpackInt();
                        direction = Direction.valueOf(this.unpacker.unpackString());
                        return this.eventFactory.createMoved(boarId, direction);

                    default:
                        throw new CommException("Unbekannter Eventtyp!");
                }
            } catch (MessagePackException | IOException var10) {
                throw new CommException("Fehler beim Lesen des nächsten Events!", var10);
            }
        }
    }

    public final void sendRegister(String name, MonsterType monsterType, String teamName) {
        try {
            this.packer.packInt(0);
            this.packer.packString(name);
            this.packer.packString(monsterType.name());
            this.packer.packString(teamName);
            this.packer.flush();
            this.socket.send(this.outputBuffer.toByteArray());
        } catch (MessagePackException | IOException var9) {
            throw new CommException("Ein 'Register' Command konnte nicht ins Wire-Format übersetzt werden!", var9);
        } catch (ZMQException var10) {
            if (var10.getErrorCode() == 65) {
                throw new CommException("Die andere Seite der Verbindung ist bereits geschlossen!", var10);
            }

            throw new CommException(String.format("Clientseitiger Commlibfehler %d! Bitte wenden Sie sich an Ihren Tutor!", var10.getErrorCode()), var10);
        } finally {
            this.outputBuffer.reset();
        }

    }

    public final void sendWatch() {
        try {
            this.packer.packInt(1);
            this.packer.flush();
            this.socket.send(this.outputBuffer.toByteArray());
        } catch (MessagePackException | IOException var6) {
            throw new CommException("Ein 'Watch' Command konnte nicht ins Wire-Format übersetzt werden!", var6);
        } catch (ZMQException var7) {
            if (var7.getErrorCode() == 65) {
                throw new CommException("Die andere Seite der Verbindung ist bereits geschlossen!", var7);
            }

            throw new CommException(String.format("Clientseitiger Commlibfehler %d! Bitte wenden Sie sich an Ihren Tutor!", var7.getErrorCode()), var7);
        } finally {
            this.outputBuffer.reset();
        }

    }

    public final void sendMove(Direction direction) {
        try {
            this.packer.packInt(2);
            this.packer.packString(direction.name());
            this.packer.flush();
            this.socket.send(this.outputBuffer.toByteArray());
        } catch (MessagePackException | IOException var7) {
            throw new CommException("Ein 'Move' Command konnte nicht ins Wire-Format übersetzt werden!", var7);
        } catch (ZMQException var8) {
            if (var8.getErrorCode() == 65) {
                throw new CommException("Die andere Seite der Verbindung ist bereits geschlossen!", var8);
            }

            throw new CommException(String.format("Clientseitiger Commlibfehler %d! Bitte wenden Sie sich an Ihren Tutor!", var8.getErrorCode()), var8);
        } finally {
            this.outputBuffer.reset();
        }

    }

    public final void sendWarCry(String cry) {
        try {
            this.packer.packInt(16);
            this.packer.packString(cry);
            this.packer.flush();
            this.socket.send(this.outputBuffer.toByteArray());
        } catch (MessagePackException | IOException var7) {
            throw new CommException("Ein 'WarCry' Command konnte nicht ins Wire-Format übersetzt werden!", var7);
        } catch (ZMQException var8) {
            if (var8.getErrorCode() == 65) {
                throw new CommException("Die andere Seite der Verbindung ist bereits geschlossen!", var8);
            }

            throw new CommException(String.format("Clientseitiger Commlibfehler %d! Bitte wenden Sie sich an Ihren Tutor!", var8.getErrorCode()), var8);
        } finally {
            this.outputBuffer.reset();
        }

    }

    public final void sendDoneActing() {
        try {
            this.packer.packInt(17);
            this.packer.flush();
            this.socket.send(this.outputBuffer.toByteArray());
        } catch (MessagePackException | IOException var6) {
            throw new CommException("Ein 'DoneActing' Command konnte nicht ins Wire-Format übersetzt werden!", var6);
        } catch (ZMQException var7) {
            if (var7.getErrorCode() == 65) {
                throw new CommException("Die andere Seite der Verbindung ist bereits geschlossen!", var7);
            }

            throw new CommException(String.format("Clientseitiger Commlibfehler %d! Bitte wenden Sie sich an Ihren Tutor!", var7.getErrorCode()), var7);
        } finally {
            this.outputBuffer.reset();
        }

    }

    static final class Events {
        static final int REGISTERED = 0;
        static final int REGISTRATIONABORTED = 1;
        static final int MOVED = 2;
        static final int STABBED = 3;
        static final int SLASHED = 4;
        static final int CUT = 5;
        static final int BITTEN = 6;
        static final int STARED = 7;
        static final int CLAWED = 8;
        static final int CRUSHED = 9;
        static final int SINGED = 10;
        static final int SHOT = 11;
        static final int BURNED = 12;
        static final int CAST = 13;
        static final int BLINKED = 14;
        static final int SWAPPED = 15;
        static final int WARCRY = 16;
        static final int DIED = 17;
        static final int KICKED = 18;
        static final int BOARSPAWNED = 19;
        static final int FAIRYSPAWNED = 20;
        static final int ACTNOW = 21;
        static final int FIELDEFFECT = 22;
        static final int POISONEFFECT = 23;
        static final int DONEACTING = 24;
        static final int MAP = 25;
        static final int FIGHT = 26;
        static final int ROUNDBEGIN = 27;
        static final int ROUNDEND = 28;
        static final int WINNER = 29;
        static final int BOARATTACK = 30;

        private Events() {
        }
    }
}
