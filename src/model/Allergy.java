package model;

import java.sql.Date;

public class Allergy {

    private int allergyId;
    private int patientId;
    private String allergyName;
    private String description;
    private String severity;
    private Date addedDate;


    public Allergy(int allergyId,
                   int patientId,
                   String allergyName,
                   String description,
                   String severity,
                   Date addedDate) {


        this.allergyId = allergyId;
        this.patientId = patientId;
        this.allergyName = allergyName;
        this.description = description;
        this.severity = severity;
        this.addedDate = addedDate;

    }



    public int getAllergyId() {
        return allergyId;
    }


    public int getPatientId() {
        return patientId;
    }


    public String getAllergyName() {
        return allergyName;
    }


    public String getDescription() {
        return description;
    }


    public String getSeverity() {
        return severity;
    }


    public Date getAddedDate() {
        return addedDate;
    }

}