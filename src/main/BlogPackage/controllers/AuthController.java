package main.BlogPackage.controllers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import main.BlogPackage.db.DatabaseConnection;
import main.BlogPackage.middleware.AuthMiddleware;
import main.BlogPackage.models.User;
import main.BlogPackage.repositories.UserRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.Objects;

public class AuthController {
    private final UserRepository userRepository;
    private final AuthMiddleware authMiddleware;

    public AuthController(Connection connection) {
        this.userRepository = new UserRepository(connection);
        this.authMiddleware = new AuthMiddleware();
    }

    public void signup(HttpExchange exchange) throws IOException{
        if ("POST".equals(exchange.getRequestMethod())){
            // parse the request body (JSON)
            BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            StringBuilder requestBody = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null){
                requestBody.append(line);
            }

            // convert JSON to user object
            Gson gson = new Gson();
            User user = gson.fromJson(requestBody.toString(), User.class);

             // Insert user into DB
            try{
                userRepository.createUser(user);

                // Respond to client
                String response = "User Signed up!";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                exchange.getResponseBody().write(response.getBytes());

            }catch(Exception e){
                e.printStackTrace();
                exchange.sendResponseHeaders(500,-1);

            }finally {
                exchange.getResponseBody().close();
            }

        }
    }

    public void login(HttpExchange exchange) throws IOException{
        if ("POST".equals(exchange.getRequestMethod())){
            // parse the request body (JSON)
            BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            StringBuilder requestBody = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null){
                requestBody.append(line);
            }

            // convert JSON to user object
            Gson gson = new Gson();
            User loginReq = gson.fromJson(requestBody.toString(), User.class);

            // Insert user into DB
            try{
                User dbUser = userRepository.userLogin(loginReq.getEmail());
                if (dbUser==null){
                    String response = "User not found!";
                    exchange.sendResponseHeaders(404, response.getBytes().length);
                    exchange.getResponseBody().write(response.getBytes());
                }

                // TODO: we can hash the pass using bcrypt
                boolean isPassValid = (dbUser != null &&  loginReq.getPassword().equals(dbUser.getPassword()));

                // wrong password
                if (!isPassValid){
                    String response = "Invalid email or password!";
                    exchange.sendResponseHeaders(401, response.getBytes().length);
                    exchange.getResponseBody().write(response.getBytes());
                    exchange.getResponseBody().close();
                }


                assert dbUser != null;
                String token = authMiddleware.generateToken(dbUser.getId(), dbUser.getEmail());

                String response = "{" +
                        "\"message\":\"Login Success!\"," +
                        "\"token\":\"" + token + "\"" +
                        "}";

                exchange.sendResponseHeaders(200, response.getBytes().length);
                exchange.getResponseBody().write(response.getBytes());

            }catch(Exception e){
                e.printStackTrace();
                exchange.sendResponseHeaders(500,-1);

            }finally {
                exchange.getResponseBody().close();
            }
        }
    }
}
