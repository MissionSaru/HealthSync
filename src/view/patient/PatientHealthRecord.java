package view.patient;

import model.HealthRecord;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PatientHealthRecord extends JFrame {

    public PatientHealthRecord(ArrayList<HealthRecord> records) {

        setTitle("Health Records");
        setSize(850, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {
                "Date",
                "Height (m)",
                "Weight (kg)",
                "BMI",
                "Blood Pressure",
                "Temperature (°C)",
                "Heart Rate (bpm)",
                "SpO2 (%)"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));

        for (HealthRecord record : records) {
            model.addRow(new Object[]{
                    record.getRecordedDate(),
                    record.getHeight() > 0 ? record.getHeight() : "—",
                    record.getWeight() > 0 ? record.getWeight() : "—",
                    record.getBmi() > 0 ? record.getBmi() : "—",
                    record.getBloodPressure() != null && !record.getBloodPressure().isEmpty()
                            ? record.getBloodPressure() : "—",
                    record.getTemperature() > 0 ? record.getTemperature() : "—",
                    record.getHeartRate() > 0 ? record.getHeartRate() : "—",
                    record.getSpo2() > 0 ? record.getSpo2() : "—"
            });
        }

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
