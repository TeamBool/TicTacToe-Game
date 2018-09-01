package de.htwsaar.tictactoe;

public interface CommandFactory<T> {
    T createRegister(int var1, String var2, String var4);

    T createDoneActing(int var1);

    T createWatch(int var1);

    T createMove(int var1, int var2, int var3);
    T createGame(int var1);

    T gameFinished(int var1);
    T gamePaused(int var1);
    T lostPassword(int var1);
    T chat(int var1);
}
