package com.example.sheba_mental_health_project.model;

import androidx.annotation.Nullable;

import com.example.sheba_mental_health_project.model.enums.QuestionTypeEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;

import java.io.Serializable;

public class Question implements Serializable {

    private String mId;
    private String mQuestion;
    private QuestionTypeEnum mQuestionType;
    private ViewModelEnum mPage;

    public Question() {}

    public Question(String mId) {
        this.mId = mId;
    }

    public Question(final String mId, final String mQuestion,
                    final QuestionTypeEnum mQuestionType, final ViewModelEnum mPage) {
        this.mId = mId;
        this.mQuestion = mQuestion;
        this.mQuestionType = mQuestionType;
        this.mPage = mPage;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        this.mQuestion = question;
    }

    public QuestionTypeEnum getQuestionType() {
        return mQuestionType;
    }

    public void setQuestionType(QuestionTypeEnum questionType) {
        this.mQuestionType = questionType;
    }

    public ViewModelEnum getPage() {
        return mPage;
    }

    public void setPage(ViewModelEnum page) {
        this.mPage = page;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Question) {
            final Question otherQuestion = (Question) obj;
            return otherQuestion.getId().equals(this.mId);
        }
        return false;
    }
}
