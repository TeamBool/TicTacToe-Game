package de.htwsaar.tictactoe;

public interface EventFactory<T> {
    T createRegistered(int var1, String var2, String var4, int var5, int var6, int var7);

    T createRegistrationAborted();

    T createActNow(int var1);

    T createDoneActing(int var1);

    T createWinner(String var1);
}
