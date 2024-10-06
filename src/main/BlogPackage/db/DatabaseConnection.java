package main.BlogPackage.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/blog";
    private static final String USER = "root";
    private static final String PASSWORD = "0000";

    public static Connection getConnection(){
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("> DB Connected!");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
