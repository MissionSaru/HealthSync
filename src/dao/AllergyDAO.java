package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Allergy;

public class AllergyDAO {

    public ArrayList<Allergy> getAllergiesByPatientId(int patientId) {

        ArrayList<Allergy> list = new ArrayList<>();

        String query =
                "SELECT * FROM patient_allergies WHERE patient_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, patientId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Allergy allergy = new Allergy(
                        rs.getInt("allergy_id"),
                        rs.getInt("patient_id"),
                        rs.getString("allergy_name"),
                        rs.getString("description"),
                        rs.getString("severity"),
                        rs.getDate("added_date")
                );

                list.add(allergy);
            }

        } catch (Exception e) {
            System.out.println("Allergy Error: " + e.getMessage());
        }

        return list;
    }


    // Adds a new allergy for a patient. Returns true on success.
    public boolean addAllergy(int patientId, String allergyName, String description, String severity) {

        String query =
                "INSERT INTO patient_allergies(patient_id, allergy_name, description, severity, added_date) " +
                "VALUES(?,?,?,?,CURRENT_DATE)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, patientId);
            ps.setString(2, allergyName);
            ps.setString(3, description);
            ps.setString(4, severity);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Allergy Add Error: " + e.getMessage());
        }

        return false;
    }
}