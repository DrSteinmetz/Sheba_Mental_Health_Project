package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.Feeling;
import com.example.sheba_mental_health_project.repository.Repository;

import java.util.List;
import java.util.Map;

public class TherapistMentalGenericViewModel extends ViewModel {

    private final Repository mRepository;

    private Appointment mAppointment;

    private MutableLiveData<List<Feeling>> mGetFeelingsSucceed;
    private MutableLiveData<String> mGetFeelingsFailed;

    private MutableLiveData<Map<String, Integer>> mGetFeelingsAnswersSucceed;
    private MutableLiveData<String> mGetFeelingsAnswersFailed;

    private final String TAG = "TherapistMentalStateViewModel";


    public TherapistMentalGenericViewModel(final Context context) {
        this.mRepository = Repository.getInstance(context);
    }

    public MutableLiveData<List<Feeling>> getGetFeelingsSucceed() {
        if (mGetFeelingsSucceed == null) {
            mGetFeelingsSucceed = new MutableLiveData<>();
            attachSetGetFeelingsListener();
        }
        return mGetFeelingsSucceed;
    }

    public MutableLiveData<String> getGetFeelingsFailed() {
        if (mGetFeelingsFailed == null) {
            mGetFeelingsFailed = new MutableLiveData<>();
            attachSetGetFeelingsListener();
        }
        return mGetFeelingsFailed;
    }

    public void attachSetGetFeelingsListener() {
        mRepository.setGetPatientFeelingsInterface(new Repository.RepositoryGetPatientFeelingsInterface() {
            @Override
            public void onGetPatientFeelingsSucceed(List<Feeling> feelings) {
                mGetFeelingsSucceed.setValue(feelings);
            }

            @Override
            public void onGetPatientFeelingsFailed(String error) {
                mGetFeelingsFailed.setValue(error);
            }
        });
    }

    public MutableLiveData<Map<String, Integer>> getGetFeelingsAnswersSucceed() {
        if (mGetFeelingsAnswersSucceed == null) {
            mGetFeelingsAnswersSucceed = new MutableLiveData<>();
            attachSetGetFeelingsAnswersListener();
        }
        return mGetFeelingsAnswersSucceed;
    }

    public MutableLiveData<String> getGetFeelingsAnswersFailed() {
        if (mGetFeelingsAnswersFailed == null) {
            mGetFeelingsAnswersFailed = new MutableLiveData<>();
            attachSetGetFeelingsAnswersListener();
        }
        return mGetFeelingsAnswersFailed;
    }

    public void attachSetGetFeelingsAnswersListener() {
        mRepository.setGetFeelingsAnswersInterface(new Repository.RepositoryGetFeelingsAnswersInterface() {
            @Override
            public void onGetFeelingsAnswersSucceed(Map<String, Integer> feelingsAnswers) {
                mGetFeelingsAnswersSucceed.setValue(feelingsAnswers);
            }

            @Override
            public void onGetFeelingsAnswersFailed(String error) {
                mGetFeelingsAnswersFailed.setValue(error);
            }
        });
    }


    public Appointment getAppointment() {
        return mAppointment;
    }

    public void setAppointment(final Appointment mAppointment) {
        this.mAppointment = mAppointment;
    }

    public void getFeelings() {
        mRepository.getFeelings();
    }

    public void getFeelingsAnswers() {
        mRepository.getFeelingsAnswers(mAppointment);
    }

    public void removeGetFeelingsAnswersListener() {
        mRepository.removeGetFeelingsAnswersListener();
    }
}
