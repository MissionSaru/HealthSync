package model;

public class Doctor {

    private int doctorId;
    private int userId;
    private String doctorName;
    private String specialization;
    private String phone;

    public Doctor(int doctorId, int userId, String doctorName,
                  String specialization, String phone) {
        this.doctorId = doctorId;
        this.userId = userId;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.phone = phone;
    }

    public int getDoctorId() { return doctorId; }
    public int getUserId() { return userId; }
    public String getDoctorName() { return doctorName; }
    public String getSpecialization() { return specialization; }
    public String getPhone() { return phone; }

    @Override
    public String toString() {
        return doctorName + " (" + specialization + ")";
    }
}