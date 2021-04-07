package com.example.sheba_mental_health_project.model;

import androidx.annotation.Nullable;

import com.example.sheba_mental_health_project.model.enums.QuestionTypeEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;

import java.io.Serializable;

public class Question implements Serializable {

    private String mId;
    private String mQuestion;
    private QuestionTypeEnum mQuestionType;
    private boolean mIsMandatory = false;
    private ViewModelEnum mPage;

    public Question() {}

    public Question(String id) {
        this.mId = id;
    }

    public Question(final String id, final String question, final QuestionTypeEnum questionType,
                    final boolean isMandatory, final ViewModelEnum page) {
        this.mId = id;
        this.mQuestion = question;
        this.mQuestionType = questionType;
        this.mIsMandatory = isMandatory;
        this.mPage = page;
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

    public boolean isMandatory() {
        return mIsMandatory;
    }

    public void setMandatory(boolean isMandatory) {
        this.mIsMandatory = isMandatory;
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
