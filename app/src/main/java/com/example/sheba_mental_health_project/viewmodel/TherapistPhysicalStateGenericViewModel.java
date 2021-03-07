package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.PainPoint;
import com.example.sheba_mental_health_project.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TherapistPhysicalStateGenericViewModel extends ViewModel {

    private final Repository mRepository;

    private Appointment mAppointment;

    private final List<PainPoint> mPainPoints = new ArrayList<>();

    public TherapistPhysicalStateGenericViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
    }

    private MutableLiveData<List<PainPoint>> mGetTherapistPhysicalStatePainPointsSucceed;
    private MutableLiveData<String> mGetTherapistPhysicalStatePainPointsFailed;

    private final String TAG = "TherapistPhyStateGenVM";


    public MutableLiveData<List<PainPoint>> getTherapistPhysicalStatePainPointsSucceed() {
        if(mGetTherapistPhysicalStatePainPointsSucceed == null){
            mGetTherapistPhysicalStatePainPointsSucceed = new MutableLiveData<>();
            attachGetAllPainPointsPhysicalListener();
        }
        return mGetTherapistPhysicalStatePainPointsSucceed;
    }

    public MutableLiveData<String> getTherapistPhysicalStatePainPointsFailed() {
        if(mGetTherapistPhysicalStatePainPointsFailed == null){
            mGetTherapistPhysicalStatePainPointsFailed = new MutableLiveData<>();
            attachGetAllPainPointsPhysicalListener();
        }
        return mGetTherapistPhysicalStatePainPointsFailed;
    }

    public void attachGetAllPainPointsPhysicalListener() {
        mRepository.setGetAllPainPointsPhysicalInterface(new Repository.RepositoryGetAllPainPointsPhysicalInterface() {
            @Override
            public void onGetAllPainPointsPhysicalSucceed(Map<String, List<PainPoint>> painPointsMap) {
                mPainPoints.clear();
                for (String key : painPointsMap.keySet()) {
                    mPainPoints.addAll(painPointsMap.get(key));
                }

                mGetTherapistPhysicalStatePainPointsSucceed.setValue(mPainPoints);
            }

            @Override
            public void onGetAllPainPointsPhysicalFailed(String error) {
                mGetTherapistPhysicalStatePainPointsFailed.setValue(error);
            }
        });
    }


    public void getPainPointsPhysical(Appointment appointment) {
        mRepository.getAllPainPointsPhysical(appointment);
    }

    public Appointment getAppointment() {
        return mAppointment;
    }

    public void setAppointment(Appointment mAppointment) {
        this.mAppointment = mAppointment;
    }

    public final List<PainPoint> getPainPoints() {
        return mPainPoints;
    }

    public void removeGetAllPainPointsPhysicalListener() {
        mRepository.removeGetAllPainPointsPhysicalListener();
    }
}
