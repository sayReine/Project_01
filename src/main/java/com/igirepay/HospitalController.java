package com.igirepay;

import com.igirepay.dto.AppointmentRequest;
import com.igirepay.model.Appointment;
import com.igirepay.model.Doctor;
import com.igirepay.model.Patient;
import com.igirepay.service.HospitalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @RestController marks this class as a Spring Bean exposed over HTTP.
 *
 * Dependency Injection: the Application Context creates ONE HospitalService
 * bean and injects it here through the constructor. This is "Constructor
 * Injection" - the recommended style.
 */
@RestController
@RequestMapping("/api")
public class HospitalController {

    private final HospitalService service;

    // Constructor Injection - Spring automatically supplies HospitalService
    public HospitalController(HospitalService service) {
        this.service = service;
    }

    // ---- Doctors ----

    @PostMapping("/doctors")
    public Doctor addDoctor(@RequestBody Doctor doctor) {
        return service.addDoctor(doctor);
    }

    @GetMapping("/doctors")
    public List<Doctor> getDoctors() {
        return service.getDoctors();
    }

    // ---- Patients ----

    @PostMapping("/patients")
    public Patient addPatient(@RequestBody Patient patient) {
        return service.addPatient(patient);
    }

    @GetMapping("/patients")
    public List<Patient> getPatients() {
        return service.getPatients();
    }

    // ---- Appointments ----

    @PostMapping("/appointments")
    public Appointment addAppointment(@RequestBody AppointmentRequest request) {
        return service.addAppointment(
                request.appointmentId(),
                request.patientId(),
                request.doctorId(),
                request.date(),
                request.notes()
        );
    }

    @GetMapping("/appointments")
    public List<Appointment> getAppointments() {
        return service.getAppointments();
    }
}