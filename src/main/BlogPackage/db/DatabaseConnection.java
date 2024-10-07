package main.BlogPackage.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static Connection connection = null;
    private static final String URL = "jdbc:mysql://localhost:3306/blog";
    private static final String USER = "root";
    private static final String PASSWORD = "0000";

    // Private constructor to prevent instantiation
    private DatabaseConnection(){};

    public static Connection getConnection(){
        if (connection == null){
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("> DB Connected!");
            } catch (Exception e) {
                System.out.println("> Error: Could not connect to the database.");
                e.printStackTrace();
            }
        }
        return connection;
    }
}
