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

public class TherapistPhysicalStateViewModel extends ViewModel {

    private Repository mRepository;
    private final List<PainPoint> mPainPoints = new ArrayList<>();

    public TherapistPhysicalStateViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
    }

    private MutableLiveData<List<PainPoint>> mGetTherapistPhysicalStatePainPointsSucceed;
    private MutableLiveData<String> mGetTherapistPhysicalStatePainPointsFailed;

    private final String TAG = "TherapistPhysicalState";

    public MutableLiveData<List<PainPoint>> getTherapistPhysicalStatePainPointsSucceed() {
        if(mGetTherapistPhysicalStatePainPointsSucceed == null){
            mGetTherapistPhysicalStatePainPointsSucceed = new MutableLiveData<>();
            attachGetMyAppointmentsListener();
        }
        return mGetTherapistPhysicalStatePainPointsSucceed;
    }

    public MutableLiveData<String> getTherapistPhysicalStatePainPointsFailed() {
        if(mGetTherapistPhysicalStatePainPointsFailed == null){
            mGetTherapistPhysicalStatePainPointsFailed = new MutableLiveData<>();
            attachGetMyAppointmentsListener();
        }
        return mGetTherapistPhysicalStatePainPointsFailed;
    }

    public void attachGetMyAppointmentsListener() {
        mRepository.setGetAllPainPointsInterface(new Repository.RepositoryGetAllPainPointsInterface() {
            @Override
            public void onGetAllPainPointsSucceed(Map<String, List<PainPoint>> painPointsMap) {
                mPainPoints.clear();
                for (String key : painPointsMap.keySet()) {
                    for (PainPoint painPoint : painPointsMap.get(key)) {
                        mPainPoints.add(painPoint);
                    }
                }

                mGetTherapistPhysicalStatePainPointsSucceed.setValue(mPainPoints);
            }

            @Override
            public void onGetAllPainPointsFailed(String error) {
                mGetTherapistPhysicalStatePainPointsFailed.setValue(error);
            }
        });
    }

    public void getPainPoints(Appointment appointment) {
        mRepository.getAllPainPoints(appointment);
    }

    public final List<PainPoint> getPainPoints() {
        return mPainPoints;
    }

    public Appointment getCurrentAppointment() {
        return mRepository.getCurrentAppointment();
    }

    public void removeGetAllPainPointsListener() {
        mRepository.removeGetAllPainPointsListener();
    }
}
