package main.BlogPackage;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;

import main.BlogPackage.db.DatabaseConnection;
import main.BlogPackage.db.Table;
import main.BlogPackage.routes.BlogRouter;


public class Main {
    private static final int PORT = 8000;

    public static void main(String[] args) throws IOException {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            Connection connection = DatabaseConnection.getConnection();

            /*
             * Create Table in DB
             * Table tableManager = new Table(connection);
             * tableManager.createTables();
             */

            BlogRouter router = new BlogRouter(server, connection);
            // calling routs
            router.route();

            server.setExecutor(Executors.newFixedThreadPool(10));
            server.start();
            System.out.println("> Server started on port "+PORT+"...");
        } catch (IOException | SQLException e) {
            System.err.println("> Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


