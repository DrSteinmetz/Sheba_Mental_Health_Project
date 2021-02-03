package com.example.sheba_mental_health_project.model;

import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;

import java.io.Serializable;

public class Question implements Serializable {

    private String mId;
    private String mQuestion;
    private ViewModelEnum mPage;

    public Question() {}

    public Question(final String mId, final String mQuestion, final ViewModelEnum mPage) {
        this.mId = mId;
        this.mQuestion = mQuestion;
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

    public ViewModelEnum getPage() {
        return mPage;
    }

    public void setPage(ViewModelEnum page) {
        this.mPage = page;
    }
}
