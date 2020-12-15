package com.example.sheba_mental_health_project.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.sheba_mental_health_project.R;

public class WelcomeActivity extends AppCompatActivity
        implements WelcomeFragment.WelcomeFragmentInterface,
        TherapistLoginFragment.TherapistLoginFragmentInterface,
        PatientLoginFragment.PatientLoginFragmentInterface {

    private final String WELCOME_FRAG = "WelcomeFragment";

    private final String THERAPIST_LOGIN_FRAG = "Therapist_Login_Fragment";
    private final String PATIENT_LOGIN_FRAG = "Patient_Login_Fragment";

    private final String TAG = "WelcomeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

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
    }

    @Override
    public void onPatientLoginBtnClicked() {
    }
}
