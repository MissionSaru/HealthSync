package view.nurse;

import dao.MedicineDAO;
import dao.MedicineScheduleDAO;
import dao.NurseDAO;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Medicine;
import model.MedicineSchedule;
import model.Patient;

public class MedicineAdministration extends JFrame {

    private int nurseId;

    private MedicineScheduleDAO scheduleDAO;
    private MedicineDAO medicineDAO;
    private NurseDAO nurseDAO;

    private DefaultTableModel tableModel;
    private JTable table;

    private ArrayList<MedicineSchedule> currentSchedule;

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public MedicineAdministration(int nurseId) {

        this.nurseId = nurseId;

        this.scheduleDAO = new MedicineScheduleDAO();
        this.medicineDAO = new MedicineDAO();
        this.nurseDAO = new NurseDAO();

        setTitle("Medicine Administration");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));


        String[] columns = {
                "Schedule ID",
                "Patient",
                "Medicine",
                "Dosage",
                "Scheduled Time",
                "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);

        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));

        add(new JScrollPane(table), BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JButton administerButton = new JButton("Mark as Administered");
        JButton addButton = new JButton("Add Schedule");
        JButton refreshButton = new JButton("Refresh");

        bottomPanel.add(administerButton);
        bottomPanel.add(addButton);
        bottomPanel.add(refreshButton);

        add(bottomPanel, BorderLayout.SOUTH);


        loadSchedule();


        administerButton.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row < 0) {

                JOptionPane.showMessageDialog(this, "Select a schedule row first");
                return;

            }

            MedicineSchedule selected = currentSchedule.get(row);

            if ("Administered".equalsIgnoreCase(selected.getStatus())) {

                JOptionPane.showMessageDialog(this, "This dose has already been administered");
                return;

            }

            boolean success = scheduleDAO.markAdministered(selected.getScheduleId(), nurseId);

            if (success) {

                JOptionPane.showMessageDialog(this, "Dose marked as administered");
                loadSchedule();

            } else {

                JOptionPane.showMessageDialog(this, "Failed to update schedule");

            }

        });


        addButton.addActionListener(e -> openAddScheduleDialog());


        refreshButton.addActionListener(e -> loadSchedule());


        setVisible(true);

    }

    private void loadSchedule() {

        tableModel.setRowCount(0);

        currentSchedule = scheduleDAO.getScheduleForNurse(nurseId);

        for (MedicineSchedule schedule : currentSchedule) {

            tableModel.addRow(new Object[]{
                    schedule.getScheduleId(),
                    schedule.getPatientName(),
                    schedule.getMedicineName(),
                    schedule.getDosage(),
                    DATE_FORMAT.format(schedule.getScheduledTime()),
                    schedule.getStatus()
            });

        }

    }

    private void openAddScheduleDialog() {

        ArrayList<Patient> assignedPatients = nurseDAO.getPatientsByNurseId(nurseId);

        if (assignedPatients.isEmpty()) {

            JOptionPane.showMessageDialog(this, "No patients assigned to you yet. Assign a patient first.");
            return;

        }

        ArrayList<Medicine> medicines = medicineDAO.getAllMedicines();

        JComboBox<Patient> patientBox = new JComboBox<>(assignedPatients.toArray(new Patient[0]));
        JComboBox<Medicine> medicineBox = new JComboBox<>(medicines.toArray(new Medicine[0]));

        JTextField newMedicineField = new JTextField();
        JTextField dosageField = new JTextField();
        JTextField timeField = new JTextField(DATE_FORMAT.format(new Date()));

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));

        panel.add(new JLabel("Patient:"));
        panel.add(patientBox);

        panel.add(new JLabel("Existing Medicine (or leave blank to add new below):"));
        panel.add(medicineBox);

        panel.add(new JLabel("New Medicine Name (optional):"));
        panel.add(newMedicineField);

        panel.add(new JLabel("Dosage (e.g. 500mg):"));
        panel.add(dosageField);

        panel.add(new JLabel("Scheduled Time (yyyy-MM-dd HH:mm):"));
        panel.add(timeField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Add Medicine Schedule",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        Patient selectedPatient = (Patient) patientBox.getSelectedItem();

        if (selectedPatient == null) {

            JOptionPane.showMessageDialog(this, "Select a patient");
            return;

        }

        int medicineId;

        String newMedicineName = newMedicineField.getText().trim();

        if (!newMedicineName.isEmpty()) {

           medicineId = medicineDAO.addMedicine(newMedicineName, "");

            if (medicineId == -1) {

                JOptionPane.showMessageDialog(this, "Failed to add new medicine");
                return;

            }

        } else {

            Medicine selectedMedicine = (Medicine) medicineBox.getSelectedItem();

            if (selectedMedicine == null) {

                JOptionPane.showMessageDialog(this, "Select an existing medicine, or enter a new medicine name");
                return;

            }

            medicineId = selectedMedicine.getMedicineId();

        }

        String dosage = dosageField.getText().trim();

        Timestamp scheduledTime;

        try {

            scheduledTime = new Timestamp(DATE_FORMAT.parse(timeField.getText().trim()).getTime());

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Invalid date/time format. Use yyyy-MM-dd HH:mm");
            return;

        }

        boolean success = scheduleDAO.addSchedule(
                selectedPatient.getPatientId(), medicineId, dosage, scheduledTime
        );

        if (success) {

            JOptionPane.showMessageDialog(this, "Schedule added");
            loadSchedule();

        } else {

            JOptionPane.showMessageDialog(this, "Failed to add schedule");

        }

    }

}