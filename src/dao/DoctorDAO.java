package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Doctor;

public class DoctorDAO {

    public Doctor getDoctorByUserId(int userId) {

        String query =
                "SELECT * FROM doctors WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Doctor(
                        rs.getInt("doctor_id"),
                        rs.getInt("user_id"),
                        rs.getString("doctor_name"),
                        rs.getString("specialization"),
                        rs.getString("phone")
                );
            }

        } catch (Exception e) {
            System.out.println("Doctor Error: " + e.getMessage());
        }

        return null;
    }


    // Registers a new doctor profile linked to a user account. Returns true on success.
    public boolean registerDoctor(int userId, String doctorName, String specialization, String phone) {

        String query = "INSERT INTO doctors(user_id, doctor_name, specialization, phone) VALUES(?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, userId);
            ps.setString(2, doctorName);
            ps.setString(3, specialization);
            ps.setString(4, phone);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Doctor Registration Error: " + e.getMessage());
        }

        return false;
    }


    // Returns all doctors, sorted by name (used for booking dropdowns).
    public ArrayList<Doctor> getAllDoctors() {

        ArrayList<Doctor> list = new ArrayList<>();

        String query = "SELECT * FROM doctors ORDER BY doctor_name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                list.add(new Doctor(
                        rs.getInt("doctor_id"),
                        rs.getInt("user_id"),
                        rs.getString("doctor_name"),
                        rs.getString("specialization"),
                        rs.getString("phone")
                ));
            }

        } catch (Exception e) {
            System.out.println("Doctor List Error: " + e.getMessage());
        }

        return list;
    }
}