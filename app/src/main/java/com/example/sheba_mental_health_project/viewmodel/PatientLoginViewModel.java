package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.repository.AuthRepository;

public class PatientLoginViewModel extends ViewModel {

    private AuthRepository mAuthRepository;
    private String mEmail;
    private String mPassword;

    private MutableLiveData<Void> mPatientLoginSucceed;
    private MutableLiveData<String> mPatientLoginFailed;

    private final String TAG = "PatientLoginViewModel";

    public PatientLoginViewModel(final Context context) {
        mAuthRepository = AuthRepository.getInstance(context);
    }


    public MutableLiveData<Void> getPatientLoginSucceed() {
        if (mPatientLoginSucceed == null) {
            mPatientLoginSucceed = new MutableLiveData<>();
            attachSetOnPatientLoginListener();
        }
        return mPatientLoginSucceed;
    }

    public MutableLiveData<String> getPatientLoginFailed() {
        if (mPatientLoginFailed == null) {
            mPatientLoginFailed = new MutableLiveData<>();
            attachSetOnPatientLoginListener();
        }
        return mPatientLoginFailed;
    }

    private void attachSetOnPatientLoginListener() {
        mAuthRepository.setLoginPatientListener(new AuthRepository.AuthRepoLoginPatientInterface() {
            @Override
            public void onPatientLoginSucceed() {
                mPatientLoginSucceed.setValue(null);
            }

            @Override
            public void onPatientLoginFailed(String message) {
                mPatientLoginFailed.setValue(message);
            }
        });
    }


    public void setEmail(final String email) {
        if (!email.equals("")) {
            this.mEmail = email;
        } else {
            this.mEmail = " ";
        }
    }

    public void setPassword(final String password) {
        if (!password.equals(""))
            this.mPassword = password;
        else
            this.mPassword = " ";
    }

    public void login() {
        mAuthRepository.loginPatient(mEmail, mPassword);
    }
}
