package view.patient;


import dao.PatientDAO;
import dao.HealthRecordDAO;
import dao.DiseaseHistoryDAO;
import dao.AllergyDAO;
import dao.PrescriptionDAO;
import dao.AppointmentDAO;


import model.Patient;
import model.User;
import model.HealthRecord;
import model.DiseaseHistory;
import model.Allergy;
import model.Prescription;
import model.Appointment;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;



public class PatientDashboard extends JFrame {


    private User user;



    public PatientDashboard(User user) {


        this.user = user;



        setTitle("Patient Dashboard");

        setSize(500, 550);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        JPanel panel = new JPanel();


        panel.setLayout(
                new GridLayout(9,1,10,10)
        );



        JLabel welcomeLabel =
                new JLabel(
                        "Welcome, " + user.getUsername(),
                        SwingConstants.CENTER
                );



        JButton profileButton =
                new JButton("My Profile");


        JButton healthButton =
                new JButton("Health Records");


        JButton diseaseButton =
                new JButton("Disease History");


        JButton allergyButton =
                new JButton("Allergies");


        JButton prescriptionButton =
                new JButton("Prescriptions");


        JButton appointmentButton =
                new JButton("Appointments");


        JButton bookButton =
                new JButton("Book Appointment");


        JButton logoutButton =
                new JButton("Logout");



        panel.add(welcomeLabel);

        panel.add(profileButton);

        panel.add(healthButton);

        panel.add(diseaseButton);

        panel.add(allergyButton);

        panel.add(prescriptionButton);

        panel.add(appointmentButton);

        panel.add(bookButton);

        panel.add(logoutButton);



        add(panel);




        // =========================
        // PROFILE
        // =========================

        profileButton.addActionListener(e -> {


            PatientDAO dao =
                    new PatientDAO();



            Patient patient =
                    dao.getPatientByUserId(
                            user.getUserId()
                    );



            if(patient != null){


                new PatientProfile(patient);


            }
            else{


                JOptionPane.showMessageDialog(
                        this,
                        "Patient profile not found"
                );


            }


        });





        // =========================
        // HEALTH RECORD
        // =========================


        healthButton.addActionListener(e -> {


            PatientDAO dao =
                    new PatientDAO();



            Patient patient =
                    dao.getPatientByUserId(
                            user.getUserId()
                    );



            if(patient != null){



                HealthRecordDAO healthDAO =
                        new HealthRecordDAO();



                ArrayList<HealthRecord> records =
                        healthDAO.getAllHealthRecordsByPatientId(
                                patient.getPatientId()
                        );



                if(!records.isEmpty()){


                    new PatientHealthRecord(records);


                }
                else{


                    JOptionPane.showMessageDialog(
                            this,
                            "No health record found"
                    );


                }


            }


        });





        // =========================
        // DISEASE HISTORY
        // =========================


        diseaseButton.addActionListener(e -> {



            PatientDAO dao =
                    new PatientDAO();



            Patient patient =
                    dao.getPatientByUserId(
                            user.getUserId()
                    );



            if(patient != null){



                DiseaseHistoryDAO diseaseDAO =
                        new DiseaseHistoryDAO();



                ArrayList<DiseaseHistory> list =
                        diseaseDAO.getDiseaseHistoryByPatientId(
                                patient.getPatientId()
                        );



                if(!list.isEmpty()){


                    new PatientDiseaseHistory(list);


                }
                else{


                    JOptionPane.showMessageDialog(
                            this,
                            "No disease history found"
                    );


                }


            }


        });






        // =========================
        // ALLERGIES
        // =========================


        allergyButton.addActionListener(e -> {



            PatientDAO dao =
                    new PatientDAO();



            Patient patient =
                    dao.getPatientByUserId(
                            user.getUserId()
                    );



            if(patient != null){



                AllergyDAO allergyDAO =
                        new AllergyDAO();



                ArrayList<Allergy> list =
                        allergyDAO.getAllergiesByPatientId(
                                patient.getPatientId()
                        );



                if(!list.isEmpty()){


                    new PatientAllergy(list);


                }
                else{


                    JOptionPane.showMessageDialog(
                            this,
                            "No allergies found"
                    );


                }


            }


        });






        // =========================
        // PRESCRIPTIONS
        // =========================


        prescriptionButton.addActionListener(e -> {



            PatientDAO dao =
                    new PatientDAO();



            Patient patient =
                    dao.getPatientByUserId(
                            user.getUserId()
                    );



            if(patient != null){



                PrescriptionDAO prescriptionDAO =
                        new PrescriptionDAO();



                ArrayList<Prescription> list =
                        prescriptionDAO.getPrescriptionsByPatientId(
                                patient.getPatientId()
                        );



                if(!list.isEmpty()){


                    new PatientPrescription(list);


                }
                else{


                    JOptionPane.showMessageDialog(
                            this,
                            "No prescriptions found"
                    );


                }


            }


        });






        // =========================
        // APPOINTMENTS
        // =========================


        appointmentButton.addActionListener(e -> {



            PatientDAO dao =
                    new PatientDAO();



            Patient patient =
                    dao.getPatientByUserId(
                            user.getUserId()
                    );



            if(patient != null){



                AppointmentDAO appointmentDAO =
                        new AppointmentDAO();



                ArrayList<Appointment> list =
                        appointmentDAO.getAppointmentsByPatientId(
                                patient.getPatientId()
                        );



                if(!list.isEmpty()){


                    new PatientAppointment(list);


                }
                else{


                    JOptionPane.showMessageDialog(
                            this,
                            "No appointments found"
                    );


                }


            }


        });






        // =========================
        // BOOK APPOINTMENT
        // =========================


        bookButton.addActionListener(e -> {



            PatientDAO dao =
                    new PatientDAO();



            Patient patient =
                    dao.getPatientByUserId(
                            user.getUserId()
                    );



            if(patient != null){


                new BookAppointmentForm(
                        patient.getPatientId()
                );


            }
            else{


                JOptionPane.showMessageDialog(
                        this,
                        "Patient profile not found"
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