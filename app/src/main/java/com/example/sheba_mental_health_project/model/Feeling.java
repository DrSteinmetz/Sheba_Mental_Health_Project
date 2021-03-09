package com.example.sheba_mental_health_project.model;

import java.io.Serializable;

public class Feeling implements Serializable {

    private String mId;
    private String mName;
    private int mImageId;

    public Feeling() {}

    public Feeling(String id, int imageId, String name) {
        this.mId = id;
        this.mImageId = imageId;
        this.mName = name;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        this.mImageId = imageId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }
}
