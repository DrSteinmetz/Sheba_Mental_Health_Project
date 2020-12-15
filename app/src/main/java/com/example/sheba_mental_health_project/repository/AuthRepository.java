package com.example.sheba_mental_health_project.repository;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sheba_mental_health_project.model.Patient;
import com.example.sheba_mental_health_project.model.Therapist;
import com.example.sheba_mental_health_project.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AuthRepository {

    private static AuthRepository authRepository;

    private final Context mContext;

    private FirebaseAuth mAuth;
//    private FirebaseFunctions mFunctions;

    private User mUser;

    private final String TAG ="AuthRepository";

    /**
     * <------ Interfaces ------>
     */
    public interface AuthRepoLoginPatientInterface {
        void onPatientLoginSucceed();

        void onPatientLoginFailed(String message);
    }

    private AuthRepoLoginPatientInterface mAuthRepoLoginPatientListener;

    public void setLoginPatientListener(AuthRepoLoginPatientInterface authRepoLoginPatientListener){
        this.mAuthRepoLoginPatientListener = authRepoLoginPatientListener;
    }

    public interface AuthRepoLoginTherapistInterface {
        void onTherapistLoginSucceed();

        void onTherapistLoginFailed(String message);
    }

    private AuthRepoLoginTherapistInterface mAuthRepoLoginTherapistListener;

    public void setLoginTherapistListener(AuthRepoLoginTherapistInterface authRepoLoginTherapistListener){
        this.mAuthRepoLoginTherapistListener = authRepoLoginTherapistListener;
    }

    /**<------ Singleton ------>*/
    public static AuthRepository getInstance(final Context context) {
        if (authRepository == null) {
            authRepository = new AuthRepository(context);
        }
        return authRepository;
    }

    private AuthRepository(Context mContext) {
        this.mContext = mContext;
        this.mAuth = FirebaseAuth.getInstance();
    }

    public void loginPatient(final String email, final String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail succeed");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                getPatientForLogin(user.getUid());
                            }
                        } else {
                            final String error = Objects.requireNonNull(task.getException())
                                    .getMessage();

                            Log.d(TAG, "onComplete: " + error);
                            if (mAuthRepoLoginPatientListener != null) {
                                mAuthRepoLoginPatientListener.onPatientLoginFailed(error);
                            }
                        }
                    }
                });
    }

    public void loginTherapist(final String email, final String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail succeed");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                getTherapistForLogin(user.getUid());
                            }
                        } else {
                            final String error = Objects.requireNonNull(task.getException())
                                    .getMessage();

                            Log.d(TAG, "onComplete: " + error);
                            if (mAuthRepoLoginTherapistListener != null) {
                                mAuthRepoLoginTherapistListener.onTherapistLoginFailed(error);
                            }
                        }
                    }
                });
    }

    public void getPatientForLogin(final String uId) {
        final FirebaseFirestore cloudDB = FirebaseFirestore.getInstance();
        cloudDB.collection("patients")
                .document(uId)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    mUser = documentSnapshot.toObject(Patient.class);
                    Log.d(TAG, "onSuccess: " + mUser);

                    if (mAuthRepoLoginPatientListener != null) {
                        mAuthRepoLoginPatientListener.onPatientLoginSucceed();
                    }
                } else {
                    final String error = "Patient Doesn't exists";

                    Log.d(TAG, "onSuccess: " + error);
                    if (mAuthRepoLoginPatientListener != null) {
                        mAuthRepoLoginPatientListener.onPatientLoginFailed(error);
                    }
                    logOut();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                logOut();
            }
        });
    }

    public void getTherapistForLogin(final String uId) {
        final FirebaseFirestore cloudDB = FirebaseFirestore.getInstance();
        cloudDB.collection("therapists")
                .document(uId)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    mUser = documentSnapshot.toObject(Therapist.class);
                    Log.d(TAG, "onSuccess: " + mUser);
                    if (mAuthRepoLoginTherapistListener != null) {
                        mAuthRepoLoginTherapistListener.onTherapistLoginSucceed();
                    }
                } else {
                    final String error = "Therapist Doesn't exists";

                    Log.d(TAG, "onSuccess: " + error);
                    if (mAuthRepoLoginTherapistListener != null) {
                        mAuthRepoLoginTherapistListener.onTherapistLoginFailed(error);
                    }
                    logOut();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                if (mAuthRepoLoginTherapistListener != null) {
                    mAuthRepoLoginTherapistListener.onTherapistLoginFailed(e.getMessage());
                }
                logOut();
            }
        });
    }

    public void logOut(){
        mAuth.signOut();
        Log.d(TAG, "logOut:" + mAuth.getCurrentUser());
    }
}
