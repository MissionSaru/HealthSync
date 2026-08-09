package dao;

import database.DBConnection;
import model.HealthRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HealthRecordDAO {


    public HealthRecord getHealthRecordByPatientId(int patientId) {


        HealthRecord record = null;


        String query = "SELECT * FROM health_records WHERE patient_id=? ORDER BY record_id DESC LIMIT 1";


        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)) {


            ps.setInt(1, patientId);


            ResultSet rs = ps.executeQuery();


            if(rs.next()) {


                record = new HealthRecord(

                        rs.getInt("record_id"),
                        rs.getInt("patient_id"),
                        rs.getDouble("height"),
                        rs.getDouble("weight"),
                        rs.getDouble("bmi"),
                        rs.getString("blood_pressure"),
                        rs.getDouble("temperature"),
                        rs.getInt("heart_rate"),
                        rs.getDouble("spo2"),
                        rs.getDate("recorded_date")

                );

            }


        } catch(Exception e) {

            System.out.println("Health Record Error: " + e.getMessage());

        }


        return record;

    }


    // Saves a new vitals entry for a patient (keeps history; the latest is shown).

    public java.util.ArrayList<HealthRecord> getAllHealthRecordsByPatientId(int patientId) {

        java.util.ArrayList<HealthRecord> list = new java.util.ArrayList<>();

        String query = "SELECT * FROM health_records WHERE patient_id=? ORDER BY record_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, patientId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new HealthRecord(
                        rs.getInt("record_id"),
                        rs.getInt("patient_id"),
                        rs.getDouble("height"),
                        rs.getDouble("weight"),
                        rs.getDouble("bmi"),
                        rs.getString("blood_pressure"),
                        rs.getDouble("temperature"),
                        rs.getInt("heart_rate"),
                        rs.getDouble("spo2"),
                        rs.getDate("recorded_date")
                ));

            }

        } catch (Exception e) {

            System.out.println("Health Record List Error: " + e.getMessage());

        }

        return list;

    }


    public boolean saveHealthRecord(int patientId, double height, double weight, double bmi,
                                    String bloodPressure, double temperature, int heartRate,
                                    double spo2, java.sql.Date recordedDate) {

        String query = "INSERT INTO health_records(patient_id, height, weight, bmi, recorded_date, " +
                       "blood_pressure, temperature, heart_rate, spo2) " +
                       "VALUES(?,?,?,?,?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, patientId);
            ps.setDouble(2, height);
            ps.setDouble(3, weight);
            ps.setDouble(4, bmi);
            ps.setDate(5, recordedDate);
            ps.setString(6, bloodPressure);
            ps.setDouble(7, temperature);
            ps.setInt(8, heartRate);
            ps.setDouble(9, spo2);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Health Record Save Error: " + e.getMessage());

        }

        return false;

    }

}