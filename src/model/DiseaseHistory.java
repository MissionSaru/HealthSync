package model;

import java.sql.Date;

public class DiseaseHistory {

    private int historyId;
    private int patientId;
    private int diseaseId;
    private String diseaseName;
    private int addedByDoctor;
    private String diseaseType;
    private String status;
    private Date diagnosedDate;
    private String notes;


    public DiseaseHistory(int historyId,
                          int patientId,
                          int diseaseId,
                          int addedByDoctor,
                          String diseaseType,
                          String status,
                          Date diagnosedDate,
                          String notes) {

        this.historyId = historyId;
        this.patientId = patientId;
        this.diseaseId = diseaseId;
        this.addedByDoctor = addedByDoctor;
        this.diseaseType = diseaseType;
        this.status = status;
        this.diagnosedDate = diagnosedDate;
        this.notes = notes;

    }


    public int getHistoryId() {
        return historyId;
    }


    public int getPatientId() {
        return patientId;
    }


    public int getDiseaseId() {
        return diseaseId;
    }
    
    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
   }

    public int getAddedByDoctor() {
        return addedByDoctor;
    }


    public String getDiseaseType() {
        return diseaseType;
    }


    public String getStatus() {
        return status;
    }


    public Date getDiagnosedDate() {
        return diagnosedDate;
    }


    public String getNotes() {
        return notes;
    }

}