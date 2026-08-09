package view.patient;


import model.Allergy;

import javax.swing.*;
import java.util.ArrayList;


public class PatientAllergy extends JFrame {



    public PatientAllergy(ArrayList<Allergy> allergyList) {


        setTitle("Allergy Details");

        setSize(450,400);

        setLocationRelativeTo(null);



        JTextArea area = new JTextArea();

        area.setEditable(false);



        for(Allergy allergy : allergyList) {



            area.append(
                    "Allergy: "
                    + allergy.getAllergyName()
                    + "\n"
            );


            area.append(
                    "Description: "
                    + allergy.getDescription()
                    + "\n"
            );


            area.append(
                    "Severity: "
                    + allergy.getSeverity()
                    + "\n"
            );


            area.append(
                    "Added Date: "
                    + allergy.getAddedDate()
                    + "\n"
            );


            area.append(
                    "----------------------\n"
            );


        }



        add(new JScrollPane(area));

        setVisible(true);


    }

}