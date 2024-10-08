package main.BlogPackage.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class RootHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Hi Mom!";
        exchange.sendResponseHeaders(200, response.length());
        try(OutputStream os = exchange.getResponseBody()){
            os.write(response.getBytes());
        }

    }
}
