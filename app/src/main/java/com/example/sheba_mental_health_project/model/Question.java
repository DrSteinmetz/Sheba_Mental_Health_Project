package com.example.sheba_mental_health_project.model;

import java.io.Serializable;

public class Question implements Serializable {

    private String mId;
    private String mQuestion;
    private String mPage;

    public Question() {}

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

    public String getPage() {
        return mPage;
    }

    public void setPage(String page) {
        this.mPage = page;
    }
}
