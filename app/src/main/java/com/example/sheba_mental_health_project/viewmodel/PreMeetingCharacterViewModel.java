package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.repository.Repository;

public class PreMeetingCharacterViewModel extends ViewModel {

    private final Repository mRepository;

    private MutableLiveData<Boolean> mOnUpdateFinishedPreQuestionsSucceed;
    private MutableLiveData<String> mOnUpdateFinishedPreQuestionsFailed;

    private final String TAG = "PreMeetingCharacterViewModel";


    public PreMeetingCharacterViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
    }

    public MutableLiveData<Boolean> getOnUpdateFinishedPreQuestionsSucceed() {
        if (mOnUpdateFinishedPreQuestionsSucceed == null) {
            mOnUpdateFinishedPreQuestionsSucceed = new MutableLiveData<>();
            attachSetUpdateFinishedPreQuestionsListener();
        }
        return mOnUpdateFinishedPreQuestionsSucceed;
    }

    public MutableLiveData<String> getOnUpdateFinishedPreQuestionsFailed() {
        if (mOnUpdateFinishedPreQuestionsFailed == null) {
            mOnUpdateFinishedPreQuestionsFailed = new MutableLiveData<>();
            attachSetUpdateFinishedPreQuestionsListener();
        }
        return mOnUpdateFinishedPreQuestionsFailed;
    }

    private void attachSetUpdateFinishedPreQuestionsListener() {
        mRepository.setUpdateFinishedPreQuestionsInterface(new Repository.RepositoryUpdateFinishedPreQuestionsInterface() {
            @Override
            public void onUpdateFinishedPreQuestionsSucceed(boolean isFinishedPreQuestions) {
                mOnUpdateFinishedPreQuestionsSucceed.setValue(isFinishedPreQuestions);
            }

            @Override
            public void onUpdateFinishedPreQuestionsFailed(String error) {
                mOnUpdateFinishedPreQuestionsFailed.setValue(error);
            }
        });
    }


    public Appointment getCurrentAppointment() {
        return mRepository.getCurrentAppointment();
    }

    public void updateFinishedPreQuestions() {
        mRepository.updateFinishedPreQuestions();
    }
}
