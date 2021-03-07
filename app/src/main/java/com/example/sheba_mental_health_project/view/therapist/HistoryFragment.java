package com.example.sheba_mental_health_project.view.therapist;

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
import android.widget.TextView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.Patient;
import com.example.sheba_mental_health_project.model.TherapistHistoryAdapter;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.HistoryViewModel;

import java.util.List;

public class HistoryFragment extends Fragment {

    private HistoryViewModel mViewModel;

    private TherapistHistoryAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private TextView mEmptyListTv;

    private final String TAG = "HistoryFragment";


    public interface HistoryFragmentInterface {
        void onHistoryAppointmentClicked(final Appointment appointment);
    }

    private HistoryFragmentInterface listener;

    public static HistoryFragment newInstance(final Patient patient) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putSerializable("patient", patient);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (HistoryFragmentInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements HistoryFragmentInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.History)).get(HistoryViewModel.class);

        if (getArguments() != null) {
            mViewModel.setPatient((Patient) getArguments().getSerializable("patient"));
        }


        final Observer<List<Appointment>> onGetAppointmentsOfSpecificPatientSucceed = new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments) {
                mAdapter = new TherapistHistoryAdapter(requireContext(), mViewModel.getAppointments());
                mAdapter.setAppointmentListener(new TherapistHistoryAdapter.AppointmentListener() {
                    @Override
                    public void onAppointmentClicked(int position, View view) {
                        final Appointment appointment = mViewModel.getAppointments().get(position);

                        if (listener != null) {
                            listener.onHistoryAppointmentClicked(appointment);
                        }
                    }
                });
                mRecyclerView.setAdapter(mAdapter);

                if (mViewModel.getAppointments() != null && !mViewModel.getAppointments().isEmpty()) {
                    mEmptyListTv.setVisibility(View.GONE);
                } else {
                    mEmptyListTv.setVisibility(View.VISIBLE);
                }
            }
        };

        final Observer<String> onGetAppointmentsOfSpecificPatientFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getAppointmentsOfSpecificPatientSucceed().observe(this, onGetAppointmentsOfSpecificPatientSucceed);
        mViewModel.getAppointmentsOfSpecificPatientFailed().observe(this, onGetAppointmentsOfSpecificPatientFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.history_fragment, container, false);

        final TextView patientNameTv = rootView.findViewById(R.id.patient_name_tv);
        mEmptyListTv = rootView.findViewById(R.id.empty_list_tv);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);

        patientNameTv.setText(mViewModel.getPatient().getFullName());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        mViewModel.attachGetAppointmentsOfSpecificPatientListener();

        mViewModel.getAppointmentsOfSpecificPatient();

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mViewModel != null) {
            mViewModel.removeGetAppointmentsOfSpecificPatientListener();
        }
    }
}
