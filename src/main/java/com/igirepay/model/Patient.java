package com.igirepay.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Patient extends Person {
    private String patientId;

    @JsonCreator
    public Patient(@JsonProperty("patientId") String patientId,
                   @JsonProperty("firstName") String firstName,
                   @JsonProperty("lastName") String lastName,
                   @JsonProperty("phone") String phone) {
        super(firstName, lastName, phone);
        if (patientId == null || patientId.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient ID is required.");
        }
        this.patientId = patientId.trim();
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String id) {
        this.patientId = id;
    }

    @Override
    public String toString() {
        return patientId + " - " + getFullName();
    }
}