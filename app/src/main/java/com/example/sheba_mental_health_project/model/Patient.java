package com.example.sheba_mental_health_project.model;

import com.example.sheba_mental_health_project.model.enums.UserRoleEnum;

public class Patient extends User {

    public Patient() {super();}

    public Patient(String id, String email, String firstName, String lastName) {
        super(id, email, firstName, lastName);
    }
}
