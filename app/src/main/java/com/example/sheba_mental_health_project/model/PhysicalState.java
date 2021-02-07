package com.example.sheba_mental_health_project.model;

import java.io.Serializable;
import java.util.List;

public class PhysicalState implements Serializable {

    private List<PainPoint> mPainPoints;

    public PhysicalState(List<PainPoint> painPoints) {
        this.mPainPoints = painPoints;
    }

    public List<PainPoint> getPainPoints() {
        return mPainPoints;
    }

    public void setPainPoints(List<PainPoint> mPainPoints) {
        this.mPainPoints = mPainPoints;
    }
}
