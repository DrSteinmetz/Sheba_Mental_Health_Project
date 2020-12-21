package com.example.sheba_mental_health_project.model;

import com.example.sheba_mental_health_project.model.enums.AppointmentState;
import com.example.sheba_mental_health_project.model.enums.BodyPart;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Appointment implements Serializable {

    private Date mAppointmentDate;
    private Therapist mTherapist;
    private Patient mPatient;
    private MentalState mPatientMentalState;
//    private PhysicalState mPatientPhysicalState;
    private Map<BodyPart, List<PainPoint>> mPainPointsOfBodyPartMap;
//    private EnumMap<BodyPart, List<PainPoint>> painPointsOfBodyPartMap;
    private AppointmentState mState;

    public Appointment() {
    }

    public Appointment(Date appointmentDate, Therapist therapist, Patient patient) {
        this.mAppointmentDate = appointmentDate;
        this.mTherapist = therapist;
        this.mPatient = patient;
    }

    public Appointment(Date appointmentDate, Patient patient ,AppointmentState appointmentState) {
        this.mAppointmentDate = appointmentDate;
        this.mPatient = patient;
        this.mState = appointmentState;
    }

    public Date getAppointmentDate() {
        return mAppointmentDate;
    }

    public void setAppointmentDate(Date mAppointmentDate) {
        this.mAppointmentDate = mAppointmentDate;
    }

    public Therapist getTherapist() {
        return mTherapist;
    }

    public void setTherapist(Therapist mTherapist) {
        this.mTherapist = mTherapist;
    }

    public Patient getPatient() {
        return mPatient;
    }

    public void setPatient(Patient mPatient) {
        this.mPatient = mPatient;
    }

    public MentalState getPatientMentalState() {
        return mPatientMentalState;
    }

    public void setPatientMentalState(MentalState mPatientMentalState) {
        this.mPatientMentalState = mPatientMentalState;
    }

    public Map<BodyPart, List<PainPoint>> getPainPointsOfBodyPartMap() {
        return mPainPointsOfBodyPartMap;
    }

    public void setPainPointsOfBodyPartMap(Map<BodyPart, List<PainPoint>> mPainPointsOfBodyPartMap) {
        this.mPainPointsOfBodyPartMap = mPainPointsOfBodyPartMap;
    }

    public AppointmentState getStateEnum() {
        return mState;
    }

    public String getState() {
        if (mState == null) {
            return "";
        }
        return mState.name();
    }

    public void setStateEnum(AppointmentState mState) {
        this.mState = mState;
    }

    public void setState(String state) {
        if (mState == null) {
            this.mState=null;
        }else {
            this.mState = AppointmentState.valueOf(state);
        }

    }

    @Override
    public String toString() {
        return "Appointment{" +
                "mAppointmentDate=" + mAppointmentDate +
                ", mTherapist=" + mTherapist +
                ", mPatient=" + mPatient +
                ", mPatientMentalState=" + mPatientMentalState +
                ", mPainPointsOfBodyPartMap=" + mPainPointsOfBodyPartMap +
                ", mState=" + mState +
                '}';
    }
}
