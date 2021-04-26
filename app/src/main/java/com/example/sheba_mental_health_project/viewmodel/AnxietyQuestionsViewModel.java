package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.Question;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.repository.Repository;

import java.util.List;

public class AnxietyQuestionsViewModel extends ViewModel {

    private final Repository mRepository;

    private MutableLiveData<List<Question>> mGetQuestionsOfPageSucceed;
    private MutableLiveData<String> mGetQuestionsOfPageFailed;

    private MutableLiveData<Appointment> mUpdateAnswersOfAppointmentSucceed;
    private MutableLiveData<String> mUpdateAnswersOfAppointmentFailed;

    private final String TAG = "AnxietyQuestionsVM";


    public AnxietyQuestionsViewModel(final Context context) {
        this.mRepository = Repository.getInstance(context);
    }

    public MutableLiveData<List<Question>> getGetQuestionsOfPageSucceed() {
        if (mGetQuestionsOfPageSucceed == null) {
            mGetQuestionsOfPageSucceed = new MutableLiveData<>();
            attachSetGetQuestionsOfPageListener();
        }
        return mGetQuestionsOfPageSucceed;
    }

    public MutableLiveData<String> getGetQuestionsOfPageFailed() {
        if (mGetQuestionsOfPageFailed == null) {
            mGetQuestionsOfPageFailed = new MutableLiveData<>();
            attachSetGetQuestionsOfPageListener();
        }
        return mGetQuestionsOfPageFailed;
    }

    public void attachSetGetQuestionsOfPageListener() {
        mRepository.setGetQuestionsOfPageInterface(new Repository.RepositoryGetQuestionsOfPageInterface() {
            @Override
            public void onGetQuestionsOfPageSucceed(List<Question> questions) {
                mGetQuestionsOfPageSucceed.setValue(questions);
            }

            @Override
            public void onGetQuestionsOfPageFailed(String error) {
                mGetQuestionsOfPageFailed.setValue(error);
            }
        });
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


    public Appointment getCurrentAppointment() {
        return mRepository.getCurrentAppointment();
    }

    public void getQuestions(final ViewModelEnum page) {
        mRepository.getQuestionsByPage(page);
    }

    public void updateAnswersOfAppointment() {
        mRepository.updateAnswersOfAppointment();
    }
}
