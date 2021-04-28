package com.example.sheba_mental_health_project.view.patient;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.view.character.CharacterFragment;
import com.example.sheba_mental_health_project.viewmodel.RecommendationViewModel;

import java.text.SimpleDateFormat;

public class RecommendationFragment extends Fragment {

    private RecommendationViewModel mViewModel;

    private RelativeLayout mMainLayout;
    private TextView mTherapistNameTv;
    private TextView mDateTv;
    private TextView mRecommendationsTv;
    private TextView mNoLastAppointmentTv;

    private final String TAG = "RecommendationFragment";


    public static RecommendationFragment newInstance() {
        return new RecommendationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.Recommendation)).get(RecommendationViewModel.class);

        final Observer<Appointment> onGetLastAppointmentSucceed = new Observer<Appointment>() {
            @Override
            public void onChanged(Appointment appointment) {
                @SuppressLint("SimpleDateFormat")
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm | dd.MM.yyyy");

                mTherapistNameTv.setText(appointment.getTherapist().getFullName());
                mDateTv.setText(simpleDateFormat.format(appointment.getAppointmentDate()));

                final String recommendations = appointment.getRecommendations();
                mRecommendationsTv.setText((recommendations != null && !recommendations.isEmpty()) ?
                         recommendations : getString(R.string.no_recommendations));

                mMainLayout.setVisibility(View.VISIBLE);
                mNoLastAppointmentTv.setVisibility(View.GONE);
            }
        };

        final Observer<String> onGetLastAppointmentFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);

                mMainLayout.setVisibility(View.GONE);
                mNoLastAppointmentTv.setVisibility(View.VISIBLE);
            }
        };

        mViewModel.getGetLastAppointmentSucceed().observe(this, onGetLastAppointmentSucceed);
        mViewModel.getGetLastAppointmentFailed().observe(this, onGetLastAppointmentFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.recommendation_fragment, container, false);

        mMainLayout = rootView.findViewById(R.id.main_layout);
        mTherapistNameTv = rootView.findViewById(R.id.therapist_name_tv);
        mDateTv = rootView.findViewById(R.id.date_tv);
        mRecommendationsTv = rootView.findViewById(R.id.recommendations_tv);
        mNoLastAppointmentTv = rootView.findViewById(R.id.no_last_appointment_tv);

        mViewModel.getLastMeeting();

        return  rootView;
    }
}
