package view.doctor;

import dao.AllergyDAO;
import dao.DiseaseHistoryDAO;
import dao.DiseaseDAO;
import dao.DoctorPatientDAO;

import java.awt.GridLayout;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Disease;
import model.DiseaseHistory;
import model.Patient;

public class AddDiseaseHistory extends JFrame {

    private int doctorId;

    private JComboBox<Patient> patientBox;
    private JComboBox<Disease> diseaseBox;

    private JComboBox<String> diseaseTypeBox;
    private JComboBox<String> statusBox;

    private JTextField dateField;
    private JTextArea notesArea;

    // allergy fields (optional)
    private JTextField allergyNameField;
    private JTextField allergyDescriptionField;
    private JComboBox<String> severityBox;

    private JButton saveButton;


    public AddDiseaseHistory(int doctorId) {

        this.doctorId = doctorId;

        setTitle("Add Disease History");
        setSize(520, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        JPanel panel = new JPanel();

        panel.setLayout(
                new GridLayout(11, 2, 10, 10)
        );


        // =========================
        // PATIENT
        // =========================

        patientBox = new JComboBox<>();


        // =========================
        // DISEASE
        // =========================

        diseaseBox = new JComboBox<>();


        // =========================
        // DISEASE TYPE
        // =========================

        diseaseTypeBox =
                new JComboBox<>(
                        new String[]{
                                "Temporary",
                                "Chronic"
                        }
                );


        // =========================
        // STATUS
        // =========================

        statusBox =
                new JComboBox<>(
                        new String[]{
                                "Active",
                                "Recovered"
                        }
                );


        // =========================
        // DATE
        // =========================

        dateField = new JTextField();


        // =========================
        // NOTES
        // =========================

        notesArea = new JTextArea();


        // =========================
        // ALLERGY (OPTIONAL)
        // =========================

        allergyNameField = new JTextField();

        allergyDescriptionField = new JTextField();

        severityBox =
                new JComboBox<>(
                        new String[]{
                                "Mild",
                                "Moderate",
                                "Severe"
                        }
                );


        // =========================
        // SAVE BUTTON
        // =========================

        saveButton =
                new JButton(
                        "Save Disease History"
                );


        // =========================
        // ADD NEW DISEASE BUTTON
        // =========================

        JButton addDiseaseButton =
                new JButton(
                        "Add New Disease"
                );


        // Load data

        loadPatients();

        loadDiseases();


        // =========================
        // ADD COMPONENTS
        // =========================

        panel.add(
                new JLabel("Select Patient")
        );

        panel.add(patientBox);


        panel.add(
                new JLabel("Select Disease")
        );

        panel.add(diseaseBox);


        panel.add(
                new JLabel("New Disease?")
        );

        panel.add(addDiseaseButton);


        panel.add(
                new JLabel("Disease Type")
        );

        panel.add(diseaseTypeBox);


        panel.add(
                new JLabel("Status")
        );

        panel.add(statusBox);


        panel.add(
                new JLabel("Diagnosed Date (YYYY-MM-DD)")
        );

        panel.add(dateField);


        panel.add(
                new JLabel("Notes")
        );

        panel.add(
                new JScrollPane(notesArea)
        );


        panel.add(
                new JLabel("Allergy Name (optional)")
        );

        panel.add(allergyNameField);


        panel.add(
                new JLabel("Allergy Description (optional)")
        );

        panel.add(allergyDescriptionField);


        panel.add(
                new JLabel("Allergy Severity")
        );

        panel.add(severityBox);


        panel.add(
                new JLabel("")
        );

        panel.add(saveButton);


        add(panel);


        // =========================
        // ADD NEW DISEASE ACTION
        // =========================

        addDiseaseButton.addActionListener(
                e -> addNewDisease()
        );


        // =========================
        // SAVE ACTION
        // =========================

        saveButton.addActionListener(
                e -> saveDiseaseHistory()
        );


        setVisible(true);

    }


    // =====================================================
    // LOAD PATIENTS
    // =====================================================

    private void loadPatients() {

        DoctorPatientDAO dao =
                new DoctorPatientDAO();


        ArrayList<Patient> patients =
                dao.getPatientsByDoctorId(
                        doctorId
                );


        for (Patient patient : patients) {

            patientBox.addItem(patient);

        }

    }


    // =====================================================
    // LOAD DISEASES
    // =====================================================

    private void loadDiseases() {

        diseaseBox.removeAllItems();

        DiseaseDAO dao =
                new DiseaseDAO();


        for (Disease disease : dao.getAllDiseases()) {

            diseaseBox.addItem(disease);

        }

    }


    // =====================================================
    // ADD NEW DISEASE
    // =====================================================

    private void addNewDisease() {

        JTextField nameField =
                new JTextField();

        JTextField descField =
                new JTextField();


        Object[] fields = {
                "Disease Name:", nameField,
                "Description (optional):", descField
        };


        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        fields,
                        "Add New Disease",
                        JOptionPane.OK_CANCEL_OPTION
                );


        if (option != JOptionPane.OK_OPTION) {
            return;
        }


        String name =
                nameField.getText().trim();


        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Disease name is required"
            );

            return;

        }


        DiseaseDAO dao =
                new DiseaseDAO();


        int diseaseId =
                dao.addDisease(
                        name,
                        descField.getText().trim()
                );


        if (diseaseId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add disease"
            );

            return;

        }


        // Refresh the dropdown and select the new disease

        loadDiseases();


        for (int i = 0; i < diseaseBox.getItemCount(); i++) {

            Disease d =
                    diseaseBox.getItemAt(i);


            if (d.getDiseaseId() == diseaseId) {

                diseaseBox.setSelectedIndex(i);

                break;

            }

        }


        JOptionPane.showMessageDialog(
                this,
                "Disease added: " + name
        );

    }


    // =====================================================
    // SAVE DISEASE HISTORY (+ OPTIONAL ALLERGY)
    // =====================================================

    private void saveDiseaseHistory() {

        // Get patient

        Patient patient =
                (Patient) patientBox.getSelectedItem();


        if (patient == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a patient"
            );

            return;

        }


        // Get disease

        Disease disease =
                (Disease) diseaseBox.getSelectedItem();


        if (disease == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a disease"
            );

            return;

        }


        // Get date

        String dateText =
                dateField.getText().trim();


        if (dateText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter diagnosed date"
            );

            return;

        }


        Date diagnosedDate;


        try {

            diagnosedDate =
                    Date.valueOf(dateText);

        }
        catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Use date format: YYYY-MM-DD"
            );

            return;

        }


        // Get values

        String diseaseType =
                (String)
                diseaseTypeBox.getSelectedItem();


        String status =
                (String)
                statusBox.getSelectedItem();


        String notes =
                notesArea.getText().trim();


        // Create DiseaseHistory object

        DiseaseHistory history =
                new DiseaseHistory(

                        0,

                        patient.getPatientId(),

                        disease.getDiseaseId(),

                        doctorId,

                        diseaseType,

                        status,

                        diagnosedDate,

                        notes

                );


        // Save disease history

        DiseaseHistoryDAO dao =
                new DiseaseHistoryDAO();


        boolean result =
                dao.addDiseaseHistory(
                        history
                );


        if (result) {

            String message =
                    "Disease history saved successfully!";


            // Optional allergy

            String allergyName =
                    allergyNameField.getText().trim();


            if (!allergyName.isEmpty()) {

                String allergyDescription =
                        allergyDescriptionField.getText().trim();

                String severity =
                        (String)
                        severityBox.getSelectedItem();


                AllergyDAO allergyDAO =
                        new AllergyDAO();


                boolean allergyOk =
                        allergyDAO.addAllergy(
                                patient.getPatientId(),
                                allergyName,
                                allergyDescription,
                                severity
                        );


                if (allergyOk) {

                    message =
                            "Disease history and allergy saved successfully!";

                }
                else {

                    message =
                            "Disease history saved, but failed to save allergy.";

                }

            }


            JOptionPane.showMessageDialog(
                    this,
                    message
            );


            // Clear fields

            dateField.setText("");

            notesArea.setText("");

            allergyNameField.setText("");

            allergyDescriptionField.setText("");

        }
        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to save disease history"
            );

        }

    }

}