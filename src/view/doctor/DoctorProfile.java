package view.doctor;

import model.Doctor;

import javax.swing.*;
import java.awt.*;

public class DoctorProfile extends JFrame {

    public DoctorProfile(Doctor doctor) {

        setTitle("Doctor Profile");
        setSize(450,350);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();

        area.setEditable(false);

        area.setFont(new Font("Arial", Font.PLAIN, 16));

        area.append("Doctor ID : " + doctor.getDoctorId() + "\n\n");

        area.append("Name : " + doctor.getDoctorName() + "\n\n");

        area.append("Specialization : " +
                doctor.getSpecialization() + "\n\n");

        area.append("Phone : " +
                doctor.getPhone());

        add(new JScrollPane(area));

        setVisible(true);
    }

}