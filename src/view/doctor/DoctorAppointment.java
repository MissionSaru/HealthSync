package view.doctor;

import dao.AppointmentDAO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Appointment;

public class DoctorAppointment extends JFrame {

    private int doctorId;
    private DefaultTableModel model;

    public DoctorAppointment(int doctorId) {

        this.doctorId = doctorId;

        setTitle("My Appointments");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        String[] columns = {
                "Appointment ID",
                "Patient",
                "Appointment Date",
                "Status"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);

        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton acceptButton = new JButton("Accept");
        JButton completeButton = new JButton("Mark Completed");
        JButton cancelButton = new JButton("Cancel");
        JButton refreshButton = new JButton("Refresh");

        JPanel south = new JPanel();
        south.add(acceptButton);
        south.add(completeButton);
        south.add(cancelButton);
        south.add(refreshButton);

        add(south, BorderLayout.SOUTH);

        loadAppointments();

        acceptButton.addActionListener(e -> updateStatus(table, "Accepted"));
        completeButton.addActionListener(e -> updateStatus(table, "Completed"));
        cancelButton.addActionListener(e -> updateStatus(table, "Cancelled"));
        refreshButton.addActionListener(e -> loadAppointments());

        setVisible(true);
    }

    private void loadAppointments() {

        model.setRowCount(0);

        AppointmentDAO dao = new AppointmentDAO();

        ArrayList<Appointment> appointments = dao.getAppointmentsByDoctorId(doctorId);

        for (Appointment appointment : appointments) {

            String patientName = appointment.getPatientName();

            model.addRow(new Object[]{
                    appointment.getAppointmentId(),
                    patientName != null ? patientName : "ID " + appointment.getPatientId(),
                    appointment.getAppointmentDate(),
                    appointment.getStatus()
            });
        }
    }

    private void updateStatus(JTable table, String status) {

        int row = table.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an appointment first.");
            return;
        }

        int appointmentId = (Integer) model.getValueAt(row, 0);

        AppointmentDAO dao = new AppointmentDAO();

        if (dao.updateAppointmentStatus(appointmentId, status)) {

            JOptionPane.showMessageDialog(this, "Appointment marked as " + status);
            loadAppointments();

        } else {

            JOptionPane.showMessageDialog(this, "Could not update appointment.");
        }
    }
}