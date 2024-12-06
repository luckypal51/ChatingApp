package com.example.messengerapp.model;

import com.google.firebase.Timestamp;

public class UserDetails {
    private String phone;
    private String username;
    private Timestamp createdtimestamp;
    private String userid;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public UserDetails() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserDetails(String username, String phone, Timestamp createdtimestamp,String userid) {
        this.username = username;
        this.phone = phone;
        this.createdtimestamp = createdtimestamp;
        this.userid = userid;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreatedtimestamp() {
        return createdtimestamp;
    }

    public void setCreatedtimestamp(Timestamp createdtimestamp) {
        this.createdtimestamp = createdtimestamp;
    }




}
