package com.example.sheba_mental_health_project.model;

import com.example.sheba_mental_health_project.model.enums.PainFrequencyEnum;
import com.example.sheba_mental_health_project.model.enums.PainLocationEnum;
import com.example.sheba_mental_health_project.model.enums.PainTypeEnum;

import java.io.Serializable;

public class PainPoint implements Serializable {

    private float mPainStrength;
    private PainLocationEnum mPainLocation;
    private PainTypeEnum[] mPainType;
    private PainFrequencyEnum mFrequency;

    public PainPoint(float painStrength, PainLocationEnum location,
                     PainTypeEnum[] painType, PainFrequencyEnum frequency) {
        this.mPainStrength = painStrength;
        this.mPainLocation = location;
        this.mPainType = painType;
        this.mFrequency = frequency;
    }

    public float getPainStrength() {
        return mPainStrength;
    }

    public void setPainStrength(float mPainStrength) {
        this.mPainStrength = mPainStrength;
    }

    public PainLocationEnum getPainLocation() {
        return mPainLocation;
    }

    public void setPainLocation(PainLocationEnum mPainLocation) {
        this.mPainLocation = mPainLocation;
    }

    public PainTypeEnum[] getPainType() {
        return mPainType;
    }

    public void setPainType(PainTypeEnum[] mPainType) {
        this.mPainType = mPainType;
    }

    public PainFrequencyEnum getFrequency() {
        return mFrequency;
    }

    public void setFrequency(PainFrequencyEnum mFrequency) {
        this.mFrequency = mFrequency;
    }
}
