package view.doctor;

import dao.DoctorDAO;
import dao.DoctorPatientDAO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import model.Doctor;
import model.Patient;
import model.User;

public class DoctorDashboard extends JFrame {

    private final User user;

    @SuppressWarnings("unused")
    public DoctorDashboard(User user) {

        this.user = user;

        setTitle("Doctor Dashboard");
        setSize(550, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        DoctorDAO doctorDAO = new DoctorDAO();

        Doctor doctorInfo =
                doctorDAO.getDoctorByUserId(
                        user.getUserId()
                );


        String doctorName = user.getUsername();


        if (doctorInfo != null) {

            doctorName = doctorInfo.getDoctorName();

        }


        JPanel panel = new JPanel();

        panel.setLayout(
                new GridLayout(8, 1, 10, 10)
        );


        JLabel welcomeLabel =
                new JLabel(
                        "Welcome, " + doctorName,
                        SwingConstants.CENTER
                );


        JButton profileButton =
                new JButton("My Profile");


        JButton patientsButton =
                new JButton("My Patients");


        JButton addPatientButton =
                new JButton("Add Patient");


        JButton prescriptionButton =
                new JButton("Add Prescription");


        JButton diseaseButton =
                new JButton("Add Disease History");


        JButton appointmentButton =
                new JButton("Appointments");


        JButton logoutButton =
                new JButton("Logout");



        panel.add(welcomeLabel);
        panel.add(profileButton);
        panel.add(patientsButton);
        panel.add(addPatientButton);
        panel.add(prescriptionButton);
        panel.add(diseaseButton);
        panel.add(appointmentButton);
        panel.add(logoutButton);


        add(panel);



        // =========================
        // MY PROFILE
        // =========================

        profileButton.addActionListener(e -> {


            Doctor doctor =
                    doctorDAO.getDoctorByUserId(
                            user.getUserId()
                    );


            if(doctor != null){

                new DoctorProfile(doctor);

            }
            else{

                JOptionPane.showMessageDialog(
                        this,
                        "Doctor profile not found"
                );

            }


        });



        // =========================
        // MY PATIENTS
        // =========================

        patientsButton.addActionListener(e -> {


            Doctor doctor =
                    doctorDAO.getDoctorByUserId(
                            user.getUserId()
                    );


            if(doctor != null){


                DoctorPatientDAO patientDAO =
                        new DoctorPatientDAO();



                ArrayList<Patient> patients =
                        patientDAO.getPatientsByDoctorId(
                                doctor.getDoctorId()
                        );



                if(patients != null && !patients.isEmpty()){


                    new DoctorPatientList(
                            patients
                    );


                }
                else{


                    JOptionPane.showMessageDialog(
                            this,
                            "No patients assigned"
                    );


                }


            }
            else{


                JOptionPane.showMessageDialog(
                        this,
                        "Doctor not found"
                );


            }        });



        // =========================
        // ADD PATIENT
        // =========================

        addPatientButton.addActionListener(e -> {


            Doctor doctor =
                    doctorDAO.getDoctorByUserId(
                            user.getUserId()
                    );


            if (doctor != null) {

                new DoctorPatientAssignment(
                        doctor.getDoctorId()
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Doctor not found"
                );

            }


        });



        // =========================
        // ADD PRESCRIPTION
        // =========================

        prescriptionButton.addActionListener(e -> {


    Doctor doctor =
            doctorDAO.getDoctorByUserId(
                    user.getUserId()
            );


    if(doctor != null){

        new AddPrescription(
                doctor.getDoctorId()
        );

    }
    else{

        JOptionPane.showMessageDialog(
                this,
                "Doctor not found"
        );

    }


});
diseaseButton.addActionListener(e -> {

    Doctor doctor =
            doctorDAO.getDoctorByUserId(
                    user.getUserId()
            );

    if (doctor != null) {

        new AddDiseaseHistory(
                doctor.getDoctorId()
        );

    } else {

        JOptionPane.showMessageDialog(
                this,
                "Doctor not found"
        );
    }

});
appointmentButton.addActionListener(e -> {

    Doctor doctor =
            doctorDAO.getDoctorByUserId(
                    user.getUserId()
            );

    if (doctor != null) {

        new DoctorAppointment(
                doctor.getDoctorId()
        );

    } else {

        JOptionPane.showMessageDialog(
                this,
                "Doctor not found"
        );
    }

});





        // =========================
        // LOGOUT
        // =========================

        logoutButton.addActionListener(e -> {


            dispose();

            new view.LoginForm();


        });



        setVisible(true);

    }

}