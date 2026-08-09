package model;

import java.sql.Date;

public class HealthRecord {

    private int recordId;
    private int patientId;
    private double height;
    private double weight;
    private double bmi;
    private String bloodPressure;
    private double temperature;
    private int heartRate;
    private double spo2;
    private Date recordedDate;


    public HealthRecord(int recordId, int patientId, double height,
                        double weight, double bmi, String bloodPressure,
                        double temperature, int heartRate, double spo2,
                        Date recordedDate) {

        this.recordId = recordId;
        this.patientId = patientId;
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
        this.bloodPressure = bloodPressure;
        this.temperature = temperature;
        this.heartRate = heartRate;
        this.spo2 = spo2;
        this.recordedDate = recordedDate;

    }


    public int getRecordId() {
        return recordId;
    }


    public int getPatientId() {
        return patientId;
    }


    public double getHeight() {
        return height;
    }


    public double getWeight() {
        return weight;
    }


    public double getBmi() {
        return bmi;
    }


    public String getBloodPressure() {
        return bloodPressure;
    }


    public double getTemperature() {
        return temperature;
    }


    public int getHeartRate() {
        return heartRate;
    }


    public double getSpo2() {
        return spo2;
    }


    public Date getRecordedDate() {
        return recordedDate;
    }

}