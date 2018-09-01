package de.htwsaar.tictactoe.messages;

import de.htwsaar.tictactoe.Client;
import de.htwsaar.tictactoe.connection.ClientConnection;
import de.htwsaar.tictactoe.model.Game;


public class GamePaused implements Event{

    //Fields
    private int gameID;

    /**
     * @param gameID
     */
    public GamePaused(int gameID) {
        this.gameID = gameID;
    }

    /**
     * Execute as {@link Event} for a given {@link Client}
     *
     * @param client to manipulate
     */
    @Override
    public void executeEvent(Game game, Client client) {
        game.setPaused();
    }

    /**
     * Tests if this {@link Event}/{@link Command} is valid as {@link Event} for a given {@link Client}
     *
     * @param client {@link Client} to test
     * @return returns true if valid else false
     */
    @Override
    public boolean validateEvent(Game game, Client client) {
        return !game.isPaused();
    }

    @Override
    public void sendCommand(ClientConnection cc) {
        throw new UnsupportedOperationException("You are not allowed to call a GamePausedEvent!");
    }

    @Override
    public String getText(Client cli) {
        return String.format("Game is paused!");
    }

}
