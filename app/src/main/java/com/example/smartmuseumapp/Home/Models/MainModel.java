package com.example.smartmuseumapp.Home.Models;

public class MainModel {
    String Name;
    String image;

    public MainModel() {
    }

    public MainModel(String name, String image) {
        Name = name;
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
