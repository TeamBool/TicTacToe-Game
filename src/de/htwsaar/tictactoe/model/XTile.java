package de.htwsaar.tictactoe.model;

public class XTile implements Tile {

    private Position position;
    private String owner;

    public XTile(String owner, int x, int y){
        this.owner = owner;
        this.position = new Position(x,y);
    }

    @Override
    public TILE_TYPE getType() {
        return TILE_TYPE.X;
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
