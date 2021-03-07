package com.example.sheba_mental_health_project.model;

import com.example.sheba_mental_health_project.model.enums.FacialExpressionEnum;

import java.io.Serializable;

public class MentalState implements Serializable {

    private float mHappiness;
    private float mSadness;
    private FacialExpressionEnum mFacialExpression;

    public MentalState() {}

    public float getHappiness() {
        return mHappiness;
    }

    public void setHappiness(float mHappiness) {
        this.mHappiness = mHappiness;
    }

    public float getSadness() {
        return mSadness;
    }

    public void setSadness(float mSadness) {
        this.mSadness = mSadness;
    }

    public FacialExpressionEnum getFacialExpression() {
        return mFacialExpression;
    }

    public void setFacialExpression(FacialExpressionEnum mFacialExpression) {
        this.mFacialExpression = mFacialExpression;
    }
}
