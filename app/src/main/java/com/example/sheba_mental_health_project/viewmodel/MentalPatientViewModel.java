package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.Feeling;
import com.example.sheba_mental_health_project.model.Question;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.repository.Repository;

import java.util.List;

public class MentalPatientViewModel extends ViewModel {

    private final Repository mRepository;

    private MutableLiveData<List<Feeling>> mGetPatientFeelingsSucceed;
    private MutableLiveData<String> mGetPatientFeelingsFailed;

    private MutableLiveData<Appointment> mUpdateFeelingsAnswersSucceed;
    private MutableLiveData<String> mUpdateFeelingsAnswersFailed;

    private final String TAG = "MentalPatientViewModel";


    public MentalPatientViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
    }


    public MutableLiveData<List<Feeling>> getGetPatientFeelingSucceed() {
        if (mGetPatientFeelingsSucceed == null) {
            mGetPatientFeelingsSucceed = new MutableLiveData<>();
            attachSetGetPatientFeelingsListener();
        }
        return mGetPatientFeelingsSucceed;
    }

    public MutableLiveData<String> getGetPatientFeelingFailed() {
        if (mGetPatientFeelingsFailed == null) {
            mGetPatientFeelingsFailed = new MutableLiveData<>();
            attachSetGetPatientFeelingsListener();
        }
        return mGetPatientFeelingsFailed;
    }

    public void attachSetGetPatientFeelingsListener() {
        mRepository.setGetPatientFeelingsInterface(new Repository.RepositoryGetPatientFeelingsInterface() {
            @Override
            public void onGetPatientFeelingsSucceed(List<Feeling> feelings) {
                mGetPatientFeelingsSucceed.setValue(feelings);
            }

            @Override
            public void onGetPatientFeelingsFailed(String error) {
                mGetPatientFeelingsFailed.setValue(error);
            }
        });
    }

    public MutableLiveData<Appointment> getUpdateFeelingsAnswersSucceed() {
        if (mUpdateFeelingsAnswersSucceed == null) {
            mUpdateFeelingsAnswersSucceed = new MutableLiveData<>();
            attachSetUpdateFeelingsAnswersListener();
        }
        return mUpdateFeelingsAnswersSucceed;
    }

    public MutableLiveData<String> getUpdateFeelingsAnswersFailed() {
        if (mUpdateFeelingsAnswersFailed == null) {
            mUpdateFeelingsAnswersFailed = new MutableLiveData<>();
            attachSetUpdateFeelingsAnswersListener();
        }
        return mUpdateFeelingsAnswersFailed;
    }

    public void attachSetUpdateFeelingsAnswersListener() {
        mRepository.setUpdateAnswersOfFeelingsInterface(new Repository.RepositoryUpdateAnswersOfFeelingsInterface() {
            @Override
            public void onUpdateAnswersOfFeelingsSucceed(Appointment appointment) {
                mUpdateFeelingsAnswersSucceed.setValue(appointment);
            }

            @Override
            public void onUpdateAnswersOfFeelingsFailed(String error) {
                mUpdateFeelingsAnswersFailed.setValue(error);
            }
        });
    }


    public void updateAnswersOfFeelings() {
        mRepository.updateAnswersOfFeelings();
    }

    public void getAllFeelings() {
        mRepository.getFeelings();
    }

    public Appointment getCurrentAppointment() {
        return mRepository.getCurrentAppointment();
    }
}