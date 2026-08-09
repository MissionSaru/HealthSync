package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Nurse;
import model.Patient;

public class NurseDAO {

    public Nurse getNurseByUserId(int userId) {

        String query = "SELECT * FROM nurses WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Nurse(
                        rs.getInt("nurse_id"),
                        rs.getInt("user_id"),
                        rs.getString("nurse_name"),
                        rs.getString("phone")
                );
            }

        } catch (Exception e) {
            System.out.println("Nurse Error: " + e.getMessage());
        }

        return null;
    }


    public ArrayList<Patient> getPatientsByNurseId(int nurseId) {

        ArrayList<Patient> patientList = new ArrayList<>();

        String sql =
                "SELECT p.* FROM patients p " +
                "JOIN nurse_patient np ON p.patient_id = np.patient_id " +
                "WHERE np.nurse_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nurseId);

            ResultSet rs = ps.executeQuery();

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
            System.out.println("Nurse Patient Error: " + e.getMessage());
        }

        return patientList;
    }


    public boolean assignPatient(int nurseId, int patientId) {

        String checkSql = "SELECT 1 FROM nurse_patient WHERE nurse_id=? AND patient_id=?";
        String insertSql = "INSERT INTO nurse_patient(nurse_id, patient_id) VALUES(?,?)";

        try (Connection con = DBConnection.getConnection()) {

            try (PreparedStatement check = con.prepareStatement(checkSql)) {

                check.setInt(1, nurseId);
                check.setInt(2, patientId);

                ResultSet rs = check.executeQuery();

                if (rs.next()) {
                    return true;
                }
            }

            try (PreparedStatement insert = con.prepareStatement(insertSql)) {

                insert.setInt(1, nurseId);
                insert.setInt(2, patientId);

                insert.executeUpdate();

                return true;
            }

        } catch (Exception e) {
            System.out.println("Nurse Patient Assign Error: " + e.getMessage());
        }

        return false;
    }


    // Registers a new nurse profile linked to a user account. Returns true on success.
    public boolean registerNurse(int userId, String nurseName, String phone) {

        String query = "INSERT INTO nurses(user_id, nurse_name, phone) VALUES(?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, userId);
            ps.setString(2, nurseName);
            ps.setString(3, phone);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Nurse Registration Error: " + e.getMessage());
        }

        return false;
    }
}
