package com.example.sheba_mental_health_project.model;

public class AnswerBinary extends Answer {

    private boolean mAnswer = true;
    private String mAnswerDetails = "";

    public AnswerBinary() {}

    public AnswerBinary(String id) {
        super(id);
    }

    public AnswerBinary(String id, boolean answer) {
        super(id);
        this.mAnswer = answer;
    }

    public AnswerBinary(String id, boolean answer, String answerDetails) {
        super(id);
        this.mAnswer = answer;
        this.mAnswerDetails = answerDetails;
    }

    public boolean getAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        this.mAnswer = answer;
    }

    public String getAnswerDetails() {
        return mAnswerDetails;
    }

    public void setAnswerDetails(String answerDetails) {
        this.mAnswerDetails = answerDetails;
    }

    @Override
    public String toString() {
        return "AnswerBinary{" +
                "ID: " + getId() +
                "Answer: " + mAnswer +
                "Details: \"" + mAnswerDetails + '\"' +
                '}';
    }
}
