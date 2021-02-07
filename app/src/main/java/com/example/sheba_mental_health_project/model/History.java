package com.example.sheba_mental_health_project.model;

import java.io.Serializable;
import java.util.List;

public class History implements Serializable {

    private String mPatientId;
    private List<Appointment> mAppointments;
}
