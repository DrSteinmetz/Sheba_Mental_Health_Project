package com.example.sheba_mental_health_project.model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public abstract class User implements Serializable {

    private String mId;
    private String mEmail;
    private String mFirstName;
    private String mLastName;

    public User() {}

    public User(String mId, String mEmail, String mFirstName, String mLastName) {
        this.mId = mId;
        this.mEmail = mEmail;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    @Override
    public String toString() {
        return "User: {" +
                "mId='" + mId + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mFirstName='" + mFirstName + '\'' +
                ", mLastName='" + mLastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other instanceof User) {
            return this.mEmail.equals(((User) other).getEmail());
        } else {
            return false;
        }
    }
}
