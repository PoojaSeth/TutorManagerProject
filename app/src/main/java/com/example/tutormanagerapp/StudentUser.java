package com.example.tutormanagerapp;

public class StudentUser {

    private String email;
    private String name;
    private String password;
    private String id;
    private String imageURL;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public StudentUser(String email, String name, String password, String id, String imageURL, String status) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.id = id;
        this.imageURL = imageURL;
        this.status = status;
    }

    public StudentUser() {
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
