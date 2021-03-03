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

    private final Repository mRepository;

    private PainPoint mPainPoint = new PainPoint();
    private EnumMap<PainLocationEnum, PainPoint> mPainPointsMap = new EnumMap<>(PainLocationEnum.class);
    private EnumMap<PainLocationEnum, PainPoint> mPainPointsGenitalsMap = new EnumMap<>(PainLocationEnum.class);

    private MutableLiveData<PainPoint> mSetPainPointsSucceed;
    private MutableLiveData<String> mSetPainPointsFailed;

    private MutableLiveData<PainPoint> mDeletePainPointSucceed;
    private MutableLiveData<String> mDeletePainPointFailed;

    private final String TAG = "GenitalsViewModel";


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

    public MutableLiveData<PainPoint> getDeletePainPointSucceed() {
        if (mDeletePainPointSucceed == null) {
            mDeletePainPointSucceed = new MutableLiveData<>();
            attachDeletePainPointListener();
        }
        return mDeletePainPointSucceed;
    }

    public MutableLiveData<String> getDeletePainPointFailed() {
        if (mDeletePainPointFailed == null) {
            mDeletePainPointFailed = new MutableLiveData<>();
            attachDeletePainPointListener();
        }
        return mDeletePainPointFailed;
    }

    private void attachDeletePainPointListener() {
        mRepository.setDeletePainPointInterface(new Repository.RepositoryDeletePainPointInterface() {
            @Override
            public void onDeletePainPointSucceed(PainPoint painPoint) {
                mPainPointsMap.remove(painPoint.getPainLocation());

                mPainPointsGenitalsMap.remove(painPoint.getPainLocation());
                if (mPainPointsGenitalsMap.isEmpty() &&
                        mPainPointsMap.containsKey(PainLocationEnum.PrivatePart)) {
                    mPainPoint.setPainLocation(PainLocationEnum.PrivatePart);
                    deletePainPoint();
                }

                mDeletePainPointSucceed.setValue(painPoint);
            }

            @Override
            public void onDeletePainPointFailed(String error) {
                mDeletePainPointFailed.setValue(error);
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

    public EnumMap<PainLocationEnum, PainPoint> getPainPointsGenitalsMap() {
        return mPainPointsGenitalsMap;
    }

    public void setPainPointsGenitalsMap(EnumMap<PainLocationEnum, PainPoint> mPainPointsGenitalsMap) {
        this.mPainPointsGenitalsMap = mPainPointsGenitalsMap;
    }

    public void setGenitalsPainPointMap() {
        if (mPainPointsMap.containsKey(PainLocationEnum.Vagina)) {
            mPainPointsGenitalsMap.put(PainLocationEnum.Vagina, mPainPointsMap.get(PainLocationEnum.Vagina));
        }

        if (mPainPointsMap.containsKey(PainLocationEnum.Penis)) {
            mPainPointsGenitalsMap.put(PainLocationEnum.Penis, mPainPointsMap.get(PainLocationEnum.Penis));
        }

        if (mPainPointsMap.containsKey(PainLocationEnum.Testicles)) {
            mPainPointsGenitalsMap.put(PainLocationEnum.Testicles, mPainPointsMap.get(PainLocationEnum.Testicles));
        }
    }

    public void setPainPointsInDB() {
        mRepository.setPainPoints(BodyPartEnum.Genitals, mPainPoint);
    }

    public void deletePainPoint() {
        mRepository.deletePainPoint(BodyPartEnum.Genitals, mPainPoint);
    }
}
