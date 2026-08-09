package model;

import java.sql.Date;

public class Prescription {

    private int prescriptionId;
    private int patientId;
    private int doctorId;
    private Date prescriptionDate;
    private int medicineId;
    private String medicineName;
    private String dosage;
    private String duration;
    private String notes;
    private String patientName;


    public Prescription(int prescriptionId, int patientId, int doctorId,
                        Date prescriptionDate, String notes) {

        this(prescriptionId, patientId, doctorId, prescriptionDate,
                0, null, null, null, notes);
    }


    public Prescription(int prescriptionId, int patientId, int doctorId,
                        Date prescriptionDate, int medicineId, String medicineName,
                        String dosage, String duration, String notes) {

        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.prescriptionDate = prescriptionDate;
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.duration = duration;
        this.notes = notes;
    }


    public int getPrescriptionId() { return prescriptionId; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public Date getPrescriptionDate() { return prescriptionDate; }
    public int getMedicineId() { return medicineId; }
    public String getMedicineName() { return medicineName; }
    public String getDosage() { return dosage; }
    public String getDuration() { return duration; }
    public String getNotes() { return notes; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
}