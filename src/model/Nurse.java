package model;

public class Nurse {

    private int nurseId;
    private int userId;
    private String nurseName;
    private String phone;

    public Nurse(int nurseId,
                 int userId,
                 String nurseName,
                 String phone) {

        this.nurseId = nurseId;
        this.userId = userId;
        this.nurseName = nurseName;
        this.phone = phone;
    }

    public int getNurseId() {
        return nurseId;
    }

    public int getUserId() {
        return userId;
    }

    public String getNurseName() {
        return nurseName;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return nurseName + " (ID: " + nurseId + ")";
    }
}