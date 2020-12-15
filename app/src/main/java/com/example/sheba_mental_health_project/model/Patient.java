package com.example.sheba_mental_health_project.model;

import com.example.sheba_mental_health_project.model.enums.UserRoleEnum;

public class Patient extends User {

    public Patient() {super();}

    public Patient(String mId, String mFirstName, String mLastName,
                   String mEmail, String mToken, UserRoleEnum mRole) {
        super(mId, mFirstName, mLastName, mEmail, mToken, mRole);
    }
}
