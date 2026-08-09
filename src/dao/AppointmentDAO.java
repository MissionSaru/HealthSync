package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Appointment;

public class AppointmentDAO {

    public ArrayList<Appointment> getAppointmentsByPatientId(int patientId) {

        ArrayList<Appointment> list = new ArrayList<>();

        String query =
                "SELECT a.*, d.doctor_name " +
                "FROM appointments a " +
                "LEFT JOIN doctors d ON a.doctor_id = d.doctor_id " +
                "WHERE a.patient_id=? " +
                "ORDER BY a.appointment_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, patientId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new Appointment(
                        rs.getInt("appointment_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getDate("appointment_date"),
                        rs.getString("status"),
                        rs.getString("doctor_name"),
                        null
                ));
            }

        } catch (Exception e) {
            System.out.println("Appointment Error: " + e.getMessage());
        }

        return list;
    }

    public ArrayList<Appointment> getAppointmentsByDoctorId(int doctorId) {

        ArrayList<Appointment> list = new ArrayList<>();

        String query =
                "SELECT a.*, p.name AS patient_name " +
                "FROM appointments a " +
                "LEFT JOIN patients p ON a.patient_id = p.patient_id " +
                "WHERE a.doctor_id=? " +
                "ORDER BY a.appointment_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, doctorId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new Appointment(
                        rs.getInt("appointment_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getDate("appointment_date"),
                        rs.getString("status"),
                        null,
                        rs.getString("patient_name")
                ));
            }

        } catch (Exception e) {
            System.out.println("Doctor Appointment Error: " + e.getMessage());
        }

        return list;
    }


    // Books a new appointment with status 'Scheduled'. Returns generated id, or -1 on failure.
    public int scheduleAppointment(int patientId, int doctorId, java.sql.Date appointmentDate) {

        String query =
                "INSERT INTO appointments(patient_id, doctor_id, appointment_date, status) " +
                "VALUES(?,?,?,'Scheduled')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setDate(3, appointmentDate);

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getInt(1);
                }
            }

        } catch (Exception e) {
            System.out.println("Appointment Schedule Error: " + e.getMessage());
        }

        return -1;
    }


    // Updates appointment status (Scheduled / Completed / Cancelled).
    public boolean updateAppointmentStatus(int appointmentId, String status) {

        String query = "UPDATE appointments SET status=? WHERE appointment_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setInt(2, appointmentId);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Appointment Update Error: " + e.getMessage());
        }

        return false;
    }
}