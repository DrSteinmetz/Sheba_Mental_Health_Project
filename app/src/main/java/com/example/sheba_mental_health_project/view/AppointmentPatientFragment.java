package com.example.sheba_mental_health_project.view;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.viewmodel.AppointmentPatientViewModel;

public class AppointmentPatientFragment extends Fragment {

    private AppointmentPatientViewModel mViewModel;

    public static AppointmentPatientFragment newInstance(Appointment appointment) {
            AppointmentPatientFragment fragment = new AppointmentPatientFragment();
            Bundle args = new Bundle();
            args.putSerializable("appointment", appointment);
            fragment.setArguments(args);
            return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            final Appointment appointment = (Appointment) getArguments()
                    .getSerializable("appointment");

            getChildFragmentManager().beginTransaction()
                    .add(R.id.character_container, CharacterFragment.newInstance(appointment, false))
                    .commit();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.appointment_patient_fragment, container, false);


        return rootView;
    }


}