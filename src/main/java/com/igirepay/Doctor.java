package com.igirepay.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Doctor extends Person {
    private String doctorId;
    private final String specialization;

    @JsonCreator
    public Doctor(@JsonProperty("doctorId") String doctorId,
                  @JsonProperty("firstName") String firstName,
                  @JsonProperty("lastName") String lastName,
                  @JsonProperty("phone") String phone,
                  @JsonProperty("specialization") String specialization) {
        super(firstName, lastName, phone);
        if (doctorId == null || doctorId.trim().isEmpty()) {
            throw new IllegalArgumentException("Doctor ID is required.");
        }
        if (specialization == null || specialization.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialization is required.");
        }
        this.doctorId = doctorId.trim();
        this.specialization = specialization.trim();
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setDoctorId(String id) {
        this.doctorId = id;
    }

    @Override
    public String toString() {
        return doctorId + " - Dr. " + getFullName() + " (" + specialization + ")";
    }
}