package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Patient;

public class PatientDAO {


    public Patient getPatientByUserId(int userId) {

        Patient patient = null;


        String query = "SELECT * FROM patients WHERE user_id=?";


        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {


            ps.setInt(1, userId);


            ResultSet rs = ps.executeQuery();


            if(rs.next()) {

                patient = new Patient(
                        rs.getInt("patient_id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getDate("date_of_birth"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("blood_group")
                );

            }


            rs.close();


        } catch(Exception e) {

            System.out.println("Patient Load Error: " + e.getMessage());

        }


        return patient;
    }


    public java.util.ArrayList<Patient> getAllPatients() {

        java.util.ArrayList<Patient> patientList = new java.util.ArrayList<>();

        String query = "SELECT * FROM patients ORDER BY name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                patientList.add(new Patient(
                        rs.getInt("patient_id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getDate("date_of_birth"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("blood_group")
                ));

            }

        } catch (Exception e) {

            System.out.println("Patient List Error: " + e.getMessage());

        }

        return patientList;
    }


    // Registers a new patient row linked to a user account. Returns true on success.

    public boolean registerPatient(int userId, String name, java.sql.Date dateOfBirth,
                                    String gender, String phone, String address, String bloodGroup) {

        String query = "INSERT INTO patients(user_id, name, date_of_birth, gender, phone, address, blood_group) " +
                       "VALUES(?,?,?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, userId);
            ps.setString(2, name);
            ps.setDate(3, dateOfBirth);
            ps.setString(4, gender);
            ps.setString(5, phone);
            ps.setString(6, address);
            ps.setString(7, bloodGroup);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Patient Registration Error: " + e.getMessage());

        }

        return false;
    }
}