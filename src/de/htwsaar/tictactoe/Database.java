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

    public void querry() throws SQLException {
        String SQL = "SELECT * FROM Persons";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        // Iterate through the data in the result set and display it.
        while (rs.next()) {
            System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5));
        }
    }
    
    public void updateDB(String query) throws SQLException {
        Statement stmt = connection.createStatement();
    }
}
