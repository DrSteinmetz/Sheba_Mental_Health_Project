package com.example.sheba_mental_health_project.model;

import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Appointment implements Serializable {

    private String mId;
    private Date mAppointmentDate;
    private Therapist mTherapist;
    private Patient mPatient;
   // private MentalState mPatientMentalState;
    private Map<String, List<PainPoint>> mPainPointsOfBodyPartMap = new HashMap<>();
    private List<String> mAnswers = new ArrayList<>();
    private Map<String, Integer> mFeelingsAnswersMap = new HashMap<>();
    private AppointmentStateEnum mState;
    private boolean mIsFinishedPreQuestions = false;

    public Appointment() {}

    public Appointment(Date appointmentDate, Therapist therapist, Patient patient) {
        this.mAppointmentDate = appointmentDate;
        this.mTherapist = therapist;
        this.mPatient = patient;
    }

    public Appointment(Date appointmentDate, Patient patient , AppointmentStateEnum appointmentState) {
        this.mAppointmentDate = appointmentDate;
        this.mPatient = patient;
        this.mState = appointmentState;
    }


    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
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

   /* public MentalState getPatientMentalState() {
        return mPatientMentalState;
    }

    public void setPatientMentalState(MentalState mPatientMentalState) {
        this.mPatientMentalState = mPatientMentalState;
    }*/

    public Map<String, List<PainPoint>> getPainPointsOfBodyPartMap() {
        return mPainPointsOfBodyPartMap;
    }

    public void setPainPointsOfBodyPartMap(Map<String, List<PainPoint>> mPainPointsOfBodyPartMap) {
        this.mPainPointsOfBodyPartMap = mPainPointsOfBodyPartMap;
    }

    public List<String> getAnswers() {
        return mAnswers;
    }

    public Set<String> answersAsSetGetter() {
        return new HashSet<>(mAnswers);
    }

    public void setAnswers(List<String> answers) {
        this.mAnswers = answers;
    }

    public void answersAsSetSetter(Set<String> answers) {
        this.mAnswers = new ArrayList<>(answers);
    }

    public AppointmentStateEnum getState() {
        return mState;
    }

    /*public String getState() {
        if (mState == null) {
            return "";
        }
        return mState.name();
    }*/

    public void setState(AppointmentStateEnum mState) {
        this.mState = mState;
    }

    /*public void setState(String state) {
        if (mState == null) {
            this.mState = null;
        } else {
            this.mState = AppointmentState.valueOf(state);
        }
    }*/

    public Map<String, Integer> getFeelingsAnswersMap() {
        return mFeelingsAnswersMap;
    }

    public void setFeelingsAnswersMap(Map<String, Integer> FeelingsAnswersMap) {
        this.mFeelingsAnswersMap = FeelingsAnswersMap;
    }

    public boolean getIsFinishedPreQuestions() {
        return mIsFinishedPreQuestions;
    }

    public void setIsFinishedPreQuestions(boolean isFinishedPreQuestions) {
        this.mIsFinishedPreQuestions = isFinishedPreQuestions;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "mAppointmentDate=" + mAppointmentDate +
                ", mTherapist=" + mTherapist +
                ", mPatient=" + mPatient +
               // ", mPatientMentalState=" + mPatientMentalState +
                ", mPainPointsOfBodyPartMap=" + mPainPointsOfBodyPartMap +
                ", mState=" + mState.name() +
                '}';
    }
}
