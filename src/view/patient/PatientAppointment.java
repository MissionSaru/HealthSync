package view.patient;

import java.util.ArrayList;
import javax.swing.*;
import model.Appointment;

public class PatientAppointment extends JFrame {

    public PatientAppointment(ArrayList<Appointment> appointmentList) {

        setTitle("Patient Appointments");
        setSize(500, 400);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        for (Appointment appointment : appointmentList) {

            area.append("Appointment ID: " + appointment.getAppointmentId() + "\n");

            String doctorName = appointment.getDoctorName();

            area.append("Doctor: "
                    + (doctorName != null
                        ? doctorName
                        : "ID " + appointment.getDoctorId())
                    + "\n");

            area.append("Date: " + appointment.getAppointmentDate() + "\n");
            area.append("Status: " + appointment.getStatus() + "\n");
            area.append("------------------------\n");
        }

        add(new JScrollPane(area));

        setVisible(true);
    }
}