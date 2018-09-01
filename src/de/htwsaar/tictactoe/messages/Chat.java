package de.htwsaar.tictactoe.messages;

import de.htwsaar.tictactoe.Client;
import de.htwsaar.tictactoe.connection.ClientConnection;
import de.htwsaar.tictactoe.model.Game;

public class Chat implements Event {

    //Fields
    private String message;

    /**
     * @param message
     */
    public Chat(String message) {
        this.message = message;
    }

    /**
     * Execute as {@link Event} for a given {@link Client}
     *
     * @param client to manipulate
     */
    @Override
    public void executeEvent(Game game, Client client) {
        //game.
        //client.resetCreatureStats(game);
    }

    /**
     * Tests if this {@link Event}/{@link Command} is valid as {@link Event} for a given {@link Client}
     *
     * @param client {@link Client} to test
     * @return returns true if valid else false
     */
    @Override
    public boolean validateEvent(Game game, Client client) {
        return true;
    }

    @Override
    public void sendCommand(ClientConnection cc) {
        throw new UnsupportedOperationException("You are not allowed to call a ChatEvent!");
    }

    @Override
    public String getText(Client cli) {
        return String.format(this.message);
    }

}
