package com.example.sheba_mental_health_project.model;

import java.io.Serializable;

public class Feeling implements Serializable {

    private String mId;
    private String mName;
    private int mImageId;

    public Feeling() {}

    public Feeling(String mId, int mImageId, String mName) {
        this.mId = mId;
        this.mImageId = mImageId;
        this.mName = mName;
    }

    public String getId() {
        return mId;
    }

    public void setId(String Id) {
        this.mId = Id;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int ImageId) {
        this.mImageId = ImageId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String Name) {
        this.mName = Name;
    }
}
