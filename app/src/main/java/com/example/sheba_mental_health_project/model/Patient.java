package com.example.sheba_mental_health_project.model;

import com.example.sheba_mental_health_project.model.enums.UserRoleEnum;

public class Patient extends User {

    public Patient() {super();}

    public Patient(String mId, String mEmail, String mFirstName, String mLastName) {
        super(mId, mEmail, mFirstName, mLastName);
    }
}
