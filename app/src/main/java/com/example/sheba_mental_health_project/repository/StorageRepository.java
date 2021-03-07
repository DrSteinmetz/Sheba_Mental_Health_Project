package com.example.sheba_mental_health_project.repository;

import android.content.Context;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class StorageRepository {

    private static StorageRepository storageRepository;

    private final StorageReference mStorage;

    private final Context mContext;

    private final String TAG = "StorageRepository";

    /**<------ Singleton ------>*/
    public static StorageRepository getInstance(final Context context) {
        if (storageRepository == null) {
            storageRepository = new StorageRepository(context);
        }
        return storageRepository;
    }

    private StorageRepository(final Context mContext) {
        this.mContext = mContext;
        this.mStorage = FirebaseStorage.getInstance().getReference();
    }

    public void downloadImage(final String imageUri) {
        StorageReference imageToDownload = mStorage.child(imageUri);
    }
}
