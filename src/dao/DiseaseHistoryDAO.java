package dao;

import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import model.DiseaseHistory;

public class DiseaseHistoryDAO {


    public ArrayList<DiseaseHistory> getDiseaseHistoryByPatientId(int patientId) {


        ArrayList<DiseaseHistory> list = new ArrayList<>();


       String query =
        "SELECT h.*, d.disease_name " +
        "FROM patient_disease_history h " +
        "JOIN diseases d ON h.disease_id = d.disease_id " +
        "WHERE h.patient_id=?";


        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)) {


            ps.setInt(1, patientId);


            ResultSet rs = ps.executeQuery();


            while(rs.next()) {


                DiseaseHistory history =
                        new DiseaseHistory(

                        rs.getInt("history_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("disease_id"),
                        rs.getInt("added_by_doctor"),
                        rs.getString("disease_type"),
                        rs.getString("status"),
                        rs.getDate("diagnosed_date"),
                        rs.getString("notes")

                ); 
                history.setDiseaseName(
        rs.getString("disease_name")
);


                list.add(history);

            }


        }
        catch(Exception e){

            System.out.println(
                    "Disease History Error: "
                    + e.getMessage()
            );

        }


        return list;

    }
    public boolean addDiseaseHistory(DiseaseHistory history) {

    String sql =
            "INSERT INTO patient_disease_history " +
            "(patient_id, disease_id, added_by_doctor, disease_type, status, diagnosed_date, notes) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setInt(1, history.getPatientId());
        ps.setInt(2, history.getDiseaseId());
        ps.setInt(3, history.getAddedByDoctor());
        ps.setString(4, history.getDiseaseType());
        ps.setString(5, history.getStatus());
        ps.setDate(6, history.getDiagnosedDate());
        ps.setString(7, history.getNotes());

        ps.executeUpdate();

        return true;

    } catch (Exception e) {

        System.out.println(
                "Add Disease History Error: "
                + e.getMessage()
        );

    }

    return false;
}
}
