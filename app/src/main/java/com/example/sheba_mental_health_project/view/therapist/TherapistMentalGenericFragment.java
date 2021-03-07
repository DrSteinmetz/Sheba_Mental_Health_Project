package com.example.sheba_mental_health_project.view.therapist;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.Feeling;
import com.example.sheba_mental_health_project.model.TherapistMentalStateAdapter;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.TherapistMentalGenericViewModel;

import java.util.List;
import java.util.Map;

public class TherapistMentalGenericFragment extends Fragment {

    private TherapistMentalGenericViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private TherapistMentalStateAdapter mTherapistMentalStateAdapter;

    private final String TAG = "TherapistMentalGeneric";


    public static TherapistMentalGenericFragment newInstance(final Appointment appointment) {
        final TherapistMentalGenericFragment fragment = new TherapistMentalGenericFragment();
        Bundle args = new Bundle();
        args.putSerializable("appointment", appointment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.TherapistMentalGeneric)).get(TherapistMentalGenericViewModel.class);

        if (getArguments() != null) {
            final Appointment appointment = (Appointment) getArguments().getSerializable("appointment");
            mViewModel.setAppointment(appointment);
        }

        final Observer<List<Feeling>> onGetFeelingsSucceed = new Observer<List<Feeling>>() {
            @Override
            public void onChanged(List<Feeling> feelings) {
                mViewModel.getFeelingsAnswers();
            }
        };

        final Observer<String> onGetFeelingsFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);
            }
        };

        final Observer<Map<String, Integer>> onGetFeelingsAnswersSucceed = new Observer<Map<String, Integer>>() {
            @Override
            public void onChanged(Map<String, Integer> feelingsAnswersMap) {
                mTherapistMentalStateAdapter = new TherapistMentalStateAdapter(mViewModel
                        .getGetFeelingsSucceed().getValue(),
                        feelingsAnswersMap);
                mRecyclerView.setAdapter(mTherapistMentalStateAdapter);
            }
        };

        final Observer<String> onGetFeelingsAnswersFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getGetFeelingsAnswersSucceed().observe(this, onGetFeelingsAnswersSucceed);
        mViewModel.getGetFeelingsAnswersFailed().observe(this, onGetFeelingsAnswersFailed);
        mViewModel.getGetFeelingsSucceed().observe(this, onGetFeelingsSucceed);
        mViewModel.getGetFeelingsFailed().observe(this, onGetFeelingsFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.therapist_mental_generic_fragment, container, false);

        mViewModel.attachSetGetFeelingsAnswersListener();

        mRecyclerView = rootView.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        mViewModel.getFeelings();

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mViewModel.removeGetFeelingsAnswersListener();
    }
}
