package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.repository.AuthRepository;
import com.example.sheba_mental_health_project.repository.Repository;

public class MainActivityViewModel extends ViewModel {

    private final Repository mRepository;

    private final AuthRepository mAuthRepository;

    private MutableLiveData<Appointment> mGetLiveAppointmentSucceed;
    private MutableLiveData<String> mGetLiveAppointmentFailed;

    private boolean mIsTherapist;

    private final String TAG = "MainActivityViewModel";


    public MainActivityViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
        mAuthRepository = AuthRepository.getInstance(context);

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


    public boolean isTherapist() {
        return mIsTherapist;
    }

    public void setIsTherapist(boolean isTherapist) {
        this.mIsTherapist = isTherapist;
    }

    public Appointment getCurrentAppointment() {
        return mRepository.getCurrentAppointment();
    }

    public void setCurrentAppointment(final Appointment appointment) {
        mRepository.setCurrentAppointment(appointment);
    }

    public void getLiveAppointment() {
        if (getCurrentAppointment() != null) {
            mRepository.getLiveAppointmentState();
        }
    }

    public void removeLiveAppointmentListener() {
        mRepository.removeLiveAppointmentListener();
    }

    public void logout() {
        mAuthRepository.logOut();
    }
}
