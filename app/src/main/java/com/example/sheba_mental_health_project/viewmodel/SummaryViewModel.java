package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.repository.Repository;

public class SummaryViewModel extends ViewModel {

    private final Repository mRepository;

    private MutableLiveData<String[]> mSetSummarySucceed;
    private MutableLiveData<String> mSetSummaryFailed;

    private final String TAG = "SummaryViewModel";


    public SummaryViewModel(final Context context) {
        this.mRepository = Repository.getInstance(context);
    }


    public MutableLiveData<String[]> getSetSummarySucceed() {
        if (mSetSummarySucceed == null) {
            mSetSummarySucceed = new MutableLiveData<>();
            attachSetSetSummaryListener();
        }
        return mSetSummarySucceed;
    }

    public MutableLiveData<String> getSetSummaryFailed() {
        if (mSetSummaryFailed == null) {
            mSetSummaryFailed = new MutableLiveData<>();
            attachSetSetSummaryListener();
        }
        return mSetSummaryFailed;
    }

    public void attachSetSetSummaryListener() {
        mRepository.setSetSummaryInterface(new Repository.RepositorySetSummaryInterface() {
            @Override
            public void onSetSummarySucceed(String[] summary) {
                mSetSummarySucceed.setValue(summary);
            }

            @Override
            public void onSetSummaryFailed(String error) {
                mSetSummaryFailed.setValue(error);
            }
        });
    }


    public Appointment getCurrentAppointment() {
        return mRepository.getCurrentAppointment();
    }

    public void setSummary(String diagnosisTxt, String recommendationsTxt) {
        mRepository.setSummary(diagnosisTxt, recommendationsTxt);
    }
}
