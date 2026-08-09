package model;

import java.sql.Date;

public class Patient {

    private int patientId;
    private int userId;
    private String name;
    private Date dateOfBirth;
    private String gender;
    private String phone;
    private String address;
    private String bloodGroup;


    public Patient(int patientId, int userId, String name, Date dateOfBirth,
                   String gender, String phone, String address, String bloodGroup) {

        this.patientId = patientId;
        this.userId = userId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.bloodGroup = bloodGroup;

    }


    public int getPatientId() {
        return patientId;
    }


    public int getUserId() {
        return userId;
    }


    public String getName() {
        return name;
    }


    public Date getDateOfBirth() {
        return dateOfBirth;
    }


    public String getGender() {
        return gender;
    }


    public String getPhone() {
        return phone;
    }


    public String getAddress() {
        return address;
    }


    public String getBloodGroup() {
        return bloodGroup;
    }
    @Override
public String toString() {
    return name + " (ID: " + patientId + ")";
}

}