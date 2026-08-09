package view.doctor;

import dao.HealthRecordDAO;
import model.HealthRecord;
import model.Patient;
import view.RecordVitalsForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DoctorPatientList extends JFrame {

    public DoctorPatientList(ArrayList<Patient> patientList) {

        setTitle("My Patients");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {
                "Patient ID",
                "Name",
                "Gender",
                "Blood Group",
                "Phone",
                "Address",
                "Weight (kg)",
                "Height (m)",
                "Blood Pressure"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);

        table.setRowHeight(25);

        table.setFont(new Font("Arial", Font.PLAIN, 14));

        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        HealthRecordDAO healthDAO = new HealthRecordDAO();

        for (Patient patient : patientList) {

            HealthRecord record =
                    healthDAO.getHealthRecordByPatientId(
                            patient.getPatientId()
                    );

            model.addRow(new Object[]{

                    patient.getPatientId(),
                    patient.getName(),
                    patient.getGender(),
                    patient.getBloodGroup(),
                    patient.getPhone(),
                    patient.getAddress(),
                    record != null ? record.getWeight() : "—",
                    record != null ? record.getHeight() : "—",
                    record != null && record.getBloodPressure() != null
                            ? record.getBloodPressure() : "—"

            });

        }

        JScrollPane scrollPane = new JScrollPane(table);

        JButton recordButton = new JButton("Record Vitals");

        recordButton.addActionListener(e -> {

            int row = table.getSelectedRow();

            Patient preselect =
                    (row >= 0 && row < patientList.size())
                            ? patientList.get(row)
                            : null;

            new RecordVitalsForm(patientList, preselect);

        });

        JPanel southPanel = new JPanel();
        southPanel.add(recordButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);

    }

}