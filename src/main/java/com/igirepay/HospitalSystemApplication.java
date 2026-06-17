package com.igirepay;

import com.igirepay.model.Appointment;
import com.igirepay.model.Doctor;
import com.igirepay.model.Patient;
import com.igirepay.service.HospitalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HospitalSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalSystemApplication.class, args);
    }

    /**
     * CommandLineRunner is itself a Bean. Spring's Application Context
     * creates it after startup and runs its code, injecting the
     * HospitalService bean (Dependency Injection) so we can add
     * sample data and print a display to the console.
     */
    @Bean
    public CommandLineRunner demo(HospitalService service) {
        return args -> {
            Doctor doctor = service.addDoctor(
                    new Doctor("D001", "Alice", "Mugisha", "+250788111111", "Cardiology"));

            Patient patient = service.addPatient(
                    new Patient("P001", "Jean", "Uwase", "+250788222222"));

            Appointment appointment = service.addAppointment(
                    "A001", "P001", "D001", LocalDate.now().plusDays(2), "Routine checkup");

            System.out.println();
            System.out.println("==================== HOSPITAL SYSTEM ====================");
            System.out.println("Doctor:      " + doctor);
            System.out.println("Patient:     " + patient);
            System.out.println("Appointment: " + appointment);
            System.out.println("==========================================================");
            System.out.println("Try the REST API:");
            System.out.println("  GET  http://localhost:8080/api/doctors");
            System.out.println("  GET  http://localhost:8080/api/patients");
            System.out.println("  GET  http://localhost:8080/api/appointments");
            System.out.println("==========================================================");
            System.out.println();
        };
    }
}