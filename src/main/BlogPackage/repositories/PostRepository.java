package main.BlogPackage.repositories;

import main.BlogPackage.models.Post;
import main.BlogPackage.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostRepository {
    private Connection connection;

    public PostRepository(Connection connect){
        this.connection = connect;
    }

    public void createPost(Post post) throws SQLException{
        try {
            String query = "INSERT INTO posts (title, content, published, authorId) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, post.getTitle());
            statement.setString(2, post.getContent());
            statement.setBoolean(3, post.getPublished());
            statement.setString(4, post.getAuthorId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Post getPostId(String id) throws SQLException{
        String query = "SELECT * FROM posts where id = ?";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1,id);
        ResultSet result = statement.executeQuery();

        if (result.next()){
            return new Post(
                    result.getString("id"),
                    result.getString("title"),
                    result.getString("content"),
                    result.getBoolean("published"),
                    result.getString("authorId")
                    );
        }
        return null;
    }

    public List<Post> getAllPosts() throws SQLException{
        String query = "SELECT * FROM posts";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);

        List<Post> posts = new ArrayList<>();
        while(result.next()){
            Post post = new Post(
                    result.getString("id"),
                    result.getString("title"),
                    result.getString("content"),
                    result.getBoolean("published"),
                    result.getString("authorId")
            );
            posts.add(post);
        }
        return posts;
    }
}
