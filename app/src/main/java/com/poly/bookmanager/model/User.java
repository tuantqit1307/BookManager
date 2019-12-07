package com.poly.bookmanager.model;

public class User {
    String username;
    String phone;
    String password;
    String name;

    public User(String username, String phone, String password, String name) {
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.name = name;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}