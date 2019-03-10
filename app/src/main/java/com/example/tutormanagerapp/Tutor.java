package com.example.tutormanagerapp;

public class Tutor {

    private String tutorId;
    private String email;
    private String name;
    private String password;

    public Tutor() {
    }

    public Tutor(String tutorId, String email, String name, String password) {
        this.tutorId = tutorId;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getTutorId() {
        return tutorId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
