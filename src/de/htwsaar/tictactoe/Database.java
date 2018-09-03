/*
   *   Handles database connection and queries
   *   @version 0.0.1
 */

//package de.htwsaar.tictactoe;

import java.sql.*;

import javax.management.InstanceNotFoundException;

public class Database {
    private final String DB_HOST;
    private final String DB_DRIVER;
    private Connection connection;
    ResultSet resultSet;

    public Database(String host) throws InstantiationException, ClassNotFoundException, IllegalAccessException  {
        DB_DRIVER = "org.sqlite.JDBC";
        Class.forName(DB_DRIVER).newInstance();
        DB_HOST = host;
    }

    private void connectLite() throws SQLException {
        connection = DriverManager.getConnection(DB_HOST);
    }

    private void disconnect() throws SQLException {
        if(!connection.isClosed()) {
            connection.close();
            if(connection.isClosed()) System.out.println("Connection closed!");
            else throw new RuntimeException(String.format("Failed to disconnect to databse(%s)!", DB_HOST));
        }
    }

    //Returns String array with ID/Username/Password
    public String[] querry(String username) throws SQLException {
        String SQL = "SELECT * FROM users where Username = '" + username + "'";
        Statement stmt = null;
        String[] result = new String[3];

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            
            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                result[0] = rs.getString("ID");
                result[1] = rs.getString("Username");
                result[2] = rs.getString("Password");
            }
        } finally {
            if(stmt != null) stmt.close();
        }
        return result;
    }

    public String[] querryGame(int gameID) throws SQLException {
        String SQL = "SELECT * FROM activeGames where gameID = '" + gameID + "'";
        Statement stmt = null;
        String[] result = new String[5];

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            
            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                result[0] = rs.getString("gameID");
                result[1] = rs.getString("userX");
                result[2] = rs.getString("userO");
                result[3] = rs.getString("status");
                result[4] = rs.getString("turn");
            }
        } finally {
            if(stmt != null) stmt.close();
        }
        return result;
    }
    
    //True if username already exists, False if everything went well
    public boolean registerUser(String username, String password) throws SQLException {
        Statement stmt = null;
        Statement stmt3 = null;
        PreparedStatement stmt2 = null;
        boolean retval = true;

       try {
        //Check if Username exists
        stmt3 = connection.createStatement();
        String SQLTest = "SELECT * FROM users where Username = '" + username + "'";
        ResultSet rs = stmt3.executeQuery(SQLTest);
        
        //Doesn't exist
        if(!rs.next()) {
            //Look up MAXID and increment
            final String SQL = "SELECT MAX(ID) AS MAXID FROM users";
            stmt = connection.createStatement();
            ResultSet rs2 = stmt.executeQuery(SQL);
            rs2.next();
            int MAXID = rs2.getInt("MAXID");
            MAXID++;
            
            //Insert new user into DB
            stmt2 = connection.prepareStatement("INSERT INTO users(id, Username, Password, win, lose, tie) VALUES (?, ?, ?, 0, 0, 0)");
            stmt2.setInt(1, MAXID);
            stmt2.setString(2, username);
            stmt2.setString(3, password);
            stmt2.executeUpdate();
            retval = false;
        }
        return retval;
       } finally {
           if(stmt != null) stmt.close();
           if(stmt2 != null) stmt2.close();
       }
    }

    public void insertActiveGame(int gameID, String gameState, int userX, int userO, String turn) throws SQLException{
        PreparedStatement stmt2 = null;
        try {
            stmt2 = connection.prepareStatement("INSERT INTO activeGames(gameID, userX, userO, status, turn) VALUES (?, ?, ?, ?, ?)");
            stmt2.setInt(1, gameID);
            stmt2.setInt(2, userX);
            stmt2.setInt(3, userO);
            stmt2.setString(4, gameState);
            stmt2.setString(5, turn);
            stmt2.executeUpdate();
        } finally {
            if(stmt2 != null) stmt2.close();
        }
    }

    public static void main(String[] args) {
        try{
            Database db = new Database("jdbc:sqlite:sqlite.db");
            System.out.println("Connecting...");
            db.connectLite();
            String[] result = db.querry("Test");

            for(int i = 0; i < result.length; i++) {
                System.out.println(result[i]);
            }

            if(db.registerUser("Prepared", "Statement")) {
                System.out.println("Querry Failed: Username already exists");
            }
            result = db.querry("Prepared");

            for(int i = 0; i < result.length; i++) {
                System.out.println(result[i]);
            }
            
            db.insertActiveGame(1, "--------x", 1, 2, "o");
            result = db.querryGame(1);
            for(int i = 0; i < result.length; i++) {
                System.out.println(result[i]);
            }
            db.disconnect();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
