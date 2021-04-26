package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.ChatMessage;
import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;
import com.example.sheba_mental_health_project.repository.Repository;

public class AppointmentTherapistViewModel extends ViewModel {

    private final Repository mRepository;

    private MutableLiveData<AppointmentStateEnum> mUpdateAppointmentStateSucceed;
    private MutableLiveData<String> mUpdateAppointmentStateFailed;

    private MutableLiveData<ChatMessage> mGetLastChatMessageSucceed;
    private MutableLiveData<String> mGetLastChatMessageFailed;

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

    public MutableLiveData<ChatMessage> getGetLastChatMessageSucceed() {
        if (mGetLastChatMessageSucceed == null) {
            mGetLastChatMessageSucceed = new MutableLiveData<>();
            attachGetLiveAppointmentListener();
        }
        return mGetLastChatMessageSucceed;
    }

    public MutableLiveData<String> getGetLastChatMessageFailed() {
        if (mGetLastChatMessageFailed == null) {
            mGetLastChatMessageFailed = new MutableLiveData<>();
            attachGetLiveAppointmentListener();
        }
        return mGetLastChatMessageFailed;
    }

    public void attachGetLiveAppointmentListener() {
        mRepository.setGetLastChatMessageInterface(new Repository.RepositoryGetLastChatMessageInterface() {
            @Override
            public void onGetLastChatMessageSucceed(ChatMessage lastMessage) {
                mGetLastChatMessageSucceed.setValue(lastMessage);
            }

            @Override
            public void onGetLastChatMessageFailed(String error) {
                mGetLastChatMessageFailed.setValue(error);
            }
        });
    }


    public Appointment getCurrentAppointment() {
        return mRepository.getCurrentAppointment();
    }

    public void updateState(AppointmentStateEnum stateEnum) {
        mRepository.updateAppointmentState(stateEnum);
    }

    public void getLastChatMessage() {
        if (getCurrentAppointment() != null) {
            mRepository.getLastChatMessage();
        }
    }

    public void removeGetLastChatMessageListener() {
        mRepository.removeGetLastChatMessageListener();
    }
}