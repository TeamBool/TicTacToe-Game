package de.htwsaar.tictactoe.model;

import java.util.ArrayList;

public class Field {
    public int size;
    public Tile[][] gameField;
    public ArrayList<Player> players;
    public int winCondition;

    public Field(int size, ArrayList<Player> players)
    {
        this.size = size;
        gameField = new Tile[size][size];
        this.players = players;
        this.winCondition = players.size() + 1;
    }

    public boolean checkSetTile(Tile tile){
        if(gameField[tile.getPos().getX()][tile.getPos().getY()].getType() == Tile.TILE_TYPE.EMPTY){
            return true;
            //current player == tile.owner?
        } else {return false;}
    }

    public void setTile(Tile tile){
        if(checkSetTile(tile)){
            gameField[tile.getPos().getX()][tile.getPos().getY()] = tile;
        } else {
            throw new IllegalArgumentException("The position is already occupied!");
        }
    }


    public Player checkWinner() {
        int count = 0;
        int player1 = 0;
        int player2 = 0;
        int player3 = 0;
        int player4 = 0;

        for (int i = 0; i < size; i++) {
            switch (this.players.size()) {
                case 2: {
                    Player nr1 = this.players.get(0);
                    Player nr2 = this.players.get(1);
                    player1 = checkDiagonal(i, nr1);
                    if (player1 == winCondition)
                        return nr1;
                    player2 = checkDiagonal(i, nr2);
                    if (player2 == winCondition)
                        return nr2;
                    break;
                }
                case 3: {
                    Player nr1 = this.players.get(0);
                    Player nr2 = this.players.get(1);
                    Player nr3 = this.players.get(2);
                    player1 = checkDiagonal(i, nr1);
                    if (player1 == winCondition)
                        return nr1;
                    player2 = checkDiagonal(i, nr2);
                    if (player2 == winCondition)
                        return nr2;
                    player3 = checkDiagonal(i, nr3);
                    if (player3 == winCondition)
                        return nr3;
                    break;
                }
                case 4: {
                    Player nr1 = this.players.get(0);
                    Player nr2 = this.players.get(1);
                    Player nr3 = this.players.get(2);
                    Player nr4 = this.players.get(3);
                    player1 = checkDiagonal(i, nr1);
                    if (player1 == winCondition)
                        return nr1;
                    player2 = checkDiagonal(i, nr2);
                    if (player2 == winCondition)
                        return nr2;
                    player3 = checkDiagonal(i, nr3);
                    if (player3 == winCondition)
                        return nr3;
                    player4 = checkDiagonal(i, nr4);
                    if (player4 == winCondition)
                        return nr4;
                    break;
                }
                default:
                    break;

            }
            for (int j = 0; j < size; j++) {
                switch (this.players.size()) {
                    case 2: {
                        Player nr1 = this.players.get(0);
                        Player nr2 = this.players.get(1);
                        player1 = checkHorizontal(i, j, nr1);
                        if (player1 == winCondition)
                            return nr1;
                        player2 = checkHorizontal(i, j, nr2);
                        if (player2 == winCondition)
                            return nr2;
                        break;
                    }
                    case 3: {
                        Player nr1 = this.players.get(0);
                        Player nr2 = this.players.get(1);
                        Player nr3 = this.players.get(2);
                        player1 = checkHorizontal(i, j, nr1);
                        if (player1 == winCondition)
                            return nr1;
                        player2 = checkHorizontal(i, j, nr2);
                        if (player2 == winCondition)
                            return nr2;
                        player3 = checkHorizontal(i, j, nr3);
                        if (player3 == winCondition)
                            return nr3;
                        break;
                    }
                    case 4: {
                        Player nr1 = this.players.get(0);
                        Player nr2 = this.players.get(1);
                        Player nr3 = this.players.get(2);
                        Player nr4 = this.players.get(3);
                        player1 = checkHorizontal(i, j, nr1);
                        if (player1 == winCondition)
                            return nr1;
                        player2 = checkHorizontal(i, j, nr2);
                        if (player2 == winCondition)
                            return nr2;
                        player3 = checkHorizontal(i, j, nr3);
                        if (player3 == winCondition)
                            return nr3;
                        player4 = checkHorizontal(i, j, nr4);
                        if (player4 == winCondition)
                            return nr4;
                        break;
                    }
                    default:
                        break;

                }
                switch (this.players.size()) {
                    case 2: {
                        Player nr1 = this.players.get(0);
                        Player nr2 = this.players.get(1);
                        player1 = checkVertical(i, j, nr1);
                        if (player1 == winCondition)
                            return nr1;
                        player2 = checkVertical(i, j, nr2);
                        if (player2 == winCondition)
                            return nr2;
                        break;
                    }
                    case 3: {
                        Player nr1 = this.players.get(0);
                        Player nr2 = this.players.get(1);
                        Player nr3 = this.players.get(2);
                        player1 = checkVertical(i, j, nr1);
                        if (player1 == winCondition)
                            return nr1;
                        player2 = checkVertical(i, j, nr2);
                        if (player2 == winCondition)
                            return nr2;
                        player3 = checkVertical(i, j, nr3);
                        if (player3 == winCondition)
                            return nr3;
                        break;
                    }
                    case 4: {
                        Player nr1 = this.players.get(0);
                        Player nr2 = this.players.get(1);
                        Player nr3 = this.players.get(2);
                        Player nr4 = this.players.get(3);
                        player1 = checkVertical(i, j, nr1);
                        if (player1 == winCondition)
                            return nr1;
                        player2 = checkVertical(i, j, nr2);
                        if (player2 == winCondition)
                            return nr2;
                        player3 = checkVertical(i, j, nr3);
                        if (player3 == winCondition)
                            return nr3;
                        player4 = checkVertical(i, j, nr4);
                        if (player4 == winCondition)
                            return nr4;
                        break;
                    }
                    default:
                        break;
                }
            }
        }
        return null;
    }
    private int checkHorizontal(int i, int j, Player player){
        int playerCount = 0;
        if(gameField[i][j].getType() == player.getTileType()){
            if(j == 0 && gameField[i][j+1].getType() == player.getTileType()){
                playerCount++;
            } else {
                playerCount = 0;
            }
            if(j != 0 && j < size-1 && gameField[i][j+1].getType() == player.getTileType() && gameField[i][j-1].getType() == player.getTileType()){
                playerCount++;
            } else {
                playerCount = 0;
            }
            if(j != 0 && j == size-1 && gameField[i][j-1].getType() == player.getTileType()){
                playerCount++;
            } else {
                playerCount = 0;
            }
        }
        return playerCount;
    }

    private int checkVertical(int i, int j, Player player){
        int playerCount = 0;
        if(gameField[i][j].getType() == player.getTileType()){
            if(i == 0 && gameField[i+1][j].getType() == player.getTileType()){
                playerCount++;
            } else {
                playerCount = 0;
            }
            if(i != 0 && i < size-1 && gameField[i+1][j].getType() == player.getTileType() && gameField[i-1][j].getType() == player.getTileType()){
                playerCount++;
            } else {
                playerCount = 0;
            }
            if(i != 0 && i == size-1 && gameField[i-1][j].getType() == player.getTileType()){
                playerCount++;
            } else {
                playerCount = 0;
            }
        }
        return playerCount;
    }

    private int checkDiagonal(int i, Player player){
        int playerCount = 0;
        if(gameField[i][i].getType() == player.getTileType()){
            if(i == 0 && gameField[i+1][i+1].getType() == player.getTileType()){
                playerCount++;
            } else {
                playerCount = 0;
            }
            if(i != 0 && i < size-1 && gameField[i+1][i+1].getType() == player.getTileType() && gameField[i-1][i-1].getType() == player.getTileType()){
                playerCount++;
            } else {
                playerCount = 0;
            }
            if(i != 0 && i == size-1 && gameField[i-1][i-1].getType() == player.getTileType()){
                playerCount++;
            } else {
                playerCount = 0;
            }
        }
        return playerCount;
    }
}
