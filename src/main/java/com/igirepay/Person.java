package com.igirepay.model;

public abstract class Person {
    private final String firstName;
    private final String lastName;
    private final String phone;

    protected Person(String firstName, String lastName, String phone) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required.");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required.");
        }
        if (phone == null || !phone.matches("\\+?[0-9]{7,15}")) {
            throw new IllegalArgumentException("Phone number is invalid.");
        }
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.phone = phone.trim();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}