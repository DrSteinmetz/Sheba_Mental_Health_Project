package com.example.sheba_mental_health_project.model;

public class AnswerBinary extends Answer {

    private boolean mAnswer = true;

    public AnswerBinary() {}

    public AnswerBinary(String mId) {
        super(mId);
    }

    public AnswerBinary(String mId, boolean mAnswer) {
        super(mId);
        this.mAnswer = mAnswer;
    }

    public boolean getAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean mAnswer) {
        this.mAnswer = mAnswer;
    }
}
