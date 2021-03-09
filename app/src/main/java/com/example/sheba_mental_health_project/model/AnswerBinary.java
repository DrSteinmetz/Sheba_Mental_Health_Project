package com.example.sheba_mental_health_project.model;

import androidx.annotation.Nullable;

public class AnswerBinary extends Answer {

    private boolean mAnswer = true;

    public AnswerBinary() {}

    public AnswerBinary(String id) {
        super(id);
    }

    public AnswerBinary(String id, boolean answer) {
        super(id);
        this.mAnswer = answer;
    }

    public boolean getAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        this.mAnswer = answer;
    }

    @Override
    public String toString() {
        return "AnswerBinary{" +
                "ID: " + getId() +
                "Answer: " + mAnswer +
                '}';
    }
}
