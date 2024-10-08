package main.BlogPackage.routes;

import com.sun.net.httpserver.HttpServer;
import java.sql.Connection;
import java.sql.SQLException;

import main.BlogPackage.controllers.AuthController;
import main.BlogPackage.controllers.BlogController;
import main.BlogPackage.controllers.RootHandler;
import main.BlogPackage.middleware.AuthMiddleware;


public class BlogRouter {
    private final HttpServer server;
    private final Connection connection;
    private final AuthMiddleware authMiddleware;

    public BlogRouter(HttpServer server, Connection connection){
        this.server = server;
        this.connection = connection;
        this.authMiddleware = new AuthMiddleware();
    }

    public void route() throws SQLException {
        server.createContext("/", new RootHandler());

        // auth controller
        AuthController authController = new AuthController(connection);
        server.createContext("/signup", authController::signup);
        server.createContext("/login", authController::login);

        // blog controller
        BlogController blogController = new BlogController(connection);
        server.createContext("/newContent", authMiddleware.authenticate(blogController::newContent));
        server.createContext("/allBlogs", blogController::allBlogs);
        server.createContext("/blog", blogController::getBlogById);
    }
}
