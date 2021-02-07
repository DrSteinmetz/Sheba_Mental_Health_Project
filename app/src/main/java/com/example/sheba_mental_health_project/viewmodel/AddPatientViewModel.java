package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.repository.AuthRepository;

public class AddPatientViewModel extends ViewModel {

    private AuthRepository mAuthRepository;

    private MutableLiveData<Void> mAddNewPatientSucceed;
    private MutableLiveData<String> mAddNewPatientFailed;

    private final String TAG = "AddPatientViewModel";

    public AddPatientViewModel(final Context context) {
        mAuthRepository = AuthRepository.getInstance(context);
    }

    public MutableLiveData<Void> getAddNewPatientSucceed() {
        if (mAddNewPatientSucceed == null) {
            mAddNewPatientSucceed = new MutableLiveData<>();
            attachSetAddNewPatientListener();
        }
        return mAddNewPatientSucceed;
    }

    public MutableLiveData<String> getAddNewPatientFailed() {
        if (mAddNewPatientFailed == null) {
            mAddNewPatientFailed = new MutableLiveData<>();
            attachSetAddNewPatientListener();
        }
        return mAddNewPatientFailed;
    }

    private void attachSetAddNewPatientListener() {
        mAuthRepository.setAddNewPatientListener(new AuthRepository.AuthRepoAddNewPatientInterface() {
            @Override
            public void onAddNewPatientSucceed() {
                mAddNewPatientSucceed.setValue(null);
            }

            @Override
            public void onAddNewPatientFailed(String error) {
                mAddNewPatientFailed.setValue(error);
            }
        });
    }

    public void addNewPatient(final String email, final String password,
                              final String firstName, final String lastName) {
        mAuthRepository.addNewPatient(email, password, firstName, lastName);
    }
}
