package de.htwsaar.tictactoe;

import de.htwsaar.tictactoe.connection.ServerConnection;
import de.htwsaar.tictactoe.messages.Command;
import de.htwsaar.tictactoe.messages.CommandFactoryImpl;
import de.htwsaar.tictactoe.model.Game;

import java.util.HashMap;

public class Server {

    private ServerConnection<Command> serverConnection;
    private CommIds commIds;
    private boolean running = true;
    private HashMap<Integer, Game> runnningGames = new HashMap<>(); //id, game

    /**
     * Creates a new instance of {@link Server} with given parameters.
     *
     * @param port      The port for the {@link ServerConnection}
     */
    public Server(int port) {
        super();
        commIds = new CommIds();
        CommandFactoryImpl cmdFactory = new CommandFactoryImpl(commIds);
        serverConnection = new ServerConnection<>(port, cmdFactory);
    }

    /**
     * Starts the {@link Server}.
     */
    public void run() {
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