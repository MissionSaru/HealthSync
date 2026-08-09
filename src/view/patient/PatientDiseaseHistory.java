package view.patient;

import java.util.ArrayList;
import javax.swing.*;
import model.DiseaseHistory;


public class PatientDiseaseHistory extends JFrame {


    public PatientDiseaseHistory(ArrayList<DiseaseHistory> historyList) {


        setTitle("Disease History");

        setSize(500,400);

        setLocationRelativeTo(null);



        JTextArea area = new JTextArea();

        area.setEditable(false);



        for(DiseaseHistory h : historyList){


            area.append(
        "Disease: "
        + h.getDiseaseName()
        + "\n"
);


            area.append(
                    "Type: "
                    + h.getDiseaseType()
                    + "\n"
            );


            area.append(
                    "Status: "
                    + h.getStatus()
                    + "\n"
            );


            area.append(
                    "Diagnosed Date: "
                    + h.getDiagnosedDate()
                    + "\n"
            );


            area.append(
                    "Notes: "
                    + h.getNotes()
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