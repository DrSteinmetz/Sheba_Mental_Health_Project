package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.ChatMessage;
import com.example.sheba_mental_health_project.repository.Repository;

public class AppointmentPatientViewModel extends ViewModel {

    private final Repository mRepository;

    private MutableLiveData<ChatMessage> mGetLastChatMessageSucceed;
    private MutableLiveData<String> mGetLastChatMessageFailed;

    private final String TAG = "AppointmentPatientVM";


    public AppointmentPatientViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
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

    public void getLastChatMessage() {
        if (getCurrentAppointment() != null) {
            mRepository.getLastChatMessage();
        }
    }

    public void removeGetLastChatMessageListener() {
        mRepository.removeGetLastChatMessageListener();
    }
}
