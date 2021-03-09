package com.example.sheba_mental_health_project.view.patient;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.viewmodel.PatientLoginViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class PatientLoginFragment extends Fragment {

    private PatientLoginViewModel mViewModel;

    private final String TAG = "PatientLoginFragment";


    public interface PatientLoginFragmentInterface {
        void onPatientLoginBtnClicked();
    }

    private PatientLoginFragmentInterface listener;

    public static PatientLoginFragment newInstance() {
        return new PatientLoginFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (PatientLoginFragmentInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements PatientLoginFragmentInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.PatientLogin)).get(PatientLoginViewModel.class);

        final Observer<Void> loginObserverSuccess = new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                if (listener != null) {
                    Log.d(TAG, "onChanged: login success");
                    listener.onPatientLoginBtnClicked();
                }
            }
        };

        final Observer<String> loginObserverFailed = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String error) {
              //  mErrorTv.setVisibility(View.VISIBLE);
                Log.d(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getPatientLoginSucceed().observe(this, loginObserverSuccess);
        mViewModel.getPatientLoginFailed().observe(this, loginObserverFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.patient_login_fragment, container, false);

        final TextInputEditText emailEt = rootView.findViewById(R.id.email_et);
        final TextInputEditText passwordEt = rootView.findViewById(R.id.password_et);
        final MaterialButton loginBtn = rootView.findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();
                mViewModel.setEmail(email);
                mViewModel.setPassword(password);
                mViewModel.login();
            }
        });

        return rootView;
    }
}
