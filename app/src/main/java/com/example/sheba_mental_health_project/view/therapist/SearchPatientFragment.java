package com.example.sheba_mental_health_project.view.therapist;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.sheba_mental_health_project.model.PatientsAdapter;
import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Patient;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.SearchPatientViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.List;

public class SearchPatientFragment extends Fragment {

    private ScrollView mMainScrollView;

    private RecyclerView mRecyclerView;

    private PatientsAdapter mAdapter;

    private SearchPatientViewModel mViewModel;
    private ArrayAdapter<String> mPatientsEmailsAdapter;
    private MaterialAutoCompleteTextView mPatientNameAutoTV;
    /*private TextView mPatientFoundTv;
    private TextView mPatientNameTv;
    private MaterialButton mPatientHistoryBtn;*/

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
                        android.R.layout.select_dialog_item, mViewModel.getPatientsNames());
                mPatientNameAutoTV.setAdapter(mPatientsEmailsAdapter);

                mAdapter = new PatientsAdapter(requireContext(), patients);
                mAdapter.setPatientsAdapterListener(new PatientsAdapter.PatientsAdapterInterface() {
                    @Override
                    public void onPatientClicked(Patient patient) {

//                        mPatientFoundTv.setVisibility(View.VISIBLE);
                        if (patient == null) {
                            /*mPatientFoundTv.setText(getString(R.string.patient_not_found));
                            mPatientNameTv.setVisibility(View.GONE);
                            mPatientHistoryBtn.setVisibility(View.GONE);*/
                        } else {
                            mViewModel.setPatient(patient);

                            /*mPatientNameTv.setVisibility(View.VISIBLE);
                            mPatientFoundTv.setText(getString(R.string.patient_found_prompt));
                            mPatientNameTv.setText(patient.getFullName());
                            mPatientHistoryBtn.setVisibility(View.VISIBLE);

                            mMainScrollView.smoothScrollTo(0, mMainScrollView.getHeight());*/
                            if (listener != null) {
                                listener.onPatientHistoryClicked(mViewModel.getPatient());
                            }
                        }
                    }

                    @Override
                    public void onEmptyResults() {
                        mPatientNameAutoTV.setError(getString(R.string.patient_does_not_exist));
                    }
                });
                mRecyclerView.setAdapter(mAdapter);

                Log.d(TAG, "onChanged: " + mViewModel.getPatientsNames().size());
            }
        };

        final Observer<String> onGetAllPatientsFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.w(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getGetPatientsOfSpecificTherapistSucceed().observe(this, onGetAllPatientsSucceed);
        mViewModel.getGetPatientsOfSpecificTherapistFailed().observe(this, onGetAllPatientsFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.search_patient_fragment, container, false);

//        mMainScrollView = rootView.findViewById(R.id.main_scroll_view);
        mPatientNameAutoTV = rootView.findViewById(R.id.patient_name_auto_tv);
        /*mPatientFoundTv = rootView.findViewById(R.id.patient_found_title);
        mPatientNameTv = rootView.findViewById(R.id.patient_name);*/
//        final ImageButton searchBtn = rootView.findViewById(R.id.search_btn);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
//        mPatientHistoryBtn = rootView.findViewById(R.id.patient_history_btn);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        mPatientNameAutoTV.setThreshold(1);
        mPatientNameAutoTV.setTextColor(Color.BLACK);


        mPatientNameAutoTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (mAdapter != null) {
                    mAdapter.getFilter().filter(s);
                }
            }
        });

        /*mPatientHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPatientHistoryClicked(mViewModel.getPatient());
                }
            }
        });*/

        mViewModel.getAllMyPatients();

        return rootView;
    }
}
