package com.example.sheba_mental_health_project.model;

public class AnswerOpen extends Answer {

    private String mAnswer;

    public AnswerOpen() {}

    public AnswerOpen(String mId) {
        super(mId);
    }

    public AnswerOpen(String mId, String mAnswer) {
        super(mId);
        this.mAnswer = mAnswer;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String mAnswer) {
        this.mAnswer = mAnswer;
    }
}
