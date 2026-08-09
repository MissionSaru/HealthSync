package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import model.MedicineSchedule;

public class MedicineScheduleDAO {

    public boolean addSchedule(int patientId, int medicineId, String dosage, Timestamp scheduledTime) {

        String sql =
                "INSERT INTO medicine_schedule(patient_id, medicine_id, dosage, scheduled_time, status) " +
                "VALUES(?,?,?,?,'Pending')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, patientId);
            ps.setInt(2, medicineId);
            ps.setString(3, dosage);
            ps.setTimestamp(4, scheduledTime);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Medicine Schedule Add Error: " + e.getMessage());

        }

        return false;

    }

    public ArrayList<MedicineSchedule> getScheduleForNurse(int nurseId) {

        ArrayList<MedicineSchedule> list = new ArrayList<>();

        String sql =
                "SELECT ms.*, p.name AS patient_name, m.medicine_name " +
                "FROM medicine_schedule ms " +
                "JOIN nurse_patient np ON np.patient_id = ms.patient_id " +
                "JOIN patients p ON p.patient_id = ms.patient_id " +
                "JOIN medicines m ON m.medicine_id = ms.medicine_id " +
                "WHERE np.nurse_id = ? " +
                "ORDER BY ms.scheduled_time";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nurseId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                MedicineSchedule schedule = mapRow(rs);

                schedule.setPatientName(rs.getString("patient_name"));
                schedule.setMedicineName(rs.getString("medicine_name"));

                list.add(schedule);

            }

        } catch (Exception e) {

            System.out.println("Medicine Schedule Fetch Error: " + e.getMessage());

        }

        return list;

    }

    public ArrayList<MedicineSchedule> getScheduleByPatientId(int patientId) {

        ArrayList<MedicineSchedule> list = new ArrayList<>();

        String sql =
                "SELECT ms.*, m.medicine_name " +
                "FROM medicine_schedule ms " +
                "JOIN medicines m ON m.medicine_id = ms.medicine_id " +
                "WHERE ms.patient_id = ? " +
                "ORDER BY ms.scheduled_time";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, patientId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                MedicineSchedule schedule = mapRow(rs);

                schedule.setMedicineName(rs.getString("medicine_name"));

                list.add(schedule);

            }

        } catch (Exception e) {

            System.out.println("Medicine Schedule Fetch Error: " + e.getMessage());

        }

        return list;

    }

    public boolean markAdministered(int scheduleId, int nurseId) {

        String sql =
                "UPDATE medicine_schedule " +
                "SET status='Administered', administered_by=?, administered_at=? " +
                "WHERE schedule_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nurseId);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setInt(3, scheduleId);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println("Medicine Schedule Update Error: " + e.getMessage());

        }

        return false;

    }

    private MedicineSchedule mapRow(ResultSet rs) throws Exception {

        Integer administeredBy = rs.getObject("administered_by") != null
                ? rs.getInt("administered_by")
                : null;

        return new MedicineSchedule(
                rs.getInt("schedule_id"),
                rs.getInt("patient_id"),
                rs.getInt("medicine_id"),
                rs.getString("dosage"),
                rs.getTimestamp("scheduled_time"),
                rs.getString("status"),
                administeredBy,
                rs.getTimestamp("administered_at")
        );

    }

}