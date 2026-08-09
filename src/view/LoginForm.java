package view;

import java.awt.*;
import javax.swing.*;
import model.User;
import service.LoginService;

public class LoginForm extends JFrame {

    JLabel titleLabel;
    JLabel usernameLabel;
    JLabel passwordLabel;

    JTextField usernameField;
    JPasswordField passwordField;

    JButton loginButton;
    JButton registerButton;

    public LoginForm() {

        setTitle("HealthSync Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        titleLabel = new JLabel("HealthSync Login");
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        panel.add(titleLabel);
        panel.add(new JLabel(""));

        panel.add(usernameLabel);
        panel.add(usernameField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(new JLabel(""));
        panel.add(loginButton);

        panel.add(new JLabel(""));
        panel.add(registerButton);

        add(panel);

        loginButton.addActionListener(e -> login());

        registerButton.addActionListener(e -> {

            dispose();
            new RegisterForm();

        });

        setVisible(true);
    }

    private void login() {

        String username = usernameField.getText().trim();
        String password = String.valueOf(passwordField.getPassword());

        LoginService loginService = new LoginService();
        User user = loginService.authenticate(username, password);

        if (user != null) {

            dispose();

            if (user.getRole().equalsIgnoreCase("Patient")) {

                new view.patient.PatientDashboard(user);

            } 
            else if (user.getRole().equalsIgnoreCase("Doctor")) {

                new view.doctor.DoctorDashboard(user);

            } 
            else if (user.getRole().equalsIgnoreCase("Nurse")) {

                new view.nurse.NurseDashboard(user);

            }
            else {

                JOptionPane.showMessageDialog(
                        this,
                        "Unknown user role."
                );

                new LoginForm();

            }

        } 
        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password"
            );

        }

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new LoginForm());

    }

}