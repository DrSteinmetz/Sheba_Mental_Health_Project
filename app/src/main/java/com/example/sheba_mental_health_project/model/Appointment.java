package com.example.sheba_mental_health_project.model;

import com.example.sheba_mental_health_project.model.enums.BodyPart;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Appointment implements Serializable {

    private Date mAppointmentDate;
    private String mTherapistId;
    private String mPatientId;
    private MentalState mPatientMentalState;
    private PhysicalState mPatientPhysicalState;
    private Map<BodyPart, List<PainPoint>> mPainPointsOfBodyPartMap;
//    private EnumMap<BodyPart, List<PainPoint>> painPointsOfBodyPartMap;

    public Appointment(Date appointmentDate, String therapistId, String patientId,
                       MentalState patientMentalState, PhysicalState patientPhysicalState,
                       Map<BodyPart, List<PainPoint>> painPointsOfBodyPartMap) {
        this.mAppointmentDate = appointmentDate;
        this.mTherapistId = therapistId;
        this.mPatientId = patientId;
        this.mPatientMentalState = patientMentalState;
        this.mPatientPhysicalState = patientPhysicalState;
        this.mPainPointsOfBodyPartMap = painPointsOfBodyPartMap;
    }
}
