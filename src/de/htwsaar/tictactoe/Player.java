/*
   *   Der eingeloggte Spieler, mit den entsprechenden
   *   @version 0.0.1
 */

package de.htwsaar.tictactoe;

public class Player {

    private String id;
    private String password;


    public static void sendTurn()
    {
        try
        {
            while(true)
            {
                // TO DO: Gibt den zu spielenden Zug in eine sendbares Format um, sendet diesen und wartet auf die Bestaetigung



            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void getTurn()
    {

        try
        {
            while(true)
            {
                // TO DO: wartet auf den Zug



            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
