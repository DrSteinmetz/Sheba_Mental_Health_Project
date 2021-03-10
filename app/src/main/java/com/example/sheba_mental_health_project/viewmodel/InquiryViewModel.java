package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.Question;
import com.example.sheba_mental_health_project.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class InquiryViewModel extends ViewModel {

    private final Repository mRepository;

    private List<Question> mExpectationsQuestions;
    private List<Question> mCovidQuestions;
    private List<Question> mStatementQuestions;
    private List<Question> mSocialQuestions;
    private List<Question> mHabitsQuestions;
    private List<Question> mMentalQuestions;

    private MutableLiveData<List<Question>> mGetAllQuestionsSucceed;
    private MutableLiveData<String> mGetAllQuestionsFailed;

    private final String TAG = "InquiryViewModel";


    public InquiryViewModel(final Context context) {
        this.mRepository = Repository.getInstance(context);
    }

    public MutableLiveData<List<Question>> getGetAllQuestionsSucceed() {
        if (mGetAllQuestionsSucceed == null) {
            mGetAllQuestionsSucceed = new MutableLiveData<>();
            attachSetGetQuestionsOfPageListener();
        }
        return mGetAllQuestionsSucceed;
    }

    public MutableLiveData<String> getGetAllQuestionsFailed() {
        if (mGetAllQuestionsFailed == null) {
            mGetAllQuestionsFailed = new MutableLiveData<>();
            attachSetGetQuestionsOfPageListener();
        }
        return mGetAllQuestionsFailed;
    }

    public void attachSetGetQuestionsOfPageListener() {
        mRepository.setGetQuestionsOfPageInterface(new Repository.RepositoryGetQuestionsOfPageInterface() {
            @Override
            public void onGetQuestionsOfPageSucceed(List<Question> questions) {
                mExpectationsQuestions = new ArrayList<>();
                mCovidQuestions = new ArrayList<>();
                mStatementQuestions = new ArrayList<>();
                mSocialQuestions = new ArrayList<>();
                mHabitsQuestions = new ArrayList<>();
                mMentalQuestions = new ArrayList<>();

                for (Question question : questions) {
                    switch (question.getPage()) {
                        case Treaty:
                        case Bureaucracy:
                            mExpectationsQuestions.add(question);
                            break;
                        case CovidQuestions:
                            mCovidQuestions.add(question);
                            break;
                        case SanityCheck:
                        case Statement:
                            mStatementQuestions.add(question);
                            break;
                        case SocialQuestions:
                            mSocialQuestions.add(question);
                            break;
                        case HabitsQuestions:
                            mHabitsQuestions.add(question);
                            break;
                        case MentalQuestions:
                            mMentalQuestions.add(question);
                            break;
                    }
                }

                mGetAllQuestionsSucceed.setValue(questions);
            }

            @Override
            public void onGetQuestionsOfPageFailed(String error) {
                mGetAllQuestionsFailed.setValue(error);
            }
        });
    }


    public final Appointment getCurrentAppointment() {
        return mRepository.getCurrentAppointment();
    }

    public void getAllQuestions() {
        mRepository.getAllQuestions();
    }

    public List<Question> getExpectationsQuestions() {
        return mExpectationsQuestions;
    }

    public List<Question> getCovidQuestions() {
        return mCovidQuestions;
    }

    public List<Question> getStatementQuestions() {
        return mStatementQuestions;
    }

    public List<Question> getSocialQuestions() {
        return mSocialQuestions;
    }

    public List<Question> getHabitsQuestions() {
        return mHabitsQuestions;
    }

    public List<Question> getMentalQuestions() {
        return mMentalQuestions;
    }
}
