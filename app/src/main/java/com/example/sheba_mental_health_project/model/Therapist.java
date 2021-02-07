package com.example.sheba_mental_health_project.model;

import com.example.sheba_mental_health_project.model.enums.UserRoleEnum;

import java.util.List;

public class Therapist extends User {

    public Therapist() {
        super();
    }

    public Therapist(String mId, String mEmail, String mFirstName, String mLastName) {
        super(mId, mEmail, mFirstName, mLastName);
    }
}
