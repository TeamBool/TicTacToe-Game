package de.htwsaar.tictactoe;

public interface CommandFactory<T> {
    T createRegister(int var1, String var2, String var4);

    T createDoneActing(int var1);
}
