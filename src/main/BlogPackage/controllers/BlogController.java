package main.BlogPackage.controllers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import main.BlogPackage.models.Post;
import main.BlogPackage.repositories.PostRepository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BlogController {

    private final PostRepository postRepository;
    private final Gson gson;

    public BlogController(Connection connection) throws SQLException{
        this.postRepository = new PostRepository(connection);
        this.gson = new Gson();
    }

    public void newContent(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())){
            sendResponse(exchange, 405, "Method Not Allowed");
            return;
        }

        try{
            // Parse the request body
            InputStreamReader reader = new InputStreamReader(exchange.getRequestBody());
            Post body = gson.fromJson(reader, Post.class);

            // fetching userId from the authenticated req
            String userId = (String) exchange.getAttribute("userId");
            body.setAuthorId(userId);

            // create the post
            postRepository.createPost(body);
            sendResponse(exchange, 201, "Blog created!");

        } catch(Exception e){
            e.printStackTrace();
            sendResponse(exchange, 500, "Error creating blog post");
        }
    }

    public void allBlogs(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "Method Not Allowed");
            return;
        }

        try{
            List<Post> allPost = postRepository.getAllPosts();
            String jsonResponse = gson.toJson(allPost);
            sendResponse(exchange, 200, jsonResponse);

        }catch (Exception e){
            e.printStackTrace();
            sendResponse(exchange, 500, "Error retrieving blog posts");
        }

    }

    public void getBlogById(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "Method Not Allowed");
            return;
        }

        String query = exchange.getRequestURI().getQuery();
        if (query == null || !query.startsWith("id=")) {
            sendResponse(exchange, 400, "Bad Request: Missing or invalid 'id' parameter");
            return;
        }

        String postId = query.substring(3); // Remove "id=" prefix

        try{
            Post post = postRepository.getPostId(postId);
            if (post != null){
                String jsonResponse = gson.toJson(post);
                sendResponse(exchange, 200, jsonResponse);
            }else {
                sendResponse(exchange, 404, "Blog Post not Found!");
            }
        }catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "Error retrieving blog post");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
