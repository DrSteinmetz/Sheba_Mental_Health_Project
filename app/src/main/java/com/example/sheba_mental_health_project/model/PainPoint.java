package com.example.sheba_mental_health_project.model;

import java.io.Serializable;

public class PainPoint implements Serializable {

    private float mPainStrength;
    private int mPainLocation; // Enum
    private int[] mPainType; // Enum
    private int mFrequency; // Enum

    public PainPoint(float mPainStrength, int mLocation, int[] mPainType, int mFrequency) {
        this.mPainStrength = mPainStrength;
        this.mPainLocation = mLocation;
        this.mPainType = mPainType;
        this.mFrequency = mFrequency;
    }

    public float getPainStrength() {
        return mPainStrength;
    }

    public void setPainStrength(float painStrength) {
        this.mPainStrength = painStrength;
    }

    public int getPainLocation() {
        return mPainLocation;
    }

    public void setPainLocation(int painLocation) {
        this.mPainLocation = painLocation;
    }

    public int[] getPainType() {
        return mPainType;
    }

    public void setPainType(int[] painType) {
        this.mPainType = painType;
    }

    public int getFrequency() {
        return mFrequency;
    }

    public void setFrequency(int frequency) {
        this.mFrequency = frequency;
    }
}
