package com.example.sheba_mental_health_project.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sheba_mental_health_project.model.PainPoint;
import com.example.sheba_mental_health_project.model.RotateBitmap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StorageRepository {

    @SuppressLint("StaticFieldLeak")
    private static StorageRepository storageRepository;

    private final StorageReference mStorage;

    private final Context mContext;

    private final int COMPRESS_PERCENTAGE = 50;

    private final String TAG = "StorageRepository";


    /**<------ Interfaces ------>*/
    /*<------ Upload Document ------>*/
    public interface RepositoryUploadDocumentInterface {
        void onUploadDocumentSucceed(Uri uri);

        void onUploadDocumentFailed(String error);
    }

    private RepositoryUploadDocumentInterface mRepositoryUploadDocumentListener;

    public void setUploadDocumentInterface(RepositoryUploadDocumentInterface uploadDocumentInterface){
        this.mRepositoryUploadDocumentListener = uploadDocumentInterface;
    }

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

    public void uploadDocument(final Uri uri, final String appointmentId) {
        final StorageReference docToUpload = mStorage.child("Documents/" + appointmentId + "/" +
                System.nanoTime() + ".jpg");
        Log.d(TAG, "uploadDocument: "+ docToUpload);

        try {
            final RotateBitmap rotateBitmap = new RotateBitmap();
            final Bitmap bitmap = rotateBitmap.HandleSamplingAndRotationBitmap(mContext, uri);
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS_PERCENTAGE, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();

            docToUpload.putBytes(bytes)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Objects.requireNonNull(Objects.
                                    requireNonNull(taskSnapshot.getMetadata())
                                    .getReference())
                                    .getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            if(mRepositoryUploadDocumentListener != null){
                                                mRepositoryUploadDocumentListener.onUploadDocumentSucceed(uri);
                                            }
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if(mRepositoryUploadDocumentListener != null){
                                mRepositoryUploadDocumentListener.onUploadDocumentFailed(e.getMessage());
                            }
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteDocument(String uri) {
        StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(uri);
        photoRef.delete();
    }
}
