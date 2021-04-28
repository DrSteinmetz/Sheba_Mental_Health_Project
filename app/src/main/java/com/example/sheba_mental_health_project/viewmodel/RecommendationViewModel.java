package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.repository.AuthRepository;
import com.example.sheba_mental_health_project.repository.Repository;

public class RecommendationViewModel extends ViewModel {

    private final Repository mRepository;
    private final AuthRepository mAuthRepository;

    private MutableLiveData<Appointment> mGetLastAppointmentSucceed;
    private MutableLiveData<String> mGetLastAppointmentFailed;

    private final String TAG = "RecommendationViewModel";


    public RecommendationViewModel(final Context context) {
        this.mRepository = Repository.getInstance(context);
        this.mAuthRepository = AuthRepository.getInstance(context);
    }

    public MutableLiveData<Appointment> getGetLastAppointmentSucceed() {
        if (mGetLastAppointmentSucceed == null) {
            mGetLastAppointmentSucceed = new MutableLiveData<>();
            attachSetGetLastAppointmentListener();
        }
        return mGetLastAppointmentSucceed;
    }

    public MutableLiveData<String> getGetLastAppointmentFailed() {
        if (mGetLastAppointmentFailed == null) {
            mGetLastAppointmentFailed = new MutableLiveData<>();
            attachSetGetLastAppointmentListener();
        }
        return mGetLastAppointmentFailed;
    }

    private void attachSetGetLastAppointmentListener() {
        mRepository.setGetLastAppointmentInterface(new Repository.RepositoryGetLastAppointmentInterface() {
            @Override
            public void onGetLastAppointmentSucceed(Appointment lastAppointment) {
                mGetLastAppointmentSucceed.setValue(lastAppointment);
            }

            @Override
            public void onGetLastAppointmentFailed(String error) {
                mGetLastAppointmentFailed.setValue(error);
            }
        });
    }


    public void getLastMeeting() {
        final String patientId = mAuthRepository.getUser().getId();
        mRepository.getLastAppointment(patientId);
    }
}
