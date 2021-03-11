package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;
import com.example.sheba_mental_health_project.repository.Repository;

public class AppointmentTherapistViewModel extends ViewModel {

    private final Repository mRepository;

    private MutableLiveData<AppointmentStateEnum> mUpdateAppointmentStateSucceed;
    private MutableLiveData<String> mUpdateAppointmentStateFailed;

    private final String TAG = "AppointmentTherapistVM";


    public AppointmentTherapistViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
    }

    public MutableLiveData<AppointmentStateEnum> getUpdateAppointmentStateSucceed() {
        if (mUpdateAppointmentStateSucceed == null) {
            mUpdateAppointmentStateSucceed = new MutableLiveData<>();
            attachSetUpdateStateOfAppointmentListener();
        }
        return mUpdateAppointmentStateSucceed;
    }

    public MutableLiveData<String> getUpdateAppointmentStateFailed() {
        if (mUpdateAppointmentStateFailed == null) {
            mUpdateAppointmentStateFailed = new MutableLiveData<>();
            attachSetUpdateStateOfAppointmentListener();
        }
        return mUpdateAppointmentStateFailed;
    }

    public void attachSetUpdateStateOfAppointmentListener() {
        mRepository.setUpdateStateOfAppointmentInterface(new Repository.RepositoryUpdateStateOfAppointmentInterface() {
            @Override
            public void onUpdateStateOfAppointmentSucceed(AppointmentStateEnum appointmentState) {
                mUpdateAppointmentStateSucceed.setValue(appointmentState);
            }

            @Override
            public void onUpdateAnswersOfAppointmentFailed(String error) {
                mUpdateAppointmentStateFailed.setValue(error);
            }
        });
    }


    public Appointment getCurrentAppointment() {
        return mRepository.getCurrentAppointment();
    }

    public void updateState(AppointmentStateEnum stateEnum) {
        mRepository.updateAppointmentState(stateEnum);
    }
}