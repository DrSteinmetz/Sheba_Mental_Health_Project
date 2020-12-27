package com.example.sheba_mental_health_project.model;

import androidx.annotation.Nullable;

import com.example.sheba_mental_health_project.model.enums.PainFrequencyEnum;
import com.example.sheba_mental_health_project.model.enums.PainLocationEnum;
import com.example.sheba_mental_health_project.model.enums.PainOtherFeelingsEnum;
import com.example.sheba_mental_health_project.model.enums.PainTypeEnum;

import java.io.Serializable;

public class PainPoint implements Serializable {

    private int mPainStrength;
    private PainLocationEnum mPainLocation;
    private PainTypeEnum mPainType;
    private PainFrequencyEnum mFrequency;
    private PainOtherFeelingsEnum mOtherFeeling;
    private int mColor;

    public PainPoint() {
    }

    public PainPoint(int painStrength, PainLocationEnum location,
                     PainTypeEnum painType, PainFrequencyEnum frequency) {
        this.mPainStrength = painStrength;
        this.mPainLocation = location;
        this.mPainType = painType;
        this.mFrequency = frequency;
    }

    public PainPoint(PainLocationEnum mPainLocation) {
        this.mPainLocation = mPainLocation;
    }

    public PainPoint(final PainPoint originalPainPoint) {
        this.mPainStrength = originalPainPoint.getPainStrength();
        this.mPainLocation = originalPainPoint.getPainLocation();
        this.mPainType = originalPainPoint.getPainType();
        this.mFrequency = originalPainPoint.getFrequency();
        this.mOtherFeeling = originalPainPoint.getOtherFeeling();
    }

    public int getPainStrength() {
        return mPainStrength;
    }

    public void setPainStrength(int mPainStrength) {
        this.mPainStrength = mPainStrength;
    }

    public PainLocationEnum getPainLocation() {
        return mPainLocation;
    }

    public void setPainLocation(PainLocationEnum mPainLocation) {
        this.mPainLocation = mPainLocation;
    }

    public PainTypeEnum getPainType() {
        return mPainType;
    }

    public void setPainType(PainTypeEnum mPainType) {
        this.mPainType = mPainType;
    }

    public PainFrequencyEnum getFrequency() {
        return mFrequency;
    }

    public void setFrequency(PainFrequencyEnum mFrequency) {
        this.mFrequency = mFrequency;
    }

    public PainOtherFeelingsEnum getOtherFeeling() {
        return mOtherFeeling;
    }

    public void setOtherFeeling(PainOtherFeelingsEnum mOtherFeeling) {
        this.mOtherFeeling = mOtherFeeling;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) return true;
        if (other instanceof PainPoint) {
            return this.mPainLocation.equals(((PainPoint) other).getPainLocation());
        } else {
            return false;
        }
    }
}
