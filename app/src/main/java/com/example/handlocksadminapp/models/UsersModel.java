package com.example.handlocksadminapp.models;

import java.io.Serializable;

public class UsersModel implements Serializable {
    String name;
    String email;

    public UsersModel() {
    }

    public UsersModel(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
