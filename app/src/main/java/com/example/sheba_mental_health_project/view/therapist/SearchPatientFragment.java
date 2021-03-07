package com.example.sheba_mental_health_project.view.therapist;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
    private TextView patientFoundTv;
    private TextView patientNameTv;
    private ImageButton searchBtn;
    private MaterialButton historyBtn;

    private final String TAG = "SearchPatientFragment";

    public static SearchPatientFragment newInstance() {
        return new SearchPatientFragment();
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
        patientFoundTv = rootView.findViewById(R.id.patient_found_title);
        patientNameTv = rootView.findViewById(R.id.patient_name);
        searchBtn = rootView.findViewById(R.id.search_btn);
        historyBtn = rootView.findViewById(R.id.patient_history_btn);

        mPatientEmailAutoTV.setThreshold(1);
        mPatientEmailAutoTV.setTextColor(Color.BLACK);

        mViewModel.getAllPatients();


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String patientEmail = mPatientEmailAutoTV.getText().toString();
                Patient patient = mViewModel.getPatientByEmail(patientEmail);
                patientFoundTv.setVisibility(View.VISIBLE);
                if (patient == null){
                    patientFoundTv.setText("Patient Not Found");
                    patientNameTv.setVisibility(View.INVISIBLE);
                    historyBtn.setVisibility(View.INVISIBLE);

                }
                else{
                    patientNameTv.setVisibility(View.VISIBLE);
                    patientFoundTv.setText("Patient Found:");
                    patientNameTv.setText(patient.getFullName());
                    historyBtn.setVisibility(View.VISIBLE);
                }


            }
        });



        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchPatientViewModel.class);
        // TODO: Use the ViewModel
    }

}