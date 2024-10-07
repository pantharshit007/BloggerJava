package main.BlogPackage.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Table {
    private final Connection connection;

    public Table(Connection connection) {
        this.connection = connection;
    }

    // create tables
    public void createTables() {
        createUsersTable();
        createPostsTable();
    }

    private void createUsersTable() {
        String createUserTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "email VARCHAR(255) NOT NULL UNIQUE, "
                + "name VARCHAR(255) NOT NULL, "
                + "password VARCHAR(255) NOT NULL)";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(createUserTableSQL);
            System.out.println("Users table created.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createPostsTable() {
        String createPostTableSQL = "CREATE TABLE IF NOT EXISTS posts ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "title VARCHAR(255) NOT NULL, "
                + "content TEXT NOT NULL, "
                + "published BOOLEAN DEFAULT false, "
                + "authorId INT NOT NULL, "
                + "FOREIGN KEY (authorId) REFERENCES users(id))";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(createPostTableSQL);
            System.out.println("Posts table created.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
