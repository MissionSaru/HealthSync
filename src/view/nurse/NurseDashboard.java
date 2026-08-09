package view.nurse;

import dao.NurseDAO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import model.Nurse;
import model.Patient;
import model.User;
import view.RecordVitalsForm;

public class NurseDashboard extends JFrame {

    private User user;

    public NurseDashboard(User user) {

        this.user = user;

        setTitle("Nurse Dashboard");
        setSize(550, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        NurseDAO nurseDAO = new NurseDAO();

        Nurse nurseInfo =
                nurseDAO.getNurseByUserId(
                        user.getUserId()
                );


        String nurseName = user.getUsername();


        if (nurseInfo != null) {

            nurseName = nurseInfo.getNurseName();

        }


        JPanel panel = new JPanel();

        panel.setLayout(
                new GridLayout(6, 1, 10, 10)
        );


        JLabel welcomeLabel =
                new JLabel(
                        "Welcome, " + nurseName,
                        SwingConstants.CENTER
                );


        JButton patientsButton =
                new JButton("Patient Assignment");


        JButton medicineButton =
                new JButton("Medicine Administration");


        JButton vitalsButton =
                new JButton("Record Vitals");


        JButton prescriptionsButton =
                new JButton("Prescriptions");


        JButton logoutButton =
                new JButton("Logout");


        panel.add(welcomeLabel);
        panel.add(patientsButton);
        panel.add(medicineButton);
        panel.add(vitalsButton);
        panel.add(prescriptionsButton);
        panel.add(logoutButton);


        add(panel);


        patientsButton.addActionListener(e -> {

            Nurse nurse =
                    nurseDAO.getNurseByUserId(
                            user.getUserId()
                    );

            if (nurse != null) {

                new PatientAssignment(
                        nurse.getNurseId()
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Nurse not found"
                );

            }

        });


        medicineButton.addActionListener(e -> {

            Nurse nurse =
                    nurseDAO.getNurseByUserId(
                            user.getUserId()
                    );

            if (nurse != null) {

                new MedicineAdministration(
                        nurse.getNurseId()
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Nurse not found"
                );

            }

        });


        vitalsButton.addActionListener(e -> {

            Nurse nurse =
                    nurseDAO.getNurseByUserId(
                            user.getUserId()
                    );

            if (nurse != null) {

                ArrayList<Patient> assigned =
                        nurseDAO.getPatientsByNurseId(
                                nurse.getNurseId()
                        );

                if (assigned != null && !assigned.isEmpty()) {

                    new RecordVitalsForm(assigned);

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "No patients assigned"
                    );

                }

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Nurse not found"
                );

            }

        });


        prescriptionsButton.addActionListener(e -> {

            Nurse nurse =
                    nurseDAO.getNurseByUserId(
                            user.getUserId()
                    );

            if (nurse != null) {

                new NursePrescriptions(
                        nurse.getNurseId()
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Nurse not found"
                );

            }

        });


        logoutButton.addActionListener(e -> {

            dispose();

            new view.LoginForm();

        });


        setVisible(true);

    }

}