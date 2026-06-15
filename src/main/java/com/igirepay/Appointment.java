package com.igirepay;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    private String    appointmentId;
    private final Patient   patient;
    private final Doctor    doctor;
    private final LocalDate date;
    private final String    notes;
    private String    status = "PENDING";
    private String    diagnosis;
    private String    prescription;
    private String    medicine;

    public Appointment(String appointmentId, Patient patient, Doctor doctor,
                       LocalDate date, String notes) {
        if (appointmentId == null || appointmentId.trim().isEmpty())
            throw new IllegalArgumentException("Appointment ID is required.");
        if (patient == null) throw new IllegalArgumentException("Patient is required.");
        if (doctor  == null) throw new IllegalArgumentException("Doctor is required.");
        if (date    == null) throw new IllegalArgumentException("Date is required.");

        this.appointmentId = appointmentId.trim();
        this.patient       = patient;
        this.doctor        = doctor;
        this.date          = date;
        this.notes         = notes == null ? "" : notes.trim();
        this.diagnosis     = "";
        this.prescription  = "";
        this.medicine      = "";
    }

    public Appointment(String appointmentId, Patient patient, Doctor doctor,
                       LocalDate date, String notes,
                       String diagnosis, String prescription, String medicine) {
        this(appointmentId, patient, doctor, date, notes);
        this.diagnosis    = diagnosis    == null ? "" : diagnosis.trim();
        this.prescription = prescription == null ? "" : prescription.trim();
        this.medicine     = medicine     == null ? "" : medicine.trim();
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String    getAppointmentId() { return appointmentId; }
    public Patient   getPatient()       { return patient; }
    public Doctor    getDoctor()        { return doctor; }
    public LocalDate getDate()          { return date; }
    public String    getNotes()         { return notes; }
    public String    getStatus()        { return status; }
    public String    getDiagnosis()     { return diagnosis; }
    public String    getPrescription()  { return prescription; }
    public String    getMedicine()      { return medicine; }

    // ── Setters ──────────────────────────────────────────────────────────────

    /** Business-logic setter — enforces valid values only. */
    public void setStatus(String status) {
        if (status == null ||
                (!status.equals("PENDING") &&
                        !status.equals("ACCEPTED") &&
                        !status.equals("REJECTED"))) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
        this.status = status;
    }

    /**
     * Storage-layer setter used by DatabaseStorageService when reading rows
     * from the DB. Does NOT validate so an unexpected legacy value never
     * crashes the load; defaults to "PENDING" when the column is blank/null.
     */
    public void loadStatus(String status) {
        this.status = (status == null || status.isBlank()) ? "PENDING" : status.trim();
    }

    public void setAppointmentId(String id) { this.appointmentId = id; }

    public void setClinicalOutcome(String diagnosis, String prescription, String medicine) {
        if (diagnosis    == null || diagnosis.trim().isEmpty())
            throw new IllegalArgumentException("Diagnosis is required.");
        if (prescription == null || prescription.trim().isEmpty())
            throw new IllegalArgumentException("Prescription is required.");
        if (medicine     == null || medicine.trim().isEmpty())
            throw new IllegalArgumentException("Medicine is required.");
        this.diagnosis    = diagnosis.trim();
        this.prescription = prescription.trim();
        this.medicine     = medicine.trim();
    }

    public boolean hasClinicalOutcome() {
        return !diagnosis.isEmpty() && !prescription.isEmpty()
                && !medicine.isEmpty() && "ACCEPTED".equals(status);
    }

    public String toCsv() {
        return String.join(",",
                appointmentId,
                patient.getPatientId(),
                doctor.getDoctorId(),
                date.format(DATE_FORMAT),
                escape(notes),
                status,
                escape(diagnosis),
                escape(prescription),
                escape(medicine));
    }

    public static String escape(String value) {
        return value.replace(",", ";");
    }

    @Override
    public String toString() {
        return appointmentId + " [" + status + "] | " + date
                + " | " + patient.getFullName() + " -> Dr. " + doctor.getFullName();
    }
}