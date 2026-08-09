package model;

public class User {

    private final int userId;
    private String username;
    private String password;
    private String role;


    // Constructor
    public User(int userId, String username, String password, String role) {

        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;

    }


    // Getter methods

    public int getUserId() {
        return userId;
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public String getRole() {
        return role;
    }


    // Setter methods (optional, useful for updating data)

    public void setUsername(String username) {
        this.username = username;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public void setRole(String role) {
        this.role = role;
    }


    // Display user information

    @Override
    public String toString() {

        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';

    }
}