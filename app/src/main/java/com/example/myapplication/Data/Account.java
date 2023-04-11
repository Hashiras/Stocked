package com.example.myapplication.Data;

import com.example.myapplication.Data.Enums.AccountEnum;

public class Account {
    private int ID;
    private String fullName;
    private String email;
    private AccountEnum type;

    private int credentialID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountEnum getType() {
        return type;
    }

    public void setType(AccountEnum type) {
        this.type = type;
    }

    public int getCredentialID() {
        return credentialID;
    }

    public void setCredentialID(int credentialID) {
        this.credentialID = credentialID;
    }
}
