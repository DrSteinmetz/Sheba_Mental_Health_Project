package com.example.sheba_mental_health_project.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.WelcomeViewModel;

public class WelcomeActivity extends AppCompatActivity
        implements WelcomeFragment.WelcomeFragmentInterface,
        TherapistLoginFragment.TherapistLoginFragmentInterface,
        PatientLoginFragment.PatientLoginFragmentInterface {

    private WelcomeViewModel mViewModel;

    private SharedPreferences mSharedPreferences;

    private final String WELCOME_FRAG = "WelcomeFragment";
    private final String THERAPIST_LOGIN_FRAG = "Therapist_Login_Fragment";
    private final String PATIENT_LOGIN_FRAG = "Patient_Login_Fragment";

    private final String IS_THERAPIST = "is_therapist";

    private final String TAG = "WelcomeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(this,
                ViewModelEnum.Welcome)).get(WelcomeViewModel.class);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        final Observer<Void> loginObserverSuccess = new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                Log.d(TAG, "onChanged: login success");
                final Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        final Observer<String> loginObserverFailed = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String error) {
                Log.d(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getTherapistLoginSucceed().observe(this, loginObserverSuccess);
        mViewModel.getTherapistLoginFailed().observe(this, loginObserverFailed);
        mViewModel.getPatientLoginSucceed().observe(this, loginObserverSuccess);
        mViewModel.getPatientLoginFailed().observe(this, loginObserverFailed);

        if (mViewModel.isAuthenticated()) {
            mViewModel.initializeLoggedInUser();
        }

        getSupportFragmentManager().beginTransaction()
                //TODO: add enter and exit animations
                .add(R.id.container, WelcomeFragment.newInstance(), WELCOME_FRAG)
                .commit();
    }

    @Override
    public void onTherapistBtnClicked() {
        /*getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, CharacterFragment.newInstance(), "Character_Fragment")
                .addToBackStack(null)
                .commit();*/

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, TherapistLoginFragment.newInstance(), THERAPIST_LOGIN_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onPatientBtnClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PatientLoginFragment.newInstance(), PATIENT_LOGIN_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onTherapistLoginBtnClicked() {
        startMainActivity(true);
    }

    @Override
    public void onPatientLoginBtnClicked() {
        startMainActivity(false);
    }

    private void startMainActivity(final boolean isTherapist) {
        final Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        mSharedPreferences.edit().putBoolean(IS_THERAPIST, isTherapist).commit();
//        intent.putExtra(IS_THERAPIST, isTherapist);
        startActivity(intent);
        finish();
    }
}
