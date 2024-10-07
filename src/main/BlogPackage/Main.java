package main.BlogPackage;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;

import main.BlogPackage.controllers.AuthController;
import main.BlogPackage.controllers.BlogController;
import main.BlogPackage.db.DatabaseConnection;
import main.BlogPackage.db.Table;


public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        Connection connection = DatabaseConnection.getConnection();
        // Create Table in DB
        /*
         Table tableManager = new Table(connection);
         tableManager.createTables();
       */

        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String response = "Hey Mom!";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        // user authentication (signup/login)
        try {
            AuthController authController = new AuthController(connection);
            server.createContext("/signup", authController::signup);
            server.createContext("/login", authController::login);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Blog routes
        try{
            BlogController blogController = new BlogController(connection);
            server.createContext("/newContent", blogController::newContent);
            server.createContext("/allBlogs", blogController::allBlogs);
            server.createContext("/blog", blogController::getBlogById);
        }catch (Exception e) {
            e.printStackTrace();
        }

        server.setExecutor(null);
        server.start();
        System.out.println("> Server started on port 8000...");
    }
}


