package com.example.sheba_mental_health_project.model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public abstract class User implements Serializable {

    private String mId;
    private String mEmail;
    private String mFirstName;
    private String mLastName;

    public User() {}

    public User(String id, String email, String firstName, String lastName) {
        this.mId = id;
        this.mEmail = email;
        this.mFirstName = firstName;
        this.mLastName = lastName;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = lastName;
    }

    public String getFullName() {
        return mFirstName + " " + mLastName;
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
