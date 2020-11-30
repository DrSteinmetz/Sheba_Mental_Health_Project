package com.example.sheba_mental_health_project.model;

import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {

    private Date mAppointmentDate;
    private String mTherapistId;
    private String mPatientId;
    private MentalState mPatientMentalState;
    private PhysicalState mPatientPhysicalState;

    public Appointment(Date mAppointmentDate, String mPatientId, String mTherapistId, MentalState mPatientMentalState, PhysicalState mPatientPhysicalState) {
        this.mAppointmentDate = mAppointmentDate;
        this.mTherapistId = mTherapistId;
        this.mPatientId = mPatientId;
        this.mPatientMentalState = mPatientMentalState;
        this.mPatientPhysicalState = mPatientPhysicalState;
    }
}
