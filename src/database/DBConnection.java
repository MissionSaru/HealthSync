package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/healthsync";

    private static final String USER = "root";

    private static final String PASSWORD = "DarkLord9863!";


    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("Database Connected Successfully");

            return con;

        } catch(ClassNotFoundException e) {

            System.out.println("JDBC Driver not found: " + e.getMessage());

        } catch(SQLException e) {

            System.out.println("Database Connection Failed: " + e.getMessage());

        }

        return null;
    }
}