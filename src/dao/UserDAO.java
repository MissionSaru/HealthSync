package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.User;

public class UserDAO {

    public User login(String username, String password) {

        User user = null;

        String query = "SELECT * FROM users WHERE username=? AND password=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    user = new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return user;
    }


    // Checks if a username is already taken

    public boolean usernameExists(String username) {

        String query = "SELECT 1 FROM users WHERE username=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {

                return rs.next();

            }

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return true; // fail safe: treat unknown errors as "taken" so we don't risk a duplicate

    }


    // Registers a new user account, returns generated user_id, or -1 on failure

    public int registerUser(String username, String password, String role) {

        String query = "INSERT INTO users(username, password, role) VALUES(?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getInt(1);
                }

            }

        } catch (SQLException e) {

            System.out.println("Registration Error: " + e.getMessage());

        }

        return -1;

    }
}