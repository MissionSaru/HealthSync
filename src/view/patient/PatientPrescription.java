package view.patient;

import java.util.ArrayList;
import javax.swing.*;
import model.Prescription;

public class PatientPrescription extends JFrame {

    public PatientPrescription(ArrayList<Prescription> prescriptionList) {

        setTitle("Patient Prescriptions");
        setSize(500, 400);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        for (Prescription prescription : prescriptionList) {

            String medicineName = prescription.getMedicineName();
            String dosage = prescription.getDosage();
            String duration = prescription.getDuration();
            String notes = prescription.getNotes();

            area.append("Medicine: "
                    + (medicineName != null ? medicineName : "—")
                    + "\n");

            area.append("Dosage: "
                    + (dosage != null && !dosage.isEmpty() ? dosage : "—")
                    + "\n");

            area.append("Duration: "
                    + (duration != null && !duration.isEmpty() ? duration : "—")
                    + "\n");

            area.append("Doctor ID: " + prescription.getDoctorId() + "\n");

            area.append("Date: " + prescription.getPrescriptionDate() + "\n");

            if (notes != null && !notes.isEmpty()) {
                area.append("Notes: " + notes + "\n");
            }

            area.append("-------------------------\n");
        }

        add(new JScrollPane(area));

        setVisible(true);
    }
}