package model;

import java.sql.Timestamp;

public class MedicineSchedule {

    private int scheduleId;
    private int patientId;
    private int medicineId;
    private String dosage;
    private Timestamp scheduledTime;
    private String status;
    private Integer administeredBy;
    private Timestamp administeredAt;

    private String patientName;
    private String medicineName;

    public MedicineSchedule(int scheduleId,
                             int patientId,
                             int medicineId,
                             String dosage,
                             Timestamp scheduledTime,
                             String status,
                             Integer administeredBy,
                             Timestamp administeredAt) {

        this.scheduleId = scheduleId;
        this.patientId = patientId;
        this.medicineId = medicineId;
        this.dosage = dosage;
        this.scheduledTime = scheduledTime;
        this.status = status;
        this.administeredBy = administeredBy;
        this.administeredAt = administeredAt;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public String getDosage() {
        return dosage;
    }

    public Timestamp getScheduledTime() {
        return scheduledTime;
    }

    public String getStatus() {
        return status;
    }

    public Integer getAdministeredBy() {
        return administeredBy;
    }

    public Timestamp getAdministeredAt() {
        return administeredAt;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }
}