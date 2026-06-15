package com.igirepay.service;

import com.igirepay.model.Appointment;
import com.igirepay.model.Doctor;
import com.igirepay.model.Patient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @Service marks this class as a Spring Bean.
 * It is created once by the Application Context and injected
 * wherever it is needed (e.g. into HospitalController) via
 * Dependency Injection.
 *
 * This is the layer that "owns" the in-memory lists of doctors,
 * patients and appointments. The domain objects (Doctor, Patient,
 * Appointment) themselves are NOT beans - they are plain validated
 * objects created on demand, which is correct because each one
 * holds its own unique, validated data.
 */
@Service
public class HospitalService {

    private final List<Doctor> doctors = new ArrayList<>();
    private final List<Patient> patients = new ArrayList<>();
    private final List<Appointment> appointments = new ArrayList<>();

    public Doctor addDoctor(Doctor doctor) {
        doctors.add(doctor);
        return doctor;
    }

    public Patient addPatient(Patient patient) {
        patients.add(patient);
        return patient;
    }

    public Appointment addAppointment(String appointmentId, String patientId,
                                      String doctorId, LocalDate date, String notes) {
        Patient patient = findPatient(patientId);
        Doctor doctor = findDoctor(doctorId);
        Appointment appointment = new Appointment(appointmentId, patient, doctor, date, notes);
        appointments.add(appointment);
        return appointment;
    }

    public List<Doctor> getDoctors() {
        return Collections.unmodifiableList(doctors);
    }

    public List<Patient> getPatients() {
        return Collections.unmodifiableList(patients);
    }

    public List<Appointment> getAppointments() {
        return Collections.unmodifiableList(appointments);
    }

    private Doctor findDoctor(String doctorId) {
        return doctors.stream()
                .filter(d -> d.getDoctorId().equals(doctorId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Doctor not found: " + doctorId));
    }

    private Patient findPatient(String patientId) {
        return patients.stream()
                .filter(p -> p.getPatientId().equals(patientId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Patient not found: " + patientId));
    }
}