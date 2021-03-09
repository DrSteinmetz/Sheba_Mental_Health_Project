package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.Patient;
import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;
import com.example.sheba_mental_health_project.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class HistoryViewModel extends ViewModel {

    private final Repository mRepository;

    private Patient mPatient;

    private final List<Appointment> mAppointments = new ArrayList<>();

    private MutableLiveData<List<Appointment>> mGetAppointmentsOfSpecificPatientSucceed;
    private MutableLiveData<String> mGetMyAppointmentsOfSpecificPatientFailed;

    private final String TAG = "HistoryViewModel";


    public HistoryViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
    }

    public MutableLiveData<List<Appointment>> getAppointmentsOfSpecificPatientSucceed() {
        if (mGetAppointmentsOfSpecificPatientSucceed == null) {
            mGetAppointmentsOfSpecificPatientSucceed = new MutableLiveData<>();
            attachGetAppointmentsOfSpecificPatientListener();
        }
        return mGetAppointmentsOfSpecificPatientSucceed;
    }

    public MutableLiveData<String> getAppointmentsOfSpecificPatientFailed() {
        if (mGetMyAppointmentsOfSpecificPatientFailed == null) {
            mGetMyAppointmentsOfSpecificPatientFailed = new MutableLiveData<>();
            attachGetAppointmentsOfSpecificPatientListener();
        }
        return mGetMyAppointmentsOfSpecificPatientFailed;
    }

    public void attachGetAppointmentsOfSpecificPatientListener() {
        mRepository.setGetAppointmentOfSpecificPatient(new Repository.RepositoryGetAppointmentOfSpecificPatientInterface() {
            @Override
            public void onGetAppointmentOfSpecificPatientSucceed(List<Appointment> appointments) {
                mAppointments.clear();
                mAppointments.addAll(appointments);
                mGetAppointmentsOfSpecificPatientSucceed.setValue(appointments);
            }

            @Override
            public void onGetAppointmentOfSpecificPatientFailed(String error) {
                mGetMyAppointmentsOfSpecificPatientFailed.setValue(error);
            }
        });
    }


    public final List<Appointment> getAppointments() {
        return mAppointments;
    }

    public final Patient getPatient() {
        return mPatient;
    }

    public void setPatient(final Patient patient) {
        this.mPatient = patient;
    }

    public void getAppointmentsOfSpecificPatient() {
        final List<AppointmentStateEnum> stateQuery = new ArrayList<>();
        stateQuery.add(AppointmentStateEnum.Ended);

        if (mPatient != null) {
            mRepository.getAppointmentsOfSpecificPatient(mPatient.getId(), stateQuery);
        }
    }

    public void removeGetAppointmentsOfSpecificPatientListener() {
        mRepository.removePatientAppointmentsListener();
    }
}
