package view.nurse;

import dao.PrescriptionDAO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Prescription;

public class NursePrescriptions extends JFrame {

    private int nurseId;
    private DefaultTableModel tableModel;

    public NursePrescriptions(int nurseId) {

        this.nurseId = nurseId;

        setTitle("Patient Prescriptions");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        String[] columns = {
                "Date",
                "Patient",
                "Medicine",
                "Dosage",
                "Duration",
                "Notes"
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

        JButton refreshButton = new JButton("Refresh");

        JPanel bottom = new JPanel();
        bottom.add(refreshButton);

        add(bottom, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> loadPrescriptions());

        loadPrescriptions();

        setVisible(true);
    }

    private void loadPrescriptions() {

        tableModel.setRowCount(0);

        PrescriptionDAO dao = new PrescriptionDAO();

        ArrayList<Prescription> prescriptions = dao.getPrescriptionsForNurse(nurseId);

        for (Prescription p : prescriptions) {

            String medicineName = p.getMedicineName();
            String dosage = p.getDosage();
            String duration = p.getDuration();
            String notes = p.getNotes();

            tableModel.addRow(new Object[]{
                    p.getPrescriptionDate(),
                    p.getPatientName() != null ? p.getPatientName() : "ID " + p.getPatientId(),
                    medicineName != null ? medicineName : "—",
                    dosage != null && !dosage.isEmpty() ? dosage : "—",
                    duration != null && !duration.isEmpty() ? duration : "—",
                    notes != null && !notes.isEmpty() ? notes : "—"
            });
        }
    }
}