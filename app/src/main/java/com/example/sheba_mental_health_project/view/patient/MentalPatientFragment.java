package com.example.sheba_mental_health_project.view.patient;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
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
import com.example.sheba_mental_health_project.model.MentalPatientAdapter;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.MentalPatientViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MentalPatientFragment extends Fragment {

    private MentalPatientViewModel mViewModel;
    private MentalPatientAdapter mMentalPatientAdapter;
    private RecyclerView mRecyclerView;

    private final String TAG = "MentalPatientFragment";


    public interface MentalPatientFragmentInterface {
        void onSaveFeelings();
    }

    private MentalPatientFragmentInterface listener;

    public static MentalPatientFragment newInstance() {
        return new MentalPatientFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (MentalPatientFragmentInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements MentalPatientFragmentInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.MentalPatient)).get(MentalPatientViewModel.class);

        final Observer<List<Feeling>> onGetPatientFeelingsSucceed = new Observer<List<Feeling>>() {
            @Override
            public void onChanged(List<Feeling> feelings) {
                mMentalPatientAdapter = new MentalPatientAdapter(getContext(), feelings,
                        mViewModel.getCurrentAppointment().getFeelingsAnswersMap());
                mRecyclerView.setAdapter(mMentalPatientAdapter);
            }
        };

        final Observer<String> onGetPatientFeelingsFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.w(TAG, "onChanged: " + error);
            }
        };

        final Observer<Appointment> onUpdateAnswersOfFeelingsSucceed = new Observer<Appointment>() {
            @Override
            public void onChanged(Appointment appointment) {
                if (listener != null) {
                    listener.onSaveFeelings();
                }
            }
        };

        final Observer<String> onUpdateAnswersOfFeelingsFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getGetPatientFeelingSucceed().observe(this, onGetPatientFeelingsSucceed);
        mViewModel.getGetPatientFeelingFailed().observe(this, onGetPatientFeelingsFailed);
        mViewModel.getUpdateFeelingsAnswersSucceed().observe(this, onUpdateAnswersOfFeelingsSucceed);
        mViewModel.getUpdateFeelingsAnswersFailed().observe(this, onUpdateAnswersOfFeelingsFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.mental_patient_fragment, container, false);

        mViewModel.attachSetGetPatientFeelingsListener();
        mViewModel.attachSetUpdateFeelingsAnswersListener();

        final MaterialButton saveBtn = rootView.findViewById(R.id.save_btn);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        mViewModel.getAllFeelings();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.updateAnswersOfFeelings();
            }
        });

        return rootView;
    }
}
