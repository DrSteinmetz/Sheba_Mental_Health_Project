package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.enums.RecommendationsStateEnum;
import com.example.sheba_mental_health_project.repository.Repository;

public class RecommendationQuestionsViewModel extends ViewModel {

    private final Repository mRepository;

    private MutableLiveData<RecommendationsStateEnum> updateRecommendationsSucceed;
    private MutableLiveData<String> updateRecommendationsFailed;
    private RecommendationsStateEnum mRecommendationsStateEnum;

    private boolean isRecommendationsStateChanged = false;

    private final String TAG = "RecommendationQuestionsViewModel";


    public RecommendationQuestionsViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
    }

    public MutableLiveData<RecommendationsStateEnum> getUpdateRecommendationsSucceed() {
        if(updateRecommendationsSucceed == null){
            updateRecommendationsSucceed = new MutableLiveData<>();
            attachUpdateRecommendations();
        }
        return updateRecommendationsSucceed;
    }



    public MutableLiveData<String> getUpdateRecommendationsFailed() {
        if(updateRecommendationsFailed == null){
            updateRecommendationsFailed = new MutableLiveData<>();
            attachUpdateRecommendations();
        }
        return updateRecommendationsFailed;
    }

    private void attachUpdateRecommendations() {
        mRepository.setUpdateRecommendationsStateOfAppointmentInterface(new Repository.RepositoryUpdateRecommendationsStateOfAppointmentInterface() {
            @Override
            public void onUpdateStateRecommendationsOfAppointmentSucceed(RecommendationsStateEnum recommendationsStateEnum) {
                updateRecommendationsSucceed.setValue(recommendationsStateEnum);
            }

            @Override
            public void onUpdateStateRecommendationsOfAppointmentFailed(String error) {
                updateRecommendationsFailed.setValue(error);
            }
        });
    }

    public void updateRecommendationsState(RecommendationsStateEnum recommendationsState,String recommendationsDetails){
        mRepository.updateRecommendationsState(recommendationsState,recommendationsDetails);
    }

    public void setRecommendationsState(RecommendationsStateEnum recommendationsStateEnum) {
        this.mRecommendationsStateEnum = recommendationsStateEnum;
    }

    public RecommendationsStateEnum getRecommendationsStateEnum() {
        return mRecommendationsStateEnum;
    }

    public Appointment getCurrentAppointment(){
        return  mRepository.getCurrentAppointment();
    }

    public boolean isRecommendationsStateChanged() {
        return isRecommendationsStateChanged;
    }

    public void setRecommendationsStateChanged(boolean recommendationsStateChanged) {
        isRecommendationsStateChanged = recommendationsStateChanged;
    }
}