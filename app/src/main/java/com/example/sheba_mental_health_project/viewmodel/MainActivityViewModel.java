package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.repository.AuthRepository;
import com.example.sheba_mental_health_project.repository.Repository;

public class MainActivityViewModel extends ViewModel {

    private final Repository mRepository;

    private final AuthRepository mAuthRepository;

//    TODO: add MutableLiveData

    private boolean mIsTherapist;

    private final String TAG = "MainActivityViewModel";

    public MainActivityViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
        mAuthRepository = AuthRepository.getInstance(context);
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

    public void logout() {
        mAuthRepository.logOut();
    }
}
