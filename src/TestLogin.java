import dao.UserDAO;
import model.User;

public class TestLogin {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        // use your existing database username and password
        User user = userDAO.login("patient_ram", "patient123");


        if (user != null) {

            System.out.println("Login Successful");
            System.out.println("User ID: " + user.getUserId());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Role: " + user.getRole());

        } else {

            System.out.println("Invalid Username or Password");

        }

    }
}