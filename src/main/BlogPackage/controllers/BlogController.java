package main.BlogPackage.controllers;

import com.sun.net.httpserver.HttpExchange;
import main.BlogPackage.db.DatabaseConnection;
import main.BlogPackage.repositories.PostRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class BlogController {

    private final PostRepository postRepository;

    public BlogController(Connection connection) throws SQLException{
        this.postRepository = new PostRepository(connection);
    }

    public void newContent(HttpExchange exchange) {
    }

    public void allBlogs(HttpExchange exchange) {
    }

    public void getBlogById(HttpExchange exchange) {
    }
}
