package view;

import java.awt.*;
import javax.swing.*;
import service.RegisterService;

public class RegisterForm extends JFrame {

    private JComboBox<String> roleBox;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField phoneField;

    // patient fields
    private JTextField nameField;
    private JTextField dobField;
    private JComboBox<String> genderBox;
    private JTextField addressField;
    private JTextField bloodGroupField;

    // doctor fields
    private JTextField doctorNameField;
    private JTextField specializationField;

    // nurse fields
    private JTextField nurseNameField;

    private JButton registerButton;
    private JButton backButton;

    private CardLayout cardLayout;
    private JPanel rolePanel;

    public RegisterForm() {

        setTitle("HealthSync - Registration");
        setSize(440, 540);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(8, 8));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---- shared account fields ----
        JPanel topPanel = new JPanel(new GridLayout(5, 2, 8, 8));

        roleBox = new JComboBox<>(new String[]{"Patient", "Doctor", "Nurse"});
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        phoneField = new JTextField();

        topPanel.add(new JLabel("Register as:"));
        topPanel.add(roleBox);
        topPanel.add(new JLabel("Username:"));
        topPanel.add(usernameField);
        topPanel.add(new JLabel("Password:"));
        topPanel.add(passwordField);
        topPanel.add(new JLabel("Confirm Password:"));
        topPanel.add(confirmPasswordField);
        topPanel.add(new JLabel("Phone:"));
        topPanel.add(phoneField);

        // ---- role-specific fields ----
        cardLayout = new CardLayout();
        rolePanel = new JPanel(cardLayout);

        rolePanel.add(buildPatientCard(), "Patient");
        rolePanel.add(buildDoctorCard(), "Doctor");
        rolePanel.add(buildNurseCard(), "Nurse");

        // ---- buttons ----
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        registerButton = new JButton("Register");
        backButton = new JButton("Back to Login");

        bottomPanel.add(registerButton);
        bottomPanel.add(backButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(rolePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        roleBox.addActionListener(e ->
                cardLayout.show(rolePanel, (String) roleBox.getSelectedItem()));

        registerButton.addActionListener(e -> register());

        backButton.addActionListener(e -> {
            dispose();
            new LoginForm();
        });

        setVisible(true);
    }

    private JPanel buildPatientCard() {

        JPanel panel = new JPanel(new GridLayout(5, 2, 8, 8));

        nameField = new JTextField();
        dobField = new JTextField();
        genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        addressField = new JTextField();
        bloodGroupField = new JTextField();

        panel.add(new JLabel("Full Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Date of Birth (dd-MM-yyyy):"));
        panel.add(dobField);
        panel.add(new JLabel("Gender:"));
        panel.add(genderBox);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Blood Group:"));
        panel.add(bloodGroupField);

        return panel;
    }

    private JPanel buildDoctorCard() {

        JPanel panel = new JPanel(new GridLayout(2, 2, 8, 8));

        doctorNameField = new JTextField();
        specializationField = new JTextField();

        panel.add(new JLabel("Doctor Name:"));
        panel.add(doctorNameField);
        panel.add(new JLabel("Specialization:"));
        panel.add(specializationField);

        return panel;
    }

    private JPanel buildNurseCard() {

        JPanel panel = new JPanel(new GridLayout(1, 2, 8, 8));

        nurseNameField = new JTextField();

        panel.add(new JLabel("Nurse Name:"));
        panel.add(nurseNameField);

        return panel;
    }

    private void register() {

        String role = (String) roleBox.getSelectedItem();

        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
        String phone = phoneField.getText();

        RegisterService registerService = new RegisterService();

        String error;

        if (role.equals("Doctor")) {

            error = registerService.registerDoctor(
                    username, password, confirmPassword,
                    doctorNameField.getText(), specializationField.getText(), phone);

        } else if (role.equals("Nurse")) {

            error = registerService.registerNurse(
                    username, password, confirmPassword,
                    nurseNameField.getText(), phone);

        } else {

            error = registerService.registerPatient(
                    username, password, confirmPassword,
                    nameField.getText(), dobField.getText(),
                    (String) genderBox.getSelectedItem(), phone,
                    addressField.getText(), bloodGroupField.getText());
        }

        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Registration Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Registration successful! You can now log in.",
                "Success", JOptionPane.INFORMATION_MESSAGE);

        dispose();
        new LoginForm();
    }
}