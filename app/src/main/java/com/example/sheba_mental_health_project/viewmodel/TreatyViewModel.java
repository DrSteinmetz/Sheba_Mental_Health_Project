package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Answer;
import com.example.sheba_mental_health_project.model.AnswerBinary;
import com.example.sheba_mental_health_project.model.AnswerOpen;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class TreatyViewModel extends ViewModel {

    private final Repository mRepository;

    private final List<Answer> mAnswers;

    private MutableLiveData<Appointment> mUpdateAnswersOfAppointmentSucceed;
    private MutableLiveData<String> mUpdateAnswersOfAppointmentFailed;

    private final String TAG = "TreatyViewModel";

    public TreatyViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
        mAnswers = mRepository.getCurrentAppointment().getAnswers();
    }

    public MutableLiveData<Appointment> getUpdateAnswersOfAppointmentSucceed() {
        if (mUpdateAnswersOfAppointmentSucceed == null) {
            mUpdateAnswersOfAppointmentSucceed = new MutableLiveData<>();
            attachSetUpdateAnswersOfAppointmentListener();
        }
        return mUpdateAnswersOfAppointmentSucceed;
    }

    public MutableLiveData<String> getUpdateAnswersOfAppointmentFailed() {
        if (mUpdateAnswersOfAppointmentFailed == null) {
            mUpdateAnswersOfAppointmentFailed = new MutableLiveData<>();
            attachSetUpdateAnswersOfAppointmentListener();
        }
        return mUpdateAnswersOfAppointmentFailed;
    }

    public void attachSetUpdateAnswersOfAppointmentListener() {
        mRepository.setUpdateAnswersOfAppointmentInterface(new Repository.RepositoryUpdateAnswersOfAppointmentInterface() {
            @Override
            public void onUpdateAnswersOfAppointmentSucceed(Appointment appointment) {
                mUpdateAnswersOfAppointmentSucceed.setValue(appointment);
            }

            @Override
            public void onUpdateAnswersOfAppointmentFailed(String error) {
                mUpdateAnswersOfAppointmentFailed.setValue(error);
            }
        });
    }


    public boolean isQuestionChecked(final String id) {
        return mAnswers.contains(id);
    }

    public void updateAnswers(final boolean isChecked, final String id) {
        if (isChecked) {
            if (!mAnswers.contains(new AnswerBinary(id))) {
                mAnswers.add(new AnswerBinary(id));
                Log.d(TAG, "qwe onCheckedChanged: Added, Size: " + mAnswers.size());
            }
        } else {
            mAnswers.remove(new AnswerBinary(id));
            Log.d(TAG, "qwe onCheckedChanged: Removed");
        }
    }

    public void updateAnswerInCloud() {
        mRepository.updateAnswersOfAppointment();
    }
}
