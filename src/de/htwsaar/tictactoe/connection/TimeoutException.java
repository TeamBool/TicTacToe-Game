package de.htwsaar.tictactoe.connection;

public class TimeoutException extends RuntimeException {
    public TimeoutException() {
        super("Timeout");
    }
}