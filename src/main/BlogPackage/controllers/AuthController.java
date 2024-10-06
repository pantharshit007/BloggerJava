package main.BlogPackage.controllers;

import com.sun.net.httpserver.HttpExchange;
import main.BlogPackage.db.DatabaseConnection;
import main.BlogPackage.repositories.UserRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AuthController {
    private final UserRepository userRepository;

    public AuthController() throws SQLException{
        Connection connection = DatabaseConnection.getConnection();
        this.userRepository = new UserRepository(connection);
    }

    public void signup(HttpExchange exchange) throws IOException{
        System.out.println("signup");
    }

    public void login(HttpExchange exchange) throws IOException{
        System.out.println("Login");
    }

}
