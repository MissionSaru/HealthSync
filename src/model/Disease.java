package model;

public class Disease {

    private int diseaseId;
    private String diseaseName;
    private String description;

    public Disease() {
    }

    public Disease(int diseaseId, String diseaseName, String description) {
        this.diseaseId = diseaseId;
        this.diseaseName = diseaseName;
        this.description = description;
    }

    public int getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(int diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return diseaseName;
    }
}