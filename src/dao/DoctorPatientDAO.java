package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Patient;

public class DoctorPatientDAO {

    public ArrayList<Patient> getPatientsByDoctorId(int doctorId) {

        ArrayList<Patient> patientList = new ArrayList<>();

        String sql =
                "SELECT p.* " +
                "FROM patients p " +
                "JOIN doctor_patient dp " +
                "ON p.patient_id = dp.patient_id " +
                "WHERE dp.doctor_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, doctorId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Patient patient = new Patient(

                        rs.getInt("patient_id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getDate("date_of_birth"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("blood_group")

                );

                patientList.add(patient);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return patientList;

    }


    // Adds a patient to the doctor's list. Returns true if already assigned or added.

    public boolean assignPatient(int doctorId, int patientId) {

        String checkSql = "SELECT 1 FROM doctor_patient WHERE doctor_id=? AND patient_id=?";
        String insertSql = "INSERT INTO doctor_patient(doctor_id, patient_id) VALUES(?,?)";

        try (Connection con = DBConnection.getConnection()) {

            try (PreparedStatement check = con.prepareStatement(checkSql)) {

                check.setInt(1, doctorId);
                check.setInt(2, patientId);

                ResultSet rs = check.executeQuery();

                if (rs.next()) {
                    return true; // already assigned
                }

            }

            try (PreparedStatement insert = con.prepareStatement(insertSql)) {

                insert.setInt(1, doctorId);
                insert.setInt(2, patientId);

                insert.executeUpdate();

                return true;

            }

        } catch (Exception e) {

            System.out.println("Doctor Patient Assign Error: " + e.getMessage());

        }

        return false;

    }

}