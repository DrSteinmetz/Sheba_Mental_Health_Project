package com.example.sheba_mental_health_project.repository;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

public class AuthRepository {

    private static AuthRepository authRepository;
    private AuthRepoLoginPatientInterface mAuthRepoLoginPatientInterface;
    private FirebaseAuth mAuth;

    final Context mContext;

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

    public interface AuthRepoLoginPatientInterface{
        void onPatientLoginSucceed();
        void onPatientLoginFailed();
    }
   
}
