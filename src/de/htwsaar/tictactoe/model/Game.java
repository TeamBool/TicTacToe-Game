package de.htwsaar.tictactoe.model;

import java.util.ArrayList;

public class Game {

    private int id;
    private Field field;
    private int size = 3;
    private ArrayList<Player> players = new ArrayList<Player>();
    private boolean running = false;

    public Game(){
        //TODO: get GameID form DB
    }

    public boolean startGame(){
        try {
            createField();
            this.running = true;
        } catch (Exception e){
            System.out.println("Fehler: " );
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Field createField(){
        this.field = new Field(this.size, players);
        return this.field;
    }

    private void setSize(int x){
        this.size = x;
    }

    public boolean addPlayer(Player player){
        try {
            players.add(player);
        } catch (Exception e){
            System.out.println("Fehler: " );
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean hasWinner()
    {
        if(this.running && this.field.checkWinner() == null){
            return false;
        } else {
            this.running = false;
            return true;
        }
    }


    public void setPaused() {
        //TODO
    }

    public boolean isPaused() {
        //TODO
        return false;
    }

    public void setRunning(boolean b) {
        //TODO
    }
}
