package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.PainPoint;
import com.example.sheba_mental_health_project.model.enums.PainLocationEnum;
import com.example.sheba_mental_health_project.repository.Repository;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CharacterViewModel extends ViewModel {

    private final Repository mRepository;

    private Appointment mAppointment;

    private MutableLiveData<Map<String, List<PainPoint>>> mGetAllPaintPointsSucceed;
    private MutableLiveData<String> mGetAllPaintPointsFailed;

    private final EnumMap<PainLocationEnum, PainPoint> mPainPointsMap = new EnumMap<>(PainLocationEnum.class);

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

    private void attachGetAllPainPointsListener() {
        mRepository.setGetAllPainPointsInterface(new Repository.RepositoryGetAllPainPointsInterface() {
            @Override
            public void onGetAllPainPointsSucceed(Map<String, List<PainPoint>> painPointsMap) {
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
