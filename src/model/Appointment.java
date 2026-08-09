package model;

import java.sql.Date;

public class Appointment {

    private int appointmentId;
    private int patientId;
    private int doctorId;
    private Date appointmentDate;
    private String status;
    private String doctorName;
    private String patientName;


    public Appointment(int appointmentId, int patientId, int doctorId,
                       Date appointmentDate, String status) {

        this(appointmentId, patientId, doctorId, appointmentDate, status, null, null);
    }


    public Appointment(int appointmentId, int patientId, int doctorId,
                       Date appointmentDate, String status,
                       String doctorName, String patientName) {

        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.doctorName = doctorName;
        this.patientName = patientName;
    }


    public int getAppointmentId() { return appointmentId; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public Date getAppointmentDate() { return appointmentDate; }
    public String getStatus() { return status; }
    public String getDoctorName() { return doctorName; }
    public String getPatientName() { return patientName; }
}