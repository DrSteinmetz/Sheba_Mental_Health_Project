package com.example.sheba_mental_health_project.model;

public class AnswerOpen extends Answer {

    private String mAnswer;

    public AnswerOpen() {}

    public AnswerOpen(String id) {
        super(id);
    }

    public AnswerOpen(String id, String answer) {
        super(id);
        this.mAnswer = answer;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        this.mAnswer = answer;
    }

    @Override
    public String toString() {
        return "AnswerOpen{" +
                "ID: " + getId() +
                "Answer: '" + mAnswer + '\'' +
                '}';
    }
}
