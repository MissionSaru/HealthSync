package view.nurse;

import dao.NurseDAO;
import dao.PatientDAO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Patient;

public class PatientAssignment extends JFrame {

    private int nurseId;

    private NurseDAO nurseDAO;
    private PatientDAO patientDAO;

    private DefaultTableModel tableModel;
    private JComboBox<Patient> patientDropdown;

    public PatientAssignment(int nurseId) {

        this.nurseId = nurseId;

        this.nurseDAO = new NurseDAO();
        this.patientDAO = new PatientDAO();

        setTitle("Patient Assignment");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));


        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        patientDropdown = new JComboBox<>();

        JButton assignButton = new JButton("Assign to Me");
        JButton refreshButton = new JButton("Refresh");

        topPanel.add(new JLabel("Select Patient:"));
        topPanel.add(patientDropdown);
        topPanel.add(assignButton);
        topPanel.add(refreshButton);

        add(topPanel, BorderLayout.NORTH);


        String[] columns = {
                "Patient ID",
                "Name",
                "Gender",
                "Blood Group",
                "Phone",
                "Address"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);

        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));

        add(new JScrollPane(table), BorderLayout.CENTER);


        loadPatientDropdown();
        loadAssignedPatients();


        assignButton.addActionListener(e -> {

            Patient selected = (Patient) patientDropdown.getSelectedItem();

            if (selected == null) {

                JOptionPane.showMessageDialog(this, "Please select a patient first");
                return;

            }

            boolean success = nurseDAO.assignPatient(nurseId, selected.getPatientId());

            if (success) {

                JOptionPane.showMessageDialog(this, selected.getName() + " has been assigned to you");
                loadAssignedPatients();

            } else {

                JOptionPane.showMessageDialog(this, "Could not assign patient");

            }

        });


        refreshButton.addActionListener(e -> {

            loadPatientDropdown();
            loadAssignedPatients();

        });


        setVisible(true);

    }

    private void loadPatientDropdown() {

        patientDropdown.removeAllItems();

        ArrayList<Patient> allPatients = patientDAO.getAllPatients();

        for (Patient patient : allPatients) {

            patientDropdown.addItem(patient);

        }

    }

    private void loadAssignedPatients() {

        tableModel.setRowCount(0);

        ArrayList<Patient> assigned = nurseDAO.getPatientsByNurseId(nurseId);

        for (Patient patient : assigned) {

            tableModel.addRow(new Object[]{
                    patient.getPatientId(),
                    patient.getName(),
                    patient.getGender(),
                    patient.getBloodGroup(),
                    patient.getPhone(),
                    patient.getAddress()
            });

        }

    }

}