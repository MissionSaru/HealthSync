package view.doctor;

import dao.DoctorPatientDAO;
import dao.MedicineDAO;
import dao.PrescriptionDAO;
import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.*;
import model.Medicine;
import model.Patient;
import model.Prescription;

public class AddPrescription extends JFrame {

    private final int doctorId;

    private final JComboBox<Patient> patientBox;
    private final JComboBox<Medicine> medicineBox;
    private final JTextField dosageField;
    private final JTextField durationField;
    private final JTextArea notesArea;
    private final JButton saveButton;

    public AddPrescription(int doctorId) {

        this.doctorId = doctorId;

        setTitle("Add Prescription");
        setSize(520, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        patientBox = new JComboBox<>();
        medicineBox = new JComboBox<>();
        dosageField = new JTextField();
        durationField = new JTextField();
        notesArea = new JTextArea();
        saveButton = new JButton("Save Prescription");

        loadPatients();
        loadMedicines();

        JPanel top = new JPanel(new GridLayout(4, 2, 8, 8));

        top.add(new JLabel("Patient:"));
        top.add(patientBox);
        top.add(new JLabel("Medicine:"));
        top.add(medicineBox);
        top.add(new JLabel("Dosage (e.g. 1 tablet twice daily):"));
        top.add(dosageField);
        top.add(new JLabel("Duration (e.g. 5 days):"));
        top.add(durationField);

        JPanel center = new JPanel(new BorderLayout(0, 5));
        center.add(new JLabel("Notes (optional):"), BorderLayout.NORTH);
        center.add(new JScrollPane(notesArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(saveButton);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> savePrescription());

        setVisible(true);
    }

    private void loadPatients() {

        DoctorPatientDAO dao = new DoctorPatientDAO();

        ArrayList<Patient> patients = dao.getPatientsByDoctorId(doctorId);

        for (Patient p : patients) {
            patientBox.addItem(p);
        }
    }

    private void loadMedicines() {

        MedicineDAO dao = new MedicineDAO();

        ArrayList<Medicine> medicines = dao.getAllMedicines();

        for (Medicine m : medicines) {
            medicineBox.addItem(m);
        }
    }

    private void savePrescription() {

        Patient patient = (Patient) patientBox.getSelectedItem();
        Medicine medicine = (Medicine) medicineBox.getSelectedItem();
        String dosage = dosageField.getText().trim();
        String duration = durationField.getText().trim();
        String notes = notesArea.getText().trim();

        if (patient == null) {
            JOptionPane.showMessageDialog(this, "Select patient");
            return;
        }

        if (medicine == null) {
            JOptionPane.showMessageDialog(this, "Select medicine");
            return;
        }

        if (dosage.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter dosage");
            return;
        }

        if (duration.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter duration");
            return;
        }

        Prescription prescription = new Prescription(
                0,
                patient.getPatientId(),
                doctorId,
                new Date(System.currentTimeMillis()),
                medicine.getMedicineId(),
                medicine.getName(),
                dosage,
                duration,
                notes
        );

        PrescriptionDAO dao = new PrescriptionDAO();

        boolean result = dao.addPrescription(prescription);

        if (result) {

            JOptionPane.showMessageDialog(this, "Prescription Saved");

            dosageField.setText("");
            durationField.setText("");
            notesArea.setText("");

        } else {

            JOptionPane.showMessageDialog(this, "Failed to save prescription");
        }
    }
}