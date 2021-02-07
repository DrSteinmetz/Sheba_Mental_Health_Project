package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.repository.AuthRepository;

public class WelcomeViewModel extends ViewModel {

    private final AuthRepository mAuthRepository;

    private final SharedPreferences mSharedPreferences;

    private MutableLiveData<Void> mTherapistLoginSucceed;
    private  MutableLiveData<String> mTherapistLoginFailed;

    private MutableLiveData<Void> mPatientLoginSucceed;
    private  MutableLiveData<String> mPatientLoginFailed;

    private final String IS_THERAPIST = "is_therapist";

    private final String TAG = "WelcomeViewModel";


    public WelcomeViewModel(final Context context) {
        mAuthRepository = AuthRepository.getInstance(context);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
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


    public boolean isAuthenticated() {
        return mAuthRepository.isAuthenticated();
    }

    public void initializeLoggedInUser() {
        if (mSharedPreferences.getBoolean(IS_THERAPIST, false)) {
            mAuthRepository.getTherapistForLogin(mAuthRepository.getFirebaseUserId());
        } else {
            mAuthRepository.getPatientForLogin(mAuthRepository.getFirebaseUserId());
        }
    }
}
