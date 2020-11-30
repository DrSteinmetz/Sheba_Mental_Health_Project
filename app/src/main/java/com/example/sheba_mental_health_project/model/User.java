package com.example.sheba_mental_health_project.model;

import java.io.Serializable;

public abstract class User implements Serializable {

    private String mId;
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mToken;
    private int mRole; // Enum

    public User(String mId, String mFirstName, String mLastName, String mEmail, String mToken, int mRole) {
        this.mId = mId;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mEmail = mEmail;
        this.mToken = mToken;
        this.mRole = mRole;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmToken() {
        return mToken;
    }

    public void setmToken(String mToken) {
        this.mToken = mToken;
    }

    public int getmRole() {
        return mRole;
    }

    public void setmRole(int mRole) {
        this.mRole = mRole;
    }
}
