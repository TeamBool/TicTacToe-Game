package de.htwsaar.tictactoe.model;

public interface Tile {
    public enum TILE_TYPE {X, O, A, I, EMPTY};

    public TILE_TYPE getType();

    public Position getPos();

    public String getOwner();

    public int getX();

    public int getY();
}
