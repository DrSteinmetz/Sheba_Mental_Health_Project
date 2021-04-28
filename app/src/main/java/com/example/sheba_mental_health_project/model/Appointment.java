package com.example.sheba_mental_health_project.model;

import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Appointment implements Serializable {

    private String mId;
    private Date mAppointmentDate;
    private Therapist mTherapist;
    private Patient mPatient;
    private Map<String, List<PainPoint>> mPainPointsOfBodyPartMap = new HashMap<>();
    private List<Answer> mAnswers = new ArrayList<>();
    private Map<String, Integer> mFeelingsAnswersMap = new HashMap<>();
    private AppointmentStateEnum mState;
    private String mRecommendations;
    private String mDiagnosis;
    private List<String> mDocuments;
    private boolean mIsFinishedPreQuestions = false;

    public Appointment() {}

    public Appointment(final Appointment appointment) {
        this.mId = appointment.mId;
        this.mAppointmentDate = appointment.mAppointmentDate;
        this.mTherapist = appointment.mTherapist;
        this.mPatient = appointment.mPatient;
        this.mPainPointsOfBodyPartMap = appointment.mPainPointsOfBodyPartMap;
        this.mAnswers = appointment.mAnswers;
        this.mFeelingsAnswersMap = appointment.mFeelingsAnswersMap;
        this.mState = appointment.mState;
        this.mRecommendations = appointment.mRecommendations;
        this.mDiagnosis = appointment.mDiagnosis;
        this.mDocuments = appointment.mDocuments;
        this.mIsFinishedPreQuestions = appointment.mIsFinishedPreQuestions;
    }

    public Appointment(Date appointmentDate, Therapist therapist, Patient patient) {
        this.mAppointmentDate = appointmentDate;
        this.mTherapist = therapist;
        this.mPatient = patient;
    }

    public Appointment(Date appointmentDate, Patient patient, AppointmentStateEnum appointmentState) {
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

    public void setAppointmentDate(Date appointmentDate) {
        this.mAppointmentDate = appointmentDate;
    }

    public Therapist getTherapist() {
        return mTherapist;
    }

    public void setTherapist(Therapist therapist) {
        this.mTherapist = therapist;
    }

    public Patient getPatient() {
        return mPatient;
    }

    public void setPatient(Patient patient) {
        this.mPatient = patient;
    }

    public Map<String, List<PainPoint>> getPainPointsOfBodyPartMap() {
        return mPainPointsOfBodyPartMap;
    }

    public void setPainPointsOfBodyPartMap(Map<String, List<PainPoint>> painPointsOfBodyPartMap) {
        this.mPainPointsOfBodyPartMap = painPointsOfBodyPartMap;
    }

    public List<Answer> getAnswers() {
        return mAnswers;
    }

    public Set<Answer> answersAsSetGetter() {
        return new HashSet<>(mAnswers);
    }

    public void setAnswers(List<Answer> answers) {
        this.mAnswers = answers;
    }

    public void answersAsSetSetter(Set<Answer> answers) {
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

    public void setState(AppointmentStateEnum state) {
        this.mState = state;
    }

    /*public void setState(String state) {
        if (mState == null) {
            this.mState = null;
        } else {
            this.mState = AppointmentState.valueOf(state);
        }
    }*/

    public String getRecommendations() {
        return mRecommendations;
    }

    public void setRecommendations(String recommendations) {
        this.mRecommendations = recommendations;
    }

    public String getDiagnosis() {
        return mDiagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.mDiagnosis = diagnosis;
    }

    public Map<String, Integer> getFeelingsAnswersMap() {
        return mFeelingsAnswersMap;
    }

    public void setFeelingsAnswersMap(Map<String, Integer> feelingsAnswersMap) {
        this.mFeelingsAnswersMap = feelingsAnswersMap;
    }

    public List<String> getDocuments() {
        return mDocuments;
    }

    public void setDocuments(List<String> documents) {
        this.mDocuments = documents;
    }

    public boolean getIsFinishedPreQuestions() {
        return mIsFinishedPreQuestions;
    }

    public void setIsFinishedPreQuestions(boolean isFinishedPreQuestions) {
        this.mIsFinishedPreQuestions = isFinishedPreQuestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Appointment that = (Appointment) o;

        return Objects.equals(mId, that.mId);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "Id='" + mId + '\'' +
                ", AppointmentDate=" + mAppointmentDate +
                ", Therapist=" + mTherapist +
                ", Patient=" + mPatient +
                ", PainPointsOfBodyPartMap=" + mPainPointsOfBodyPartMap +
                ", Answers=" + mAnswers +
                ", FeelingsAnswersMap=" + mFeelingsAnswersMap +
                ", State=" + mState +
                ", Recommendations=" + mRecommendations +
                ", Diagnosis=" + mDiagnosis +
                ", Documents=" + mDocuments +
                ", IsFinishedPreQuestions=" + mIsFinishedPreQuestions +
                '}';
    }
}
