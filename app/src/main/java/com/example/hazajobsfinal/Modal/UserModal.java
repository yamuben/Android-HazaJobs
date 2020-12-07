package com.example.hazajobsfinal.Modal;

public class UserModal {
    private String firstName;
    private String lastName;
    private String title;
    private String motor;
    private String id;
    private String phone;
    private String imageUrl;

    public UserModal(){}

    public UserModal(String firstName, String lastName, String title, String motor, String id, String phone, String imageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.motor = motor;
        this.id = id;
        this.phone = phone;
        this.imageUrl = imageUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
