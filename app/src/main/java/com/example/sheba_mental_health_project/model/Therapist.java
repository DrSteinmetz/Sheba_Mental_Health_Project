package com.example.sheba_mental_health_project.model;

import com.example.sheba_mental_health_project.model.enums.UserRoleEnum;

import java.util.List;

public class Therapist extends User {

    public Therapist() {
        super();
    }

    public Therapist(String mId, String mFirstName, String mLastName,
                     String mEmail, String mToken, UserRoleEnum mRole) {
        super(mId, mFirstName, mLastName, mEmail, mToken, mRole);
    }
}
