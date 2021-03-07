package com.example.sheba_mental_health_project.view.therapist;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Patient;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.SearchPatientViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.List;

public class SearchPatientFragment extends Fragment {

    private SearchPatientViewModel mViewModel;
    private ArrayAdapter<String> mPatientsEmailsAdapter;
    private MaterialAutoCompleteTextView mPatientEmailAutoTV;
    private TextView mPatientFoundTv;
    private TextView mPatientNameTv;
    private MaterialButton mPatientHistoryBtn;

    private final String TAG = "SearchPatientFragment";


    public interface SearchPatientFragmentInterface {
        void onPatientHistoryClicked(Patient patient);
    }

    private SearchPatientFragmentInterface listener;

    public static SearchPatientFragment newInstance() {
        return new SearchPatientFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (SearchPatientFragmentInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements SearchPatientFragmentInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.SearchPatient)).get(SearchPatientViewModel.class);

        final Observer<List<Patient>> onGetAllPatientsSucceed = new Observer<List<Patient>>() {
            @Override
            public void onChanged(List<Patient> patients) {
                mPatientsEmailsAdapter = new ArrayAdapter<>(requireContext(),
                        android.R.layout.select_dialog_item, mViewModel.getPatientsEmails());
                mPatientEmailAutoTV.setAdapter(mPatientsEmailsAdapter);
                Log.d(TAG, "onChanged: " + mViewModel.getPatientsEmails().size());
            }
        };

        final Observer<String> onGetAllPatientsFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.w(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getGetAllPatientsSucceed().observe(this, onGetAllPatientsSucceed);
        mViewModel.getGetAllPatientsFailed().observe(this, onGetAllPatientsFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.search_patient_fragment, container, false);

        mPatientEmailAutoTV = rootView.findViewById(R.id.patient_email_auto_tv);
        mPatientFoundTv = rootView.findViewById(R.id.patient_found_title);
        mPatientNameTv = rootView.findViewById(R.id.patient_name);
        final ImageButton searchBtn = rootView.findViewById(R.id.search_btn);
        mPatientHistoryBtn = rootView.findViewById(R.id.patient_history_btn);

        mPatientEmailAutoTV.setThreshold(1);
        mPatientEmailAutoTV.setTextColor(Color.BLACK);


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String patientEmail = mPatientEmailAutoTV.getText().toString();
                final Patient patient = mViewModel.getPatientByEmail(patientEmail);

                mPatientFoundTv.setVisibility(View.VISIBLE);
                if (patient == null) {
                    mPatientFoundTv.setText(getString(R.string.patient_not_found));
                    mPatientNameTv.setVisibility(View.INVISIBLE);
                    mPatientHistoryBtn.setVisibility(View.INVISIBLE);

                } else {
                    mViewModel.setPatient(patient);

                    mPatientNameTv.setVisibility(View.VISIBLE);
                    mPatientFoundTv.setText(getString(R.string.patient_found_prompt));
                    mPatientNameTv.setText(patient.getFullName());
                    mPatientHistoryBtn.setVisibility(View.VISIBLE);
                }
            }
        });

        mPatientHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPatientHistoryClicked(mViewModel.getPatient());
                }
            }
        });

        mViewModel.getAllPatients();

        return rootView;
    }
}
