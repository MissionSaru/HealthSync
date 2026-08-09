package service;

import dao.DoctorDAO;
import dao.NurseDAO;
import dao.PatientDAO;
import dao.UserDAO;
import java.sql.Date;
import util.DateUtil;
import util.Validator;

public class RegisterService {

    private UserDAO userDAO = new UserDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private DoctorDAO doctorDAO = new DoctorDAO();
    private NurseDAO nurseDAO = new NurseDAO();

    public String registerPatient(String username, String password, String confirmPassword,
                                  String name, String dobText, String gender,
                                  String phone, String address, String bloodGroup) {

        String error = validateCredentials(username, password, confirmPassword);
        if (error != null) {
            return error;
        }
        if (Validator.isEmpty(name)) {
            return "Full name is required.";
        }

        Date dob = DateUtil.parseDate(dobText);
        if (dob == null) {
            return "Invalid date of birth. Use dd-MM-yyyy format.";
        }
        if (Validator.isEmpty(phone)) {
            return "Phone number is required.";
        }
        if (Validator.isEmpty(address)) {
            return "Address is required.";
        }
        if (Validator.isEmpty(bloodGroup)) {
            return "Blood group is required.";
        }

        String trimmedUsername = username.trim();
        if (userDAO.usernameExists(trimmedUsername)) {
            return "Username is already taken.";
        }

        int userId = userDAO.registerUser(trimmedUsername, password, "Patient");
        if (userId == -1) {
            return "Failed to create account. Please try again.";
        }

        boolean ok = patientDAO.registerPatient(userId, name.trim(), dob, gender,
                phone.trim(), address.trim(), bloodGroup.trim());

        if (!ok) {
            return "Account created but patient profile failed.";
        }

        return null; // success
    }

    public String registerDoctor(String username, String password, String confirmPassword,
                                 String doctorName, String specialization, String phone) {

        String error = validateCredentials(username, password, confirmPassword);
        if (error != null) {
            return error;
        }
        if (Validator.isEmpty(doctorName)) {
            return "Doctor name is required.";
        }
        if (Validator.isEmpty(specialization)) {
            return "Specialization is required.";
        }
        if (Validator.isEmpty(phone)) {
            return "Phone number is required.";
        }

        String trimmedUsername = username.trim();
        if (userDAO.usernameExists(trimmedUsername)) {
            return "Username is already taken.";
        }

        int userId = userDAO.registerUser(trimmedUsername, password, "Doctor");
        if (userId == -1) {
            return "Failed to create account. Please try again.";
        }

        boolean ok = doctorDAO.registerDoctor(userId, doctorName.trim(), specialization.trim(), phone.trim());

        if (!ok) {
            return "Account created but doctor profile failed.";
        }

        return null; // success
    }

    public String registerNurse(String username, String password, String confirmPassword,
                                String nurseName, String phone) {

        String error = validateCredentials(username, password, confirmPassword);
        if (error != null) {
            return error;
        }
        if (Validator.isEmpty(nurseName)) {
            return "Nurse name is required.";
        }
        if (Validator.isEmpty(phone)) {
            return "Phone number is required.";
        }

        String trimmedUsername = username.trim();
        if (userDAO.usernameExists(trimmedUsername)) {
            return "Username is already taken.";
        }

        int userId = userDAO.registerUser(trimmedUsername, password, "Nurse");
        if (userId == -1) {
            return "Failed to create account. Please try again.";
        }

        boolean ok = nurseDAO.registerNurse(userId, nurseName.trim(), phone.trim());

        if (!ok) {
            return "Account created but nurse profile failed.";
        }

        return null; // success
    }

    private String validateCredentials(String username, String password, String confirmPassword) {

        if (!Validator.isValidUsername(username)) {
            return "Username must be 3-20 characters (letters, numbers, underscore only).";
        }
        if (Validator.isEmpty(password)) {
            return "Password cannot be empty.";
        }
        if (password.length() < 6) {
            return "Password must be at least 6 characters.";
        }
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match.";
        }

        return null;
    }
}