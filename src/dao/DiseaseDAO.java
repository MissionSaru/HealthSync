package dao;

import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Disease;

public class DiseaseDAO {

    public List<Disease> getAllDiseases() {

        List<Disease> diseases = new ArrayList<>();

        String sql =
                "SELECT disease_id, disease_name, description " +
                "FROM diseases " +
                "ORDER BY disease_name";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Disease disease = new Disease(
                        rs.getInt("disease_id"),
                        rs.getString("disease_name"),
                        rs.getString("description")
                );

                diseases.add(disease);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading diseases: "
                    + e.getMessage()
            );
        }

        return diseases;
    }


    // Finds an existing disease by name, or inserts a new one. Returns disease_id, or -1 on failure.
    public int addDisease(String diseaseName, String description) {

        String findSql = "SELECT disease_id FROM diseases WHERE disease_name=?";

        try (Connection con = DBConnection.getConnection()) {

            try (PreparedStatement find = con.prepareStatement(findSql)) {

                find.setString(1, diseaseName);

                ResultSet rs = find.executeQuery();

                if (rs.next()) {
                    return rs.getInt("disease_id");
                }
            }

            String insertSql = "INSERT INTO diseases(disease_name, description) VALUES(?,?)";

            try (PreparedStatement insert = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

                insert.setString(1, diseaseName);
                insert.setString(2, description);

                insert.executeUpdate();

                try (ResultSet keys = insert.getGeneratedKeys()) {

                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Disease Add Error: " + e.getMessage());
        }

        return -1;
    }
}