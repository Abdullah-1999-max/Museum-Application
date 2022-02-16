package com.example.smartmuseumapp;

public class UserModel {
    String  Name,phone,email,UID;

    public UserModel() {
    }

    public UserModel(String name, String phone, String email, String UID) {
        Name = name;
        this.phone = phone;
        this.email = email;
        this.UID = UID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}