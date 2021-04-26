package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.ChatMessage;
import com.example.sheba_mental_health_project.repository.AuthRepository;
import com.example.sheba_mental_health_project.repository.Repository;
import com.google.firebase.firestore.Query;

public class ChatViewModel extends ViewModel {

    private final Repository mRepository;

    private final AuthRepository mAuthRepository;

    private MutableLiveData<String> mUploadMessageFailed;

    private final String TAG = "ChatViewModel";


    public ChatViewModel(final Context context) {
        this.mRepository = Repository.getInstance(context);
        this.mAuthRepository = AuthRepository.getInstance(context);
    }

    public MutableLiveData<String> getUploadMessageFailed() {
        if (mUploadMessageFailed == null) {
            mUploadMessageFailed = new MutableLiveData<>();
            attachSetUploadChatMessageToCloudListener();
        }
        return mUploadMessageFailed;
    }

    private void attachSetUploadChatMessageToCloudListener() {
        mRepository.setUploadChatMessageInterface(new Repository.RepositoryUploadChatMessageInterface() {
            @Override
            public void onUploadChatMessageFailed(String error) {
                mUploadMessageFailed.setValue(error);
            }
        });
    }


    public Query getChatQuery() {
        return mRepository.getChatQuery();
    }

    public String getUserEmail() {
        return mAuthRepository.getUser().getEmail();
    }

    public String getRecipientFullName() {
        String recipientName = mAuthRepository.getUser().getFullName();
        final String patientName = mRepository.getCurrentAppointment().getPatient().getFullName();
        final String therapistName = mRepository.getCurrentAppointment().getTherapist().getFullName();

        if (recipientName.equals(patientName)) {
            recipientName = therapistName;
        } else {
            recipientName = patientName;
        }

        return recipientName;
    }

    public String getRecipientEmail() {
        String recipientEmail = mAuthRepository.getUser().getEmail();
        final String patientEmail = mRepository.getCurrentAppointment().getPatient().getEmail();
        final String therapistEmail = mRepository.getCurrentAppointment().getTherapist().getEmail();

        if (recipientEmail.equals(patientEmail)) {
            recipientEmail = therapistEmail;
        } else {
            recipientEmail = patientEmail;
        }

        return recipientEmail;
    }

    public void uploadMessageToCloud(final String messageContent) {
        final ChatMessage message = new ChatMessage(messageContent, getRecipientEmail());
        mRepository.uploadChatMessageToCloud(message);
    }

    public void updateLastChatMessage() {
        mRepository.updateLastChatMessage();
    }
}
