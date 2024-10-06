package main.BlogPackage;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.SQLException;

import main.BlogPackage.controllers.AuthController;
import main.BlogPackage.controllers.BlogController;


public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

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

        // Route for user authentication (signup/login)
        try {
            server.createContext("/signup", new AuthController()::signup);
            server.createContext("/login", new AuthController()::login);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Route for blog operations (create, fetch all, fetch specific)
        server.createContext("/newContent", new BlogController()::newContent);
        server.createContext("/allBlogs", new BlogController()::allBlogs);
        server.createContext("/blog", new BlogController()::getBlogById);

        server.setExecutor(null);
        server.start();
        System.out.println("> Server started on port 8000...");
    }
}


