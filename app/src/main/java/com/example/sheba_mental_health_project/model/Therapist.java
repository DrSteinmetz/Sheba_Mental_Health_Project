package com.example.sheba_mental_health_project.model;

import com.example.sheba_mental_health_project.model.enums.UserRoleEnum;

import java.util.List;

public class Therapist extends User {

    public Therapist() {
        super();
    }

    public Therapist(String id, String email, String firstName, String lastName) {
        super(id, email, firstName, lastName);
    }
}
