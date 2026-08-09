package view.patient;

import model.Patient;

import javax.swing.*;
import java.awt.*;

public class PatientProfile extends JFrame {


    public PatientProfile(Patient patient) {


        setTitle("Patient Profile");
        setSize(400,400);
        setLocationRelativeTo(null);


        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(8,1,10,10));


        panel.add(new JLabel("Name: " + patient.getName()));

        panel.add(new JLabel("Date of Birth: " 
                + patient.getDateOfBirth()));

        panel.add(new JLabel("Gender: " 
                + patient.getGender()));

        panel.add(new JLabel("Phone: " 
                + patient.getPhone()));

        panel.add(new JLabel("Address: " 
                + patient.getAddress()));

        panel.add(new JLabel("Blood Group: " 
                + patient.getBloodGroup()));


        add(panel);


        setVisible(true);

    }

}