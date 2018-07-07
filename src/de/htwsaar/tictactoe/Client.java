package de.htwsaar.tictactoe;

import de.htwsaar.tictactoe.connection.Connection;

public class Client implements Connection {

    public static String serveradress;

    public Client(String serveradress)
    {
        this.serveradress = serveradress;

    }

    public String serveradress()
    {
        return  serveradress;
    }

}
