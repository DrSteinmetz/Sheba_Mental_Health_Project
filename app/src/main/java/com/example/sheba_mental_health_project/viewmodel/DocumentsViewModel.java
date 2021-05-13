package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.repository.Repository;
import com.example.sheba_mental_health_project.repository.StorageRepository;

import java.util.List;

public class DocumentsViewModel extends ViewModel {

    private Repository mRepository;

    private StorageRepository mStorageRepository;

    private Appointment mSelectedAppointment;

    private MutableLiveData<Uri> mUploadDocumentSucceed;
    private MutableLiveData<String> mUploadDocumentFailed;

    private MutableLiveData<String> mUpdateDocumentsSucceed;
    private MutableLiveData<String> mUpdateDocumentsFailed;

    private MutableLiveData<List<String>> mGetDocumentsSucceed;
    private MutableLiveData<String> mGetDocumentsFailed;

    private final String TAG = "DocumentsViewModel";
    private boolean isTherapist;


    public DocumentsViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
        mStorageRepository = StorageRepository.getInstance(context);
    }

    public MutableLiveData<Uri> getUploadDocumentSucceed() {
        if(mUploadDocumentSucceed == null){
            mUploadDocumentSucceed = new MutableLiveData<>();
            attachUploadPictureDocument();
        }
        return mUploadDocumentSucceed;
    }

    public MutableLiveData<String> getUploadDocumentFailed() {
        if(mUploadDocumentFailed == null){
            mUploadDocumentFailed = new MutableLiveData<>();
            attachUploadPictureDocument();
        }
        return mUploadDocumentFailed;
    }

    public MutableLiveData<String> getUpdateDocumentSucceed() {
        if(mUpdateDocumentsSucceed == null){
            mUpdateDocumentsSucceed = new MutableLiveData<>();
            attachUpdatePictureDocuments();
        }
        return mUpdateDocumentsSucceed;
    }



    public MutableLiveData<String> getUpdateDocumentFailed() {
        if(mUpdateDocumentsFailed == null){
            mUpdateDocumentsFailed = new MutableLiveData<>();
            attachUpdatePictureDocuments();
        }
        return mUpdateDocumentsFailed;
    }

    public MutableLiveData<List<String>> getGetDocumentSucceed() {
        if(mGetDocumentsSucceed == null){
            mGetDocumentsSucceed = new MutableLiveData<>();
            attachGetDocuments();
        }
        return mGetDocumentsSucceed;
    }



    public MutableLiveData<String> getGetDocumentFailed() {
        if(mGetDocumentsFailed == null){
            mGetDocumentsFailed = new MutableLiveData<>();
            attachGetDocuments();
        }
        return mGetDocumentsFailed;
    }

    private void attachGetDocuments() {
        mRepository.setGetDocumentsOfAppointmentInterface(new Repository.RepositoryGetDocumentsOfAppointmentInterface() {
            @Override
            public void onGetDocumentsOfAppointmentSucceed(List<String> appointmentDocumentsUriList) {
                mGetDocumentsSucceed.setValue(appointmentDocumentsUriList);
            }

            @Override
            public void onGetDocumentsOfAppointmentFailed(String error) {
                mGetDocumentsFailed.setValue(error);
            }
        });
    }

    private void attachUpdatePictureDocuments() {
        mRepository.setUpdateDocumentsOfAppointmentInterface(new Repository.RepositoryUpdateDocumentsOfAppointmentInterface() {
            @Override
            public void onUpdateDocumentsOfAppointmentSucceed(String appointmentDocumentUri) {
                mUpdateDocumentsSucceed.setValue(appointmentDocumentUri);
            }

            @Override
            public void onUpdateDocumentsOfAppointmentFailed(String error) {
                mUpdateDocumentsFailed.setValue(error);
            }
        });
    }



    private void attachUploadPictureDocument() {
        mStorageRepository.setUploadDocumentInterface(new StorageRepository.RepositoryUploadDocumentInterface() {
            @Override
            public void onUploadDocumentSucceed(Uri uri) {
                mUploadDocumentSucceed.setValue(uri);
            }

            @Override
            public void onUploadDocumentFailed(String error) {
                mUploadDocumentFailed.setValue(error);
            }
        });
    }

    public void uploadPicture(Uri uri) {
        mStorageRepository.uploadDocument(uri,mSelectedAppointment.getId());
    }

    public void updateDocumentOfAppointment(String documentUri,final boolean isToRemove) {
        mRepository.updateAppointmentDocuments(documentUri,isToRemove);
    }

    public void setIsTherapist(boolean isTherapist) {
        this.isTherapist = isTherapist;
    }

    public boolean isTherapist() {
        return isTherapist;
    }

    public void readDocuments() {
        mRepository.readAppointmentDocuments(mSelectedAppointment);
    }

    public void removeDocumentsListener() {
        mRepository.removeGetDocumentsAppointmentsListener();
    }

    public void deleteDocumentFromStorage(String uri) {
        mStorageRepository.deleteDocument(uri);
    }

    public Appointment getSelectedAppointment() {
        return mSelectedAppointment;
    }

    public void setSelectedAppointment(Appointment mSelectedAppointment) {
        this.mSelectedAppointment = mSelectedAppointment;
    }
}
