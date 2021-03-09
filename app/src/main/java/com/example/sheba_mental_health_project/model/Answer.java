package com.example.sheba_mental_health_project.model;

import android.util.Log;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Answer implements Serializable {

    private String mId;

    public Answer() {}

    public Answer(String id) {
        this.mId = id;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Log.d("Answer Object", "oron equals: " + obj.getClass());
        if (obj instanceof Answer) {
            final Answer otherAnswer = (Answer) obj;
            Log.d("Answer Object", "oron equals: other.id: " + otherAnswer.getId() + " this.id: " + this.mId);
            return otherAnswer.getId().equals(this.mId);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "mId='" + mId + '\'' +
                '}';
    }
}
