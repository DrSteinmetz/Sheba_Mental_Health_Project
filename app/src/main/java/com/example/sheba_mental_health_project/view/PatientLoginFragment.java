package com.example.sheba_mental_health_project.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Enum.ViewModelEnum;
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
                if (listener != null) {
                    listener.onPatientLoginBtnClicked();
                }
            }
        });

        return rootView;
    }
}
