package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Answer;
import com.example.sheba_mental_health_project.model.AnswerBinary;
import com.example.sheba_mental_health_project.model.AnswerOpen;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.Question;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.repository.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InquiryViewModel extends ViewModel {

    private final Repository mRepository;

    private List<Question> mExpectationsQuestions;
    private List<Question> mCovidQuestions;
    private List<Question> mStatementQuestions;
    private List<Question> mSocialQuestions;
    private List<Question> mHabitsQuestions;
    private List<Question> mMentalQuestions;
    private List<Question> mAnxietyQuestions;
    private List<Question> mAngerQuestions;
    private List<Question> mDepressionQuestions;

    private MutableLiveData<List<Question>> mGetAllQuestionsSucceed;
    private MutableLiveData<String> mGetAllQuestionsFailed;

    private MutableLiveData<List<Answer>> mGetLiveAnswersSucceed;
    private MutableLiveData<String> mGetLiveAnswersFailed;

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
                mGetAllQuestionsSucceed.setValue(questions);
            }

            @Override
            public void onGetQuestionsOfPageFailed(String error) {
                mGetAllQuestionsFailed.setValue(error);
            }
        });
    }

    public MutableLiveData<List<Answer>> getGetLiveAnswersSucceed() {
        if (mGetLiveAnswersSucceed == null) {
            mGetLiveAnswersSucceed = new MutableLiveData<>();
            attachGetLiveAnswersListener();
        }
        return mGetLiveAnswersSucceed;
    }

    public MutableLiveData<String> getGetLiveAnswersFailed() {
        if (mGetLiveAnswersFailed == null) {
            mGetLiveAnswersFailed = new MutableLiveData<>();
            attachGetLiveAnswersListener();
        }
        return mGetLiveAnswersFailed;
    }

    public void attachGetLiveAnswersListener() {
        mRepository.setGetLiveAnswersInterface(new Repository.RepositoryGetLiveAnswersInterface() {
            @Override
            public void onGetLiveAnswersSucceed(List<Answer> answers) {
                mExpectationsQuestions = new ArrayList<>();
                mCovidQuestions = new ArrayList<>();
                mStatementQuestions = new ArrayList<>();
                mSocialQuestions = new ArrayList<>();
                mHabitsQuestions = new ArrayList<>();
                mMentalQuestions = new ArrayList<>();
                mAnxietyQuestions = new ArrayList<>();
                mAngerQuestions = new ArrayList<>();
                mDepressionQuestions = new ArrayList<>();

                for (Question question : Objects.requireNonNull(mGetAllQuestionsSucceed.getValue())) {
                    switch (question.getPage()) {
                        case Treaty:
                        case Bureaucracy:
                            addAnsweredQuestions(question, answers, mExpectationsQuestions);
                            break;
                        case CovidQuestions:
                            addAnsweredQuestions(question, answers, mCovidQuestions);
                            break;
                        case SanityCheck:
                        case Statement:
                            addAnsweredQuestions(question, answers, mStatementQuestions);
                            break;
                        case SocialQuestions:
                            addAnsweredQuestions(question, answers, mSocialQuestions);
                            break;
                        case HabitsQuestions:
                            addAnsweredQuestions(question, answers, mHabitsQuestions);
                            break;
                        case MentalQuestions:
                            addAnsweredQuestions(question, answers, mMentalQuestions);
                            break;
                        case AnxietyQuestions:
                            addAnsweredQuestions(question, answers, mAnxietyQuestions);
                            break;
                        case AngerQuestions:
                            addAnsweredQuestions(question, answers, mAngerQuestions);
                            break;
                        case DepressionQuestions:
                            addAnsweredQuestions(question, answers, mDepressionQuestions);
                            break;
                    }
                }

                mGetLiveAnswersSucceed.setValue(answers);
            }

            @Override
            public void onGetLiveAnswersFailed(String error) {
                mGetLiveAnswersFailed.setValue(error);
            }
        });
    }


    public final Appointment getCurrentAppointment() {
        return mRepository.getCurrentAppointment();
    }

    public void getAllQuestions() {
        mRepository.getAllQuestions();
    }

    public void getLiveAnswers() {
        final Appointment appointment = getCurrentAppointment();

        if (appointment != null) {
            mRepository.getLiveAnswersOfAppointment(appointment.getId());
        }
    }

    public void removeLiveAnswersListener() {
        mRepository.removeLiveAnswersListener();
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

    public List<Question> getAnxietyQuestions() {
        return mAnxietyQuestions;
    }

    public List<Question> getAngerQuestions() {
        return mAngerQuestions;
    }

    public List<Question> getDepressionQuestions() {
        return mDepressionQuestions;
    }

    private void addAnsweredQuestions(final Question question, final List<Answer> answers,
                                      final List<Question> questionsList) {
        final Answer answer;
        final int indexOfAnswer = answers.indexOf(new Answer(question.getId()));

        if (indexOfAnswer != -1) {
            answer = answers.get(indexOfAnswer);
        } else {
            answer = null;
        }

        if (answer != null) {
            if (answer instanceof AnswerBinary) {
                questionsList.add(question);
            } else if (answer instanceof AnswerOpen) {
                final String answerContent = ((AnswerOpen) answer).getAnswer();

                if (answerContent != null && !answerContent.trim().isEmpty()) {
                    questionsList.add(question);
                }
            }
        }
    }
}
