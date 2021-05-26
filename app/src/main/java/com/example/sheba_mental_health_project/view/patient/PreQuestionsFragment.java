package com.example.sheba_mental_health_project.view.patient;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.PreQuestionsViewModel;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;

public class PreQuestionsFragment extends Fragment {

    private PreQuestionsViewModel mViewModel;

    private final String TAG = "PreQuestionsFragment";


    public interface PreQuestionsFragmentInterface {
        void onContinueFromPreQuestionsToRecommendationsQuestions(String recommendations);
        void onContinueFromPreQuestions();
    }

    private PreQuestionsFragmentInterface listener;

    public static PreQuestionsFragment newInstance() {
        return new PreQuestionsFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (PreQuestionsFragmentInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements PreQuestionsFragmentInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.PreQuestions)).get(PreQuestionsViewModel.class);

        final Observer<Appointment> onGetLastAppointmentSucceed = new Observer<Appointment>() {
            @Override
            public void onChanged(Appointment appointment) {

                if(listener != null) {
                    final String recommendations = appointment.getRecommendations();
                    if ((recommendations != null && !recommendations.isEmpty())) {
                        listener.onContinueFromPreQuestionsToRecommendationsQuestions(recommendations);
                    } else {
                        listener.onContinueFromPreQuestions();
                    }
                }
            }
        };

        final Observer<String> onGetLastAppointmentFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);

                if(listener != null){
                    listener.onContinueFromPreQuestions();
                }

            }
        };

        mViewModel.getGetLastAppointmentSucceed().observe(this, onGetLastAppointmentSucceed);
        mViewModel.getGetLastAppointmentFailed().observe(this, onGetLastAppointmentFailed);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.pre_qeustions_fragment, container, false);

        final TextView patientNameTv = rootView.findViewById(R.id.patient_name_tv);
        final TextView therapistNameTv = rootView.findViewById(R.id.therapist_name_tv);
        final MaterialButton beginBtn = rootView.findViewById(R.id.begin_btn);

        final String patientNameTvString = getString(R.string.hello) +
                " " + mViewModel.getPatientFirstName();
        patientNameTv.setText(patientNameTvString);

        final String therapistNameTvString = getString(R.string.pre_questions_tv_3) + " " +
                mViewModel.getTherapistFullName() + " " + getString(R.string.pre_questions_tv_4);
        therapistNameTv.setText(therapistNameTvString);

        beginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getLastMeeting();
            }
        });

        return rootView;
    }
}
