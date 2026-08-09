package view;

import dao.HealthRecordDAO;
import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.Patient;
import service.BMIService;

public class RecordVitalsForm extends JFrame {

    private JComboBox<Patient> patientBox;
    private JTextField weightField;
    private JTextField heightField;
    private JTextField bloodPressureField;
    private JTextField temperatureField;
    private JTextField heartRateField;
    private JTextField spo2Field;
    private JLabel bmiLabel;

    public RecordVitalsForm(ArrayList<Patient> patients) {
        this(patients, null);
    }

    public RecordVitalsForm(ArrayList<Patient> patients, Patient preselect) {

        setTitle("Record Patient Vitals");
        setSize(430, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2, 8, 8));

        patientBox = new JComboBox<>(patients.toArray(new Patient[0]));
        if (preselect != null) {
            patientBox.setSelectedItem(preselect);
        }

        weightField = new JTextField();
        heightField = new JTextField();
        bloodPressureField = new JTextField();
        temperatureField = new JTextField();
        heartRateField = new JTextField();
        spo2Field = new JTextField();
        bmiLabel = new JLabel("—");

        JButton saveButton = new JButton("Save Vitals");
        JButton backButton = new JButton("Back");

        panel.add(new JLabel("Patient:"));
        panel.add(patientBox);

        panel.add(new JLabel("Weight (kg):"));
        panel.add(weightField);

        panel.add(new JLabel("Height (m):"));
        panel.add(heightField);

        panel.add(new JLabel("Blood Pressure (120/80):"));
        panel.add(bloodPressureField);

        panel.add(new JLabel("Temperature (°C):"));
        panel.add(temperatureField);

        panel.add(new JLabel("Heart Rate (bpm):"));
        panel.add(heartRateField);

        panel.add(new JLabel("SpO2 (%):"));
        panel.add(spo2Field);

        panel.add(new JLabel("BMI (auto):"));
        panel.add(bmiLabel);

        panel.add(saveButton);
        panel.add(backButton);

        add(panel);

        SimpleDocListener bmiListener = new SimpleDocListener(() -> updateBmiPreview());
        weightField.getDocument().addDocumentListener(bmiListener);
        heightField.getDocument().addDocumentListener(bmiListener);

        saveButton.addActionListener(e -> save());

        backButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void updateBmiPreview() {
        double weight = parseDouble(weightField.getText());
        double height = parseDouble(heightField.getText());
        double bmi = BMIService.calculateBMI(weight, height);
        bmiLabel.setText(bmi > 0 ? String.valueOf(bmi) : "—");
    }

    private double parseDouble(String text) {
        try {
            return Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int parseInt(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void save() {

        Patient patient = (Patient) patientBox.getSelectedItem();

        if (patient == null) {
            JOptionPane.showMessageDialog(this, "Select a patient first.");
            return;
        }

        double weight = parseDouble(weightField.getText());
        double height = parseDouble(heightField.getText());

        if (weight <= 0 || height <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Enter a valid weight (kg) and height (m).",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String bloodPressure = bloodPressureField.getText().trim();

        if (bloodPressure.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Blood pressure is required (e.g. 120/80).",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double temperature = parseDouble(temperatureField.getText());
        int heartRate = parseInt(heartRateField.getText());
        double spo2 = parseDouble(spo2Field.getText());

        if (temperature <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Enter a valid temperature (°C).",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (heartRate <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Enter a valid heart rate (bpm).",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (spo2 <= 0 || spo2 > 100) {
            JOptionPane.showMessageDialog(this,
                    "Enter a valid SpO2 percentage (0-100).",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double bmi = BMIService.calculateBMI(weight, height);

        HealthRecordDAO dao = new HealthRecordDAO();

        boolean ok = dao.saveHealthRecord(
                patient.getPatientId(), height, weight, bmi,
                bloodPressure, temperature, heartRate, spo2,
                new Date(System.currentTimeMillis())
        );

        if (ok) {
            JOptionPane.showMessageDialog(this,
                    "Vitals saved for " + patient.getName()
                            + " (BMI: " + bmi + " - " + BMIService.getCategory(bmi) + ")",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to save vitals. Check the database connection.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class SimpleDocListener implements DocumentListener {

        private final Runnable action;

        SimpleDocListener(Runnable action) {
            this.action = action;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            action.run();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            action.run();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            action.run();
        }
    }

}
