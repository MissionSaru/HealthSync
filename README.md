# HealthSync 🏥

A desktop **hospital / health management system** built with **Java Swing** and **MySQL**. It connects three roles — **Patients**, **Doctors**, and **Nurses** — around a shared set of health records: patient profiles, vitals, disease history, allergies, prescriptions, medicine schedules, and appointments.

## Features

### Common
- **Login** — role-based routing (Patient / Doctor / Nurse) after authentication.
- **Registration** — anyone can sign up from the login screen by choosing a role:
  - **Patient:** name, date of birth, gender, phone, address, blood group
  - **Doctor:** name, specialization, phone
  - **Nurse:** name, phone
  - Username rules (3–20 chars, letters/digits/underscore), password ≥ 6 chars, duplicate-username rejection.

### Patient
- **My Profile** — personal details.
- **Health Records** — full vitals history (all recorded entries per date, not just the latest): height, weight, BMI, blood pressure, temperature, heart rate, SpO₂.
- **Disease History** — list of diagnosed diseases with type, status, date, notes.
- **Allergies** — name, description, severity, date added.
- **Prescriptions** — medicine, dosage, duration, date, notes.
- **Appointments** — view booked appointments with the doctor's name and status.
- **Book Appointment** — pick a doctor (dropdown from the DB) + date → saved as `Scheduled`.

### Doctor
- **My Profile** — specialization, phone.
- **My Patients** — list of assigned patients.
- **Add Patient** — assign any registered patient to the doctor (duplicate-safe).
- **Add Prescription** — patient dropdown → **medicine dropdown** (loaded from the `medicines` table) → dosage → duration → optional notes.
- **Add Disease History** — patient → disease dropdown → type/status/date/notes, plus an **"Add New Disease"** button for diseases not in the catalog, plus an **optional allergy section** (name, description, severity) saved in the same click.
- **Appointments** — table of the doctor's appointments with patient names; select a row and **Accept**, **Mark Completed**, or **Cancel**.

### Nurse
- **Patient Assignment** — assign patients to the nurse (duplicate-safe).
- **Medicine Administration** — medicine schedule for assigned patients (with patient & medicine names); mark entries as **Administered** (records who/when).
- **Record Vitals** — record height, weight (BMI auto-calculated), blood pressure, temperature, heart rate, SpO₂ for assigned patients; each entry is kept as history.
- **Prescriptions** — one table of every prescription for all assigned patients (with patient names).

## Tech Stack

| Layer     | Technology                          |
|-----------|-------------------------------------|
| UI        | Java Swing (JDK 8+)                 |
| Data      | MySQL                               |
| JDBC      | MySQL Connector/J `9.7.0` (`lib/`)  |
| Structure | Layered: `view` → `service` → `dao` → `model` |

## Project Structure

```
HealthSync/
├── lib/
│   └── mysql-connector-j-9.7.0.jar     # JDBC driver
├── src/
│   ├── database/
│   │   └── DBConnection.java           # MySQL connection (URL, user, password)
│   ├── model/                          # POJOs: User, Patient, Doctor, Nurse, ...
│   ├── dao/                            # JDBC data access: UserDAO, PatientDAO, ...
│   ├── service/                        # Business logic: LoginService, RegisterService, BMIService
│   ├── util/                           # Validator, DateUtil
│   ├── view/
│   │   ├── LoginForm.java              # Entry point (main)
│   │   ├── RegisterForm.java           # Role-based registration
│   │   ├── RecordVitalsForm.java
│   │   ├── patient/                    # PatientDashboard + patient screens
│   │   ├── doctor/                     # DoctorDashboard + doctor screens
│   │   └── nurse/                      # NurseDashboard + nurse screens
│   └── TestConnection.java             # Quick DB connection check
└── .vscode/settings.json               # VS Code Java paths (already configured)
```

## Prerequisites

- **JDK 8+** (the code uses lambdas and try-with-resources)
- **MySQL Server** running on `localhost:3306`
- A MySQL database named **`healthsync`** with the tables below

## Setup

### 1. Database

Create the database and tables in MySQL (e.g., via MySQL Workbench or the VS Code MySQL extension). The tables the app reads/writes:

| Table                   | Key columns |
|-------------------------|-------------|
| `users`                 | user_id, username, password, role |
| `patients`              | patient_id, user_id, name, date_of_birth, gender, phone, address, blood_group |
| `doctors`               | doctor_id, user_id, doctor_name, specialization, phone |
| `nurses`                | nurse_id, user_id, nurse_name, phone |
| `doctor_patient`        | doctor_id, patient_id (assignment link) |
| `nurse_patient`         | nurse_id, patient_id (assignment link) |
| `health_records`        | record_id, patient_id, height, weight, bmi, blood_pressure, temperature, heart_rate, spo2, recorded_date |
| `medicines`             | medicine_id, medicine_name, description |
| `prescriptions`         | prescription_id, patient_id, doctor_id, prescription_date, medicine_id, dosage, duration, notes |
| `diseases`              | disease_id, disease_name, description |
| `patient_disease_history` | history_id, patient_id, disease_id, added_by_doctor, disease_type, status, diagnosed_date, notes |
| `patient_allergies`     | allergy_id, patient_id, allergy_name, description, severity, added_date |
| `appointments`          | appointment_id, patient_id, doctor_id, appointment_date, status (`Scheduled` / `Accepted` / `Completed` / `Cancelled`) |
| `medicine_schedule`     | schedule_id, patient_id, medicine_id, dosage, scheduled_time, status, administered_by, administered_at |

> **Note:** there is no schema SQL file in the repo yet — create the tables directly in your MySQL client.

### 2. Database credentials

`src/database/DBConnection.java` currently hardcodes:

```java
URL      = "jdbc:mysql://localhost:3306/healthsync"
USER     = "root"
PASSWORD = "<your password>"
```

Edit this file if your MySQL user/password differ.

### 3. Build

From the project root (Git Bash / bash on any OS):

```bash
rm -rf out && mkdir -p out
javac -cp "lib/mysql-connector-j-9.7.0.jar" -d out $(find src -name "*.java")
```

### 4. Run

**Windows:**

```bash
java -cp "out;lib/mysql-connector-j-9.7.0.jar" view.LoginForm
```

**macOS / Linux:**

```bash
java -cp "out:lib/mysql-connector-j-9.7.0.jar" view.LoginForm
```

### VS Code

The repo includes `.vscode/settings.json` (source path `src`, referenced library `lib/**/*.jar`), so the **Extension Pack for Java** works out of the box. After pulling code changes, use **Java: Clean Java Language Server Workspace → Restart and delete** to clear stale compiled classes.

## Typical Flow

1. Register an account as a **Patient**, **Doctor**, or **Nurse** from the login screen.
2. Log in as a **Patient** → **Book Appointment** (choose a doctor + date in `dd-MM-yyyy`).
3. Log in as that **Doctor** → **Appointments** → **Accept** the booking → later **Mark Completed** or **Cancel**.
4. Doctor → **Add Prescription** (medicine, dosage, duration) and **Add Disease History** (add new diseases / allergies on the fly).
5. Log in as a **Nurse** → **Patient Assignment** to link patients → **Medicine Administration** (mark administered) → **Record Vitals** (BMI auto-calculated) → **Prescriptions** to see everything prescribed to assigned patients.
6. The patient can then view all of it: vitals history, prescriptions, allergies, disease history, and appointment status.

## Notes

- **Date format** everywhere is `dd-MM-yyyy` (see `util/DateUtil.java`).
- **BMI** is computed as `weight(kg) / height(m)²` with categories Underweight / Normal / Overweight / Obese (`service/BMIService.java`).
- **Appointments status** must be one of `Scheduled`, `Accepted`, `Completed`, `Cancelled` — if your `appointments.status` column was created with the older 3-value enum, run:
  ```sql
  ALTER TABLE appointments
    MODIFY COLUMN status ENUM('Scheduled','Accepted','Completed','Cancelled') DEFAULT NULL;
  ```
- Prescription `medicine_id`, `dosage`, and `duration` columns were added to the `prescriptions` table after the initial schema — make sure they exist:
  ```sql
  ALTER TABLE prescriptions
    ADD COLUMN medicine_id INT DEFAULT NULL AFTER doctor_id,
    ADD COLUMN dosage VARCHAR(100) DEFAULT NULL AFTER medicine_id,
    ADD COLUMN duration VARCHAR(100) DEFAULT NULL AFTER dosage;
  ```
- The `NotificationService` class is currently a placeholder (no notifications implemented yet).

## Troubleshooting

| Symptom | Fix |
|---------|-----|
| `java.lang.Error: Unresolved compilation problems` | Stale compiled classes — **Java: Clean Java Language Server Workspace → Restart and delete**, or delete the `out/` folder and rebuild. |
| `Database Connection Failed` | MySQL not running, wrong credentials in `DBConnection.java`, or the `healthsync` database doesn't exist. |
| `Could not update appointment.` | The `appointments.status` enum doesn't include `Accepted` — run the ALTER above. |
