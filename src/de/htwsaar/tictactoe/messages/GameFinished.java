
package de.htwsaar.tictactoe.messages;

import de.htwsaar.tictactoe.Client;
import de.htwsaar.tictactoe.connection.ClientConnection;
import de.htwsaar.tictactoe.model.Game;

public class GameFinished implements Event {

    private String teamName = "";

    public GameFinished(String NameWnr){
        this.teamName = NameWnr;
    }

    public void executeEvent(Game game, Client client){
        game.setRunning(false);
    }

    public boolean validateEvent(Game game, Client client){
     return true;
    }

    @Override
    public void sendCommand(ClientConnection cc) {
        throw new UnsupportedOperationException("You are not allowed to send a WinnerEvent!");
    }

    @Override
    public String getText(Client cli) {
        return String.format("%s wins!", this.teamName);
    }

    public String getNameWnr() {
        return this.teamName;
    }
}
