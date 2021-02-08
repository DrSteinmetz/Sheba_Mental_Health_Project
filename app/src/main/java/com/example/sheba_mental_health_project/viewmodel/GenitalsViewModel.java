package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.PainPoint;
import com.example.sheba_mental_health_project.model.enums.BodyPartEnum;
import com.example.sheba_mental_health_project.model.enums.PainLocationEnum;
import com.example.sheba_mental_health_project.repository.Repository;

import java.util.EnumMap;
import java.util.List;

public class GenitalsViewModel extends ViewModel {

    private final String TAG = "GenitalsViewModel";

    private final Repository mRepository;

    private PainPoint mPainPoint = new PainPoint();
    private EnumMap<PainLocationEnum, PainPoint> mPainPointsMap = new EnumMap<>(PainLocationEnum.class);

    private MutableLiveData<PainPoint> mSetPainPointsSucceed;
    private MutableLiveData<String> mSetPainPointsFailed;

    public GenitalsViewModel(final Context context) {
        mRepository = Repository.getInstance(context);

        final List<PainPoint> painPoints = mRepository.getSpecificPainPointsList(BodyPartEnum.Genitals);
        for (PainPoint painPoint : painPoints) {
            mPainPointsMap.put(painPoint.getPainLocation(), painPoint);
        }
    }

    public MutableLiveData<PainPoint> getSetPainPointsSucceed() {
        if (mSetPainPointsSucceed == null) {
            mSetPainPointsSucceed = new MutableLiveData<>();
            attachSetPainPointsListener();
        }
        return mSetPainPointsSucceed;
    }

    public MutableLiveData<String> getSetPainPointsFailed() {
        if (mSetPainPointsFailed == null) {
            mSetPainPointsFailed = new MutableLiveData<>();
            attachSetPainPointsListener();
        }
        return mSetPainPointsFailed;
    }

    private void attachSetPainPointsListener() {
        mRepository.setSetPainPointsInterface(new Repository.RepositorySetPainPointsInterface() {
            @Override
            public void onSetPainPointsSucceed(PainPoint painPoint) {
                mSetPainPointsSucceed.setValue(painPoint);
            }

            @Override
            public void onSetPainPointsFailed(String error) {
                mSetPainPointsFailed.setValue(error);
            }
        });
    }


    public PainPoint getPainPoint() {
        return mPainPoint;
    }

    public void setPainPoint(PainPoint mPainPoint) {
        this.mPainPoint = mPainPoint;
    }

    public EnumMap<PainLocationEnum, PainPoint> getPainPointsMap() {
        return mPainPointsMap;
    }

    public void setPainPointsMap(EnumMap<PainLocationEnum, PainPoint> mPainPointsMap) {
        this.mPainPointsMap = mPainPointsMap;
    }

    public void setPainPointsInDB() {
        mRepository.setPainPoints(BodyPartEnum.Genitals, mPainPoint);
    }
}
