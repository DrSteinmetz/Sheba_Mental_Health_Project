package com.example.sheba_mental_health_project.repository;

import android.content.Context;

public class AuthRepository {

    private static AuthRepository authRepository;

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
    }
}
