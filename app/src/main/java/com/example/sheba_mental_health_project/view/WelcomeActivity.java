package com.example.sheba_mental_health_project.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.repository.AuthRepository;
import com.google.android.material.textfield.TextInputEditText;

public class WelcomeActivity extends AppCompatActivity
        implements WelcomeFragment.WelcomeFragmentInterface {

    private final String WELCOME_FRAG = "WelcomeFragment";

    private final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
//        TextInputEditText emailEt = findViewById(R.id.email_et);
//        TextInputEditText pwEt = findViewById(R.id.password_et);
//
//        /*getSupportFragmentManager().beginTransaction()
//                //TODO: add enter and exit animations
//                .add(WelcomeFragment.newInstance(), WELCOME_FRAG)
//                .addToBackStack(null)
//                .commit();*/
//        Button btn = findViewById(R.id.login_btn);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AuthRepository authRepository = AuthRepository.getInstance(WelcomeActivity.this);
//                authRepository.loginUser(emailEt.getText().toString(),pwEt.getText().toString());
//            }
//        });
//        Button btn2 = findViewById(R.id.reg_btn);
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AuthRepository authRepository = AuthRepository.getInstance(WelcomeActivity.this);
//
//                authRepository.addMessage("abc");
//
//            //    Log.d(TAG, authRepository.addMessage("abc").getResult());
//            }
//        });
//
//        Button btn3 = findViewById(R.id.logout_btn);
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AuthRepository authRepository = AuthRepository.getInstance(WelcomeActivity.this);
//
//                authRepository.logOut();
//
//                //    Log.d(TAG, authRepository.addMessage("abc").getResult());
//            }
//        });
    }



    @Override
    public void onTherapistBtnClicked() {
    }

    @Override
    public void onPatientBtnClicked() {
    }
}
