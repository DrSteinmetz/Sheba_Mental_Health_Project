package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.repository.Repository;
import com.example.sheba_mental_health_project.repository.StorageRepository;

public class DocumentsViewModel extends ViewModel {

    private Repository mRepository;

    private StorageRepository mStorageRepository;

    private final String TAG = "DocumentsViewModel";


    public DocumentsViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
        mStorageRepository = StorageRepository.getInstance(context);
    }


    public void getDocuments() {
    }
}
