package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.repository.AuthRepository;

public class TherapistLoginViewModel extends ViewModel {

    private AuthRepository mAuthRepository;
    private String mEmail;
    private String mPassword;

    private MutableLiveData<Void> mTherapistLoginSucceed;
    private  MutableLiveData<String> mTherapistLoginFailed;

    private final String TAG = "TherapistLoginViewModel";


    public TherapistLoginViewModel(final Context context) {
        mAuthRepository = AuthRepository.getInstance(context);
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
        mAuthRepository.loginTherapist(mEmail, mPassword);
    }
}
