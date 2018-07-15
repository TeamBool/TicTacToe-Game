package de.htwsaar.tictactoe;

public class Server {

    private ServerConnection<Command> serverConnection;
    private CommIds commIds;

    /**
     * Creates a new instance of {@link Server} with given parameters.
     *
     * @param port      The port for the {@link ServerConnection}
     * @param timeout   The timeout for the {@link ServerConnection}
     * @param fightFile The read fightFile as {@link String}.
     * @param mapFile   The read mapFile as {@link String}.
     */
    public Server(int port, int timeout) {
        super();
        commIds = new CommIds();
        CommandFactoryImpl cmdFactory = new CommandFactoryImpl(commIds);
        serverConnection = new ServerConnection<>(port, timeout, cmdFactory);

        try {

        } catch (InvalidFightFileException | InvalidMapFileException exception) {
            serverConnection.close();
            running = false;
            throw exception;
        }
    }

    /**
     * Starts the {@link Server}.
     */
    public void run() {
        registerState();
        spawnFairies();

        while (running) {

        }
        serverConnection.close();
    }

    public void close() {
        running = false;
        serverConnection.close();
    }


    public CommIds getCommIds() {
        return commIds;
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
    }
}