package com.example.sheba_mental_health_project.model;

import com.example.sheba_mental_health_project.model.enums.UserRoleEnum;

import java.io.Serializable;

public abstract class User implements Serializable {

    private String mId;
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mToken;
    private UserRoleEnum mRole;

    public User() {}

    public User(String mId, String mFirstName, String mLastName,
                String mEmail, String mToken, UserRoleEnum mRole) {
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

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }

    public UserRoleEnum getRole() {
        return mRole;
    }

    public void setRole(UserRoleEnum mRole) {
        this.mRole = mRole;
    }

    @Override
    public String toString() {
        return "User: {" +
                "mId='" + mId + '\'' +
                ", mFirstName='" + mFirstName + '\'' +
                ", mLastName='" + mLastName + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mToken='" + mToken + '\'' +
                ", mRole=" + mRole +
                '}';
    }
}
