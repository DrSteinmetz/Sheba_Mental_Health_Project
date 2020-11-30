package com.example.sheba_mental_health_project.view;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Enum.ViewModelEnum;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.viewmodel.WelcomeViewModel;
import com.google.android.material.button.MaterialButton;

public class WelcomeFragment extends Fragment {

    private WelcomeViewModel mViewModel;

    private final String TAG = "WelcomeFragment";

    public interface WelcomeFragmentInterface {
        void onTherapistBtnClicked();
        void onPatientBtnClicked();
    }

    private WelcomeFragmentInterface listener;

    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (WelcomeFragmentInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements WelcomeFragmentInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.Welcome)).get(WelcomeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.welcome_fragment, container, false);

        MaterialButton therapistBtn = rootView.findViewById(R.id.therapist_btn);
        MaterialButton patientBtn = rootView.findViewById(R.id.patient_btn);

        therapistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTherapistBtnClicked();
                }
            }
        });

        patientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPatientBtnClicked();
                }
            }
        });

        return rootView;
    }
}
