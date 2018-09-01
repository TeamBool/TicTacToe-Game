package de.htwsaar.tictactoe.model;

public class EmptyTile implements Tile {

    private Position position;
    private String owner = "none";

    public EmptyTile(int x, int y){
        this.position = new Position(x,y);
    }

    @Override
    public TILE_TYPE getType() {
        return TILE_TYPE.EMPTY;
    }

    @Override
    public Position getPos() {
        return this.position;
    }

    @Override
    public String getOwner() {
        return this.owner;
    }

    @Override
    public int getX() {
        return this.position.getX();
    }

    @Override
    public int getY() {
        return this.position.getY();
    }
}
