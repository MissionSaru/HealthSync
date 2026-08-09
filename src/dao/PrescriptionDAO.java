package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Prescription;

public class PrescriptionDAO {

    // ADD PRESCRIPTION

    public boolean addPrescription(Prescription prescription) {

        String sql =
                "INSERT INTO prescriptions(patient_id, doctor_id, prescription_date, " +
                "medicine_id, dosage, duration, notes) " +
                "VALUES(?,?,?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, prescription.getPatientId());
            ps.setInt(2, prescription.getDoctorId());
            ps.setDate(3, prescription.getPrescriptionDate());
            ps.setInt(4, prescription.getMedicineId());
            ps.setString(5, prescription.getDosage());
            ps.setString(6, prescription.getDuration());
            ps.setString(7, prescription.getNotes());

            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Prescription Error: " + e.getMessage());
        }

        return false;
    }


    // GET PRESCRIPTIONS FOR PATIENT

    public ArrayList<Prescription> getPrescriptionsByPatientId(int patientId) {

        ArrayList<Prescription> list = new ArrayList<>();

        String sql =
                "SELECT p.*, m.medicine_name " +
                "FROM prescriptions p " +
                "LEFT JOIN medicines m ON p.medicine_id = m.medicine_id " +
                "WHERE p.patient_id=? " +
                "ORDER BY p.prescription_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, patientId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Prescription p = new Prescription(
                        rs.getInt("prescription_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getDate("prescription_date"),
                        rs.getInt("medicine_id"),
                        rs.getString("medicine_name"),
                        rs.getString("dosage"),
                        rs.getString("duration"),
                        rs.getString("notes")
                );

                list.add(p);
            }

        } catch (Exception e) {
            System.out.println("Prescription Fetch Error: " + e.getMessage());
        }

        return list;
    }


    // GET PRESCRIPTIONS FOR NURSE (all assigned patients, with patient names)

    public ArrayList<Prescription> getPrescriptionsForNurse(int nurseId) {

        ArrayList<Prescription> list = new ArrayList<>();

        String sql =
                "SELECT p.*, m.medicine_name, pat.name AS patient_name " +
                "FROM prescriptions p " +
                "JOIN medicines m ON p.medicine_id = m.medicine_id " +
                "JOIN nurse_patient np ON p.patient_id = np.patient_id " +
                "JOIN patients pat ON p.patient_id = pat.patient_id " +
                "WHERE np.nurse_id=? " +
                "ORDER BY p.prescription_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nurseId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Prescription p = new Prescription(
                        rs.getInt("prescription_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getDate("prescription_date"),
                        rs.getInt("medicine_id"),
                        rs.getString("medicine_name"),
                        rs.getString("dosage"),
                        rs.getString("duration"),
                        rs.getString("notes")
                );

                p.setPatientName(rs.getString("patient_name"));

                list.add(p);
            }

        } catch (Exception e) {
            System.out.println("Nurse Prescription Fetch Error: " + e.getMessage());
        }

        return list;
    }
}