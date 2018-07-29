/*
   *   Handles database connection and queries
   *   @version 0.0.1
 */

package de.htwsaar.tictactoe;

import java.sql.*;

import javax.management.InstanceNotFoundException;

public class Database {
    private final String DB_HOST;
    private final String DB_USERNAME;
    private final String DB_PASSWORD;
    private final String DB_DRIVER;
    private Connection connection;
    ResultSet resultSet;

    public Database(String host, String username, String password) throws InstantiationException, ClassNotFoundException, IllegalAccessException  {
        DB_DRIVER = "com.mysql.jdbc.Driver";
        Class.forName(DB_DRIVER).newInstance();
        DB_HOST = host;
        DB_USERNAME = username;
        DB_PASSWORD = password;
    }

    // Example object creation:
    // new Database("jdbc:sqlserver://Server:Port;database=Database;", "Username", "Password", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
    public Database(String host, String username, String password, String driver) throws InstantiationException, ClassNotFoundException, IllegalAccessException  {
        DB_DRIVER = driver;
        Class.forName(DB_DRIVER).newInstance();
        DB_HOST = host;
        DB_USERNAME = username;
        DB_PASSWORD = password;
    }

    private void connect() throws SQLException {
        connection = DriverManager.getConnection(DB_HOST, DB_USERNAME, DB_PASSWORD);
        if(!connection.isClosed()) System.out.println("Database connection successful!");
        else throw new RuntimeException(String.format("Failed to connect to databse(%s) with User: %s and Password: %s!", DB_HOST, DB_USERNAME, DB_PASSWORD));
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
        String SQL = "SELECT * FROM Users where Username = '" + username + "'";
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
    
    //True if username already exists, False if everything went well
    public boolean insertInDB(String username, String password) throws SQLException {
        Statement stmt = null;
        Statement stmt3 = null;
        PreparedStatement stmt2 = null;
        boolean retval = true;

       try {
        //Check if Username exists
        stmt3 = connection.createStatement();
        String SQLTest = "SELECT * FROM Users where Username = '" + username + "'";
        ResultSet rs = stmt3.executeQuery(SQLTest);
        
        //Doesn't exist
        if(!rs.next()) {
            //Look up MAXID and increment
            final String SQL = "SELECT MAX(ID) AS MAXID FROM Users";
            stmt = connection.createStatement();
            ResultSet rs2 = stmt.executeQuery(SQL);
            rs2.next();
            int MAXID = rs2.getInt("MAXID");
            MAXID++;
            
            //Insert new user into DB
            stmt2 = connection.prepareStatement("INSERT INTO Users(ID, Username, Password) VALUES (?, ?, ?)");
            stmt2.setInt(1, MAXID);
            stmt2.setString(2, username);
            stmt2.setString(3, password);
            stmt2.executeUpdate();
            retval = false;
        }
        return retval;
       } finally {
           if(stmt != null) stmt.close();
           if(stmt2 != null) stmt.close();
       }
    }
}
