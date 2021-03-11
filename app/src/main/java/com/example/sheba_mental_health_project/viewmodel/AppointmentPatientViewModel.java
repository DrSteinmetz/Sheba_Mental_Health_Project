package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.repository.Repository;

public class AppointmentPatientViewModel extends ViewModel {

    private final Repository mRepository;

    private MutableLiveData<Appointment> mGetLiveAppointmentSucceed;
    private MutableLiveData<String> mGetLiveAppointmentFailed;

    private final String TAG = "AppointmentPatientVM";


    public AppointmentPatientViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
    }

    public MutableLiveData<Appointment> getGetLiveAppointmentSucceed() {
        if (mGetLiveAppointmentSucceed == null) {
            mGetLiveAppointmentSucceed = new MutableLiveData<>();
            attachGetLiveAppointmentListener();
        }
        return mGetLiveAppointmentSucceed;
    }

    public MutableLiveData<String> getGetLiveAppointmentFailed() {
        if (mGetLiveAppointmentFailed == null) {
            mGetLiveAppointmentFailed = new MutableLiveData<>();
            attachGetLiveAppointmentListener();
        }
        return mGetLiveAppointmentFailed;
    }

    public void attachGetLiveAppointmentListener() {
        mRepository.setGetLiveAppointmentInterface(new Repository.RepositoryGetLiveAppointmentInterface() {
            @Override
            public void onGetLiveAppointmentSucceed(Appointment appointment) {
                Log.d(TAG, "onGetLiveAppointmentSucceed: " + appointment.getState().name());
                mGetLiveAppointmentSucceed.setValue(appointment);
            }

            @Override
            public void onGetLiveAppointmentFailed(String error) {
                mGetLiveAppointmentFailed.setValue(error);
            }
        });
    }


    public Appointment getCurrentAppointment() {
        return mRepository.getCurrentAppointment();
    }

    public void getLiveAppointment() {
        mRepository.getLiveAppointment();
    }
}
