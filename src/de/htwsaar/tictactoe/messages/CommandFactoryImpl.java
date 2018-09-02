package de.htwsaar.tictactoe.messages;

import de.htwsaar.tictactoe.CommIds;
import de.htwsaar.tictactoe.CommandFactory;

/**
 * Created by Team 14 on 12.09.2016.
 */
public class CommandFactoryImpl implements CommandFactory<Command> {

    private CommIds commIds;
    private int playerCount;

    public CommandFactoryImpl(CommIds commIds) {
        this.commIds = commIds;
        playerCount = 0;
    }

    public CommIds getCommIds(){
        return commIds;
    }

    public int getPlayerCount() {
        return this.playerCount;
    }

    @Override
    public Command createRegister(int var1, String var2, String var4) {
        return null;
    }

    @Override
    public Command createDoneActing(int var1) {
        return null;
    }

    @Override
    public Command createWatch(int var1) {
        return null;
    }

    @Override
    public Command createMove(int var1, int var2, int var3) {
        return null;
    }

    @Override
    public Command createGame(int var1) {
        return null;
    }

    @Override
    public Command gameFinished(int var1) {
        return null;
    }

    @Override
    public Command gamePaused(int var1) {
        return null;
    }

    @Override
    public Command lostPassword(int var1) {
        return null;
    }
    @Override
    public Command chat(int var1) {
        return null;
    }

}
