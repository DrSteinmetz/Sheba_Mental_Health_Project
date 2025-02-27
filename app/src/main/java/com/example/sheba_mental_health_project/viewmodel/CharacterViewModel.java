package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.PainPoint;
import com.example.sheba_mental_health_project.model.enums.PainLocationEnum;
import com.example.sheba_mental_health_project.repository.Repository;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CharacterViewModel extends ViewModel {

    private final Repository mRepository;

    private Appointment mAppointment;

    private MutableLiveData<Map<String, List<PainPoint>>> mGetAllPaintPointsSucceed;
    private MutableLiveData<String> mGetAllPaintPointsFailed;

    private final EnumMap<PainLocationEnum, PainPoint> mPainPointsMap = new EnumMap<>(PainLocationEnum.class);

    private MutableLiveData<List<PainPoint>> mNewPaintPoints;

    private final String TAG = "CharacterViewModel";


    public CharacterViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
    }

    public MutableLiveData<Map<String, List<PainPoint>>> getGetAllPaintPointsSucceed() {
        if (mGetAllPaintPointsSucceed == null) {
            mGetAllPaintPointsSucceed = new MutableLiveData<>();
            attachGetAllPainPointsListener();
        }
        return mGetAllPaintPointsSucceed;
    }

    public MutableLiveData<String> getGetAllPaintPointsFailed() {
        if (mGetAllPaintPointsFailed == null) {
            mGetAllPaintPointsFailed = new MutableLiveData<>();
            attachGetAllPainPointsListener();
        }
        return mGetAllPaintPointsFailed;
    }

    public void attachGetAllPainPointsListener() {
        mRepository.setGetAllPainPointsInterface(new Repository.RepositoryGetAllPainPointsInterface() {
            @Override
            public void onGetAllPainPointsSucceed(Map<String, List<PainPoint>> painPointsMap) {
                if (!mPainPointsMap.isEmpty()) {
                    updateNewPainPointsList(painPointsMap);
                }

                mPainPointsMap.clear();
                for (String key : painPointsMap.keySet()) {
                    for (PainPoint painPoint : painPointsMap.get(key)) {
                        mPainPointsMap.put(painPoint.getPainLocation(), painPoint);
                    }
                }

                mGetAllPaintPointsSucceed.setValue(painPointsMap);
            }

            @Override
            public void onGetAllPainPointsFailed(String error) {
                mGetAllPaintPointsFailed.setValue(error);
            }
        });
    }


    public MutableLiveData<List<PainPoint>> getNewPaintPoints() {
        if (mNewPaintPoints == null) {
            mNewPaintPoints = new MutableLiveData<>();
        }
        return mNewPaintPoints;
    }

    private void updateNewPainPointsList(final Map<String, List<PainPoint>> painPointsMap) {
        final List<PainPoint> oldPainPointList = new ArrayList<>(mPainPointsMap.values());
        final List<PainPoint> newPainPointList = new ArrayList<>();
        mNewPaintPoints = getNewPaintPoints();

        if (!painPointsMap.isEmpty()) {
            for (List<PainPoint> list : painPointsMap.values()) {
                newPainPointList.addAll(list);
            }
        }

        newPainPointList.removeAll(oldPainPointList);
        mNewPaintPoints.setValue(newPainPointList);
    }

    public Appointment getAppointment() {
        return mAppointment;
    }

    public void setAppointment(Appointment mAppointment) {
        this.mAppointment = mAppointment;
    }

    public EnumMap<PainLocationEnum, PainPoint> getPainPointsMap() {
        return mPainPointsMap;
    }

    public void getAllPainPoints() {
        mRepository.getAllPainPoints(mAppointment);
    }

    public void removeGetAllPainPointsListener() {
        mRepository.removeGetAllPainPointsListener();
    }
}
