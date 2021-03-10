package com.example.sheba_mental_health_project.model;

import android.content.Context;

import androidx.annotation.Nullable;

import com.example.sheba_mental_health_project.R;
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
    private String mDescription;
    private int mColor;

    public PainPoint() {}

    public PainPoint(PainLocationEnum painLocation) {
        this.mPainLocation = painLocation;
    }

    public PainPoint(int painStrength, PainLocationEnum location,
                     PainTypeEnum painType, PainFrequencyEnum frequency) {
        this.mPainStrength = painStrength;
        this.mPainLocation = location;
        this.mPainType = painType;
        this.mFrequency = frequency;
    }

    public PainPoint(final PainPoint originalPainPoint) {
        this.mPainStrength = originalPainPoint.getPainStrength();
        this.mPainLocation = originalPainPoint.getPainLocation();
        this.mPainType = originalPainPoint.getPainType();
        this.mFrequency = originalPainPoint.getFrequency();
        this.mOtherFeeling = originalPainPoint.getOtherFeeling();
        this.mDescription = originalPainPoint.getDescription();
        this.mColor = originalPainPoint.getColor();
    }

    public int getPainStrength() {
        return mPainStrength;
    }

    public void setPainStrength(int painStrength) {
        this.mPainStrength = painStrength;
    }

    public PainLocationEnum getPainLocation() {
        return mPainLocation;
    }

    public String getPainPointLocationLocalString(final Context context) {
        final String[] painPoints = context.getResources().getStringArray(R.array.pain_points);
        return painPoints[getPainLocation().ordinal()];
    }

    public void setPainLocation(PainLocationEnum painLocation) {
        this.mPainLocation = painLocation;
    }

    public PainTypeEnum getPainType() {
        return mPainType;
    }

    public String getPainPointTypeLocalString(final Context context) {
        final String[] painTypes = context.getResources().getStringArray(R.array.pain_types);
        return (getPainType() != null ? painTypes[getPainType().ordinal()] : "");
    }

    public void setPainType(PainTypeEnum painType) {
        this.mPainType = painType;
    }

    public PainFrequencyEnum getFrequency() {
        return mFrequency;
    }

    public String getPainPointFrequencyLocalString(final Context context) {
        final String[] painFrequencies = context.getResources().getStringArray(R.array.pain_frequencies);
        return (getFrequency() != null ? painFrequencies[getFrequency().ordinal()] : "");
    }

    public void setFrequency(PainFrequencyEnum frequency) {
        this.mFrequency = frequency;
    }

    public PainOtherFeelingsEnum getOtherFeeling() {
        return mOtherFeeling;
    }

    public String getOtherFeelingLocalString(final Context context) {
        final String[] otherFeelings = context.getResources().getStringArray(R.array.other_feelings);
        return (getOtherFeeling() != null ? otherFeelings[getOtherFeeling().ordinal()] : "");
    }

    public void setOtherFeeling(PainOtherFeelingsEnum otherFeeling) {
        this.mOtherFeeling = otherFeeling;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

    @Override
    public String toString() {
        return "PainPoint{" +
                "mPainStrength=" + mPainStrength +
                ", mPainLocation=" + mPainLocation +
                ", mPainType=" + mPainType +
                ", mFrequency=" + mFrequency +
                ", mOtherFeeling=" + mOtherFeeling +
                ", mDescription='" + mDescription + '\'' +
                ", mColor=" + mColor +
                '}';
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
