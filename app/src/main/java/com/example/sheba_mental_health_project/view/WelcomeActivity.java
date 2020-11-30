package com.example.sheba_mental_health_project.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sheba_mental_health_project.R;

public class WelcomeActivity extends AppCompatActivity
        implements WelcomeFragment.WelcomeFragmentInterface {

    private final String WELCOME_FRAG = "WelcomeFragment";

    private final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        getSupportFragmentManager().beginTransaction()
                //TODO: add enter and exit animations
                .add(WelcomeFragment.newInstance(), WELCOME_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onTherapistBtnClicked() {
    }

    @Override
    public void onPatientBtnClicked() {
    }
}
