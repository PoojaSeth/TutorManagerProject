package com.example.tutormanagerapp;

public class Student {
    private String studentId;
    private String email;
    private String name;
    private String password;

    public Student() {
    }

    public Student(String studentId, String email, String name, String password) {
        this.studentId = studentId;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getStudentId() {
        return studentId;
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
