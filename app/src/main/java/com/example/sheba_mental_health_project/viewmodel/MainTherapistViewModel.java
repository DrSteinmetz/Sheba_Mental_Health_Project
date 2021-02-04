package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class MainTherapistViewModel extends ViewModel {

    private Repository mRepository;

    private final List<Appointment> mAppointments = new ArrayList<>();

    private MutableLiveData<List<Appointment>> mGetMyAppointmentsSucceed;
    private MutableLiveData<String> mGetMyAppointmentsFailed;

    private final String TAG = "MainTherapistViewModel";

    public MainTherapistViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
    }

    public MutableLiveData<List<Appointment>> getMyAppointmentsSucceed() {
        if(mGetMyAppointmentsSucceed == null){
            mGetMyAppointmentsSucceed = new MutableLiveData<>();
            attachGetMyAppointmentsListener();
        }
        return mGetMyAppointmentsSucceed;
    }

    public MutableLiveData<String> getMyAppointmentsFailed() {
        if(mGetMyAppointmentsFailed == null){
            mGetMyAppointmentsFailed = new MutableLiveData<>();
            attachGetMyAppointmentsListener();
        }
        return mGetMyAppointmentsFailed;
    }

    public void attachGetMyAppointmentsListener() {
        mRepository.setGetAppointmentOfSpecificTherapist(new Repository.RepositoryGetAppointmentOfSpecificTherapistInterface() {
            @Override
            public void onGetAppointmentOfSpecificTherapistSucceed(List<Appointment> appointments) {
                mAppointments.clear();
                mAppointments.addAll(appointments);
                mGetMyAppointmentsSucceed.setValue(appointments);
            }

            @Override
            public void onGetAppointmentOfSpecificTherapistFailed(String error) {
                mGetMyAppointmentsFailed.setValue(error);
            }
        });
    }


    public void getMyAppointments() {
        mRepository.getAppointmentsOfSpecificTherapist();
    }

    public final List<Appointment> getAppointments() {
        return mAppointments;
    }

    public void removeTherapistAppointmentsListener() {
        mRepository.removeTherapistAppointmentsListener();
    }
}
