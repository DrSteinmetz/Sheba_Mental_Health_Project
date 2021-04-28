package com.example.sheba_mental_health_project.view.therapist;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.SummaryViewModel;
import com.google.android.material.button.MaterialButton;

public class SummaryFragment extends Fragment {

    private SummaryViewModel mViewModel;

    private EditText mDiagnosisEt;
    private EditText mRecommendationsEt;

    private final String TAG = "SummaryFragment";


    public static SummaryFragment newInstance() {
        return new SummaryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.Summary)).get(SummaryViewModel.class);

        final Observer<String[]> onSetSummarySucceed = new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                requireActivity().onBackPressed();
            }
        };

        final Observer<String> onSetSummaryFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getSetSummarySucceed().observe(this, onSetSummarySucceed);
        mViewModel.getSetSummaryFailed().observe(this, onSetSummaryFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.summary_fragment, container, false);

        mDiagnosisEt = rootView.findViewById(R.id.diagnosis_et);
        mRecommendationsEt = rootView.findViewById(R.id.recommendations_et);
        final MaterialButton finishBtn = rootView.findViewById(R.id.finish_btn);

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String diagnosisTxt = mDiagnosisEt.getText().toString().trim();
                final String recommendationsTxt = mRecommendationsEt.getText().toString().trim();

                mViewModel.setSummary(diagnosisTxt, recommendationsTxt);
            }
        });

        if (mViewModel != null) {
            final String currentDiagnosis = mViewModel.getCurrentAppointment().getDiagnosis();
            final String currentRecommendations = mViewModel.getCurrentAppointment().getRecommendations();

            if (currentDiagnosis != null) {
                mDiagnosisEt.setText(currentDiagnosis);
            }

            if (currentRecommendations != null) {
                mRecommendationsEt.setText(currentRecommendations);
            }
        }

        return rootView;
    }
}
