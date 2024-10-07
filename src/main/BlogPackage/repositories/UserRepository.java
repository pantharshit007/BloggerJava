package main.BlogPackage.repositories;

import main.BlogPackage.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private final Connection connection;

    public UserRepository(Connection connection){
        this.connection = connection;
    }

    public void createUser(User user) {
        try {
            String sql = "INSERT INTO users (email, name, password) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User userLogin(String email) throws SQLException{
        String query = "SELECT * FROM users where email = ?";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1,email);
        ResultSet result = statement.executeQuery();

        if (result.next()){
            return new User(
                    result.getString("id"),
                    result.getString("email"),
                    result.getString("name"),
                    result.getString("password"));
        }
        return null;
    }
}
