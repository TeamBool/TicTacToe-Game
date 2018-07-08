package de.htwsaar.tictactoe.model;

public class Field {
    public int size;
    public int gameField[][];
    public int player;
    public int winCondition;

    public Field(int size, int winCondition)
    {
        gameField = new int[size][size];
        player = 0;
        this.winCondition = winCondition;
    }


    public int checkWinner()
    {
        int count = 0;

        //Horizontal Ueberpruefung
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size ;j++)
            {
                //Spieler 1
                if (gameField[i][j] == 1)
                {
                    if(player != 1)
                        count = 0;
                    player = 1;
                    count++;
                    if(count == winCondition)
                        return player;
                }
                //Spieler 2
                else if (gameField[i][j] == 2)
                {
                    if(player != 2)
                        count = 0;
                    player = 2;
                    count++;
                    if(count == winCondition)
                        return player;
                }
                else
                {
                    player = 0;
                    count = 0;
                }
            }
        }

        //Vertikale Ueberpruefung
        for(int j = 0; j < size; j++)
        {
            for(int i = 0; i < size ;i++)
            {
                //Spieler 1
                if (gameField[i][j] == 1)
                {
                    if(player != 1)
                        count = 0;
                    player = 1;
                    count++;
                    if(count == winCondition)
                        return player;
                }
                //Spieler 2
                else if (gameField[i][j] == 2)
                {
                    if(player != 2)
                        count = 0;
                    player = 2;
                    count++;
                    if(count == winCondition)
                        return player;
                }
                else
                {
                    player = 0;
                    count = 0;
                }
            }
        }
        //Kein Gewinner

        return player = 0;
    }


}
