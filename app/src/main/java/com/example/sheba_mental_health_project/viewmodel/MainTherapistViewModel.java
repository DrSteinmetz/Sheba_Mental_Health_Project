package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.User;
import com.example.sheba_mental_health_project.repository.AuthRepository;
import com.example.sheba_mental_health_project.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class MainTherapistViewModel extends ViewModel {

    private final Repository mRepository;

    private final AuthRepository mAuthRepository;

    private final List<Appointment> mAppointments = new ArrayList<>();

    private MutableLiveData<List<Appointment>> mGetMyAppointmentsSucceed;
    private MutableLiveData<String> mGetMyAppointmentsFailed;

    private MutableLiveData<Void> mTherapistLoginSucceed;
    private  MutableLiveData<String> mTherapistLoginFailed;

    private final String TAG = "MainTherapistViewModel";

    public MainTherapistViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
        mAuthRepository = AuthRepository.getInstance(context);
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

    public MutableLiveData<Void> getTherapistLoginSucceed() {
        if (mTherapistLoginSucceed == null) {
            mTherapistLoginSucceed = new MutableLiveData<>();
            attachSetOnTherapistLoginListener();
        }
        return mTherapistLoginSucceed;
    }

    public MutableLiveData<String> getTherapistLoginFailed() {
        if (mTherapistLoginFailed == null) {
            mTherapistLoginFailed = new MutableLiveData<>();
            attachSetOnTherapistLoginListener();
        }
        return mTherapistLoginFailed;
    }

    private void attachSetOnTherapistLoginListener() {
        mAuthRepository.setLoginTherapistListener(new AuthRepository.AuthRepoLoginTherapistInterface() {
            @Override
            public void onTherapistLoginSucceed() {
                mTherapistLoginSucceed.setValue(null);
            }

            @Override
            public void onTherapistLoginFailed(String message) {
                mTherapistLoginFailed.setValue(message);
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

    public void setCurrentAppointment(Appointment appointment) {
        mRepository.setCurrentAppointment(appointment);
    }

    public User getAuthUser() {
        return mAuthRepository.getUser();
    }

    public String getTherapistFullName() {
        return mAuthRepository.getUser().getFullName();
    }

    public void getTherapistForLogin() {
        mAuthRepository.getTherapistForLogin(mAuthRepository.getFirebaseUserId());
    }
}
