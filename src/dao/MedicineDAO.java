package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Medicine;

public class MedicineDAO {

    public ArrayList<Medicine> getAllMedicines() {

        ArrayList<Medicine> list = new ArrayList<>();

        String query = "SELECT * FROM medicines ORDER BY medicine_name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                list.add(new Medicine(
                        rs.getInt("medicine_id"),
                        rs.getString("medicine_name"),
                        rs.getString("description")
                ));

            }

        } catch (Exception e) {

            System.out.println("Medicine Fetch Error: " + e.getMessage());

        }

        return list;

    }

    // Finds an existing medicine by name, or inserts a new one. Returns medicine_id, or -1 on failure.

    public int addMedicine(String name, String description) {

        String findSql = "SELECT medicine_id FROM medicines WHERE medicine_name=?";

        try (Connection con = DBConnection.getConnection()) {

            try (PreparedStatement find = con.prepareStatement(findSql)) {

                find.setString(1, name);

                ResultSet rs = find.executeQuery();

                if (rs.next()) {
                    return rs.getInt("medicine_id");
                }

            }

            String insertSql = "INSERT INTO medicines(medicine_name, description) VALUES(?,?)";

            try (PreparedStatement insert = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

                insert.setString(1, name);
                insert.setString(2, description);

                insert.executeUpdate();

                try (ResultSet keys = insert.getGeneratedKeys()) {

                    if (keys.next()) {
                        return keys.getInt(1);
                    }

                }

            }

        } catch (Exception e) {

            System.out.println("Medicine Add Error: " + e.getMessage());

        }

        return -1;

    }

}