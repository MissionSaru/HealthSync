package service;

import dao.UserDAO;
import model.User;
import util.Validator;

public class LoginService {

    private UserDAO userDAO = new UserDAO();


    public User authenticate(String username, String password) {

        if (!Validator.isValidUsername(username)) {

            System.out.println("Invalid username");

            return null;

        }

        if (Validator.isEmpty(password)) {

            System.out.println("Password cannot be empty");

            return null;

        }

        return userDAO.login(username.trim(), password);

    }

}