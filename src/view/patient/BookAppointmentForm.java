package view.patient;

import dao.AppointmentDAO;
import dao.DoctorDAO;
import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.*;
import model.Doctor;
import util.DateUtil;

public class BookAppointmentForm extends JFrame {

    private JComboBox<Doctor> doctorBox;
    private JTextField dateField;

    public BookAppointmentForm(int patientId) {

        setTitle("Book Appointment");
        setSize(400, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 8, 8));

        doctorBox = new JComboBox<>();
        dateField = new JTextField();

        DoctorDAO doctorDAO = new DoctorDAO();

        ArrayList<Doctor> doctors = doctorDAO.getAllDoctors();

        for (Doctor doctor : doctors) {
            doctorBox.addItem(doctor);
        }

        JButton bookButton = new JButton("Book");
        JButton cancelButton = new JButton("Cancel");

        panel.add(new JLabel("Doctor:"));
        panel.add(doctorBox);

        panel.add(new JLabel("Date (dd-MM-yyyy):"));
        panel.add(dateField);

        panel.add(bookButton);
        panel.add(cancelButton);

        add(panel);

        bookButton.addActionListener(e -> book(patientId));

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void book(int patientId) {

        Doctor doctor = (Doctor) doctorBox.getSelectedItem();

        if (doctor == null) {
            JOptionPane.showMessageDialog(this, "No doctors available. Please try again later.");
            return;
        }

        Date date = DateUtil.parseDate(dateField.getText());

        if (date == null) {
            JOptionPane.showMessageDialog(this, "Invalid date. Use dd-MM-yyyy format.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AppointmentDAO dao = new AppointmentDAO();

        int id = dao.scheduleAppointment(patientId, doctor.getDoctorId(), date);

        if (id != -1) {

            JOptionPane.showMessageDialog(this,
                    "Appointment booked with " + doctor.getDoctorName()
                            + " on " + date + " (Status: Scheduled)",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Could not book appointment. Check the database connection.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}