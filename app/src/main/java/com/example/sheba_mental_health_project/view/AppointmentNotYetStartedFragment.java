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
import com.example.sheba_mental_health_project.viewmodel.AppointmentNotYetStartedViewModel;

public class AppointmentNotYetStartedFragment extends Fragment {

    private AppointmentNotYetStartedViewModel mViewModel;

    public static AppointmentNotYetStartedFragment newInstance() {
        return new AppointmentNotYetStartedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.appointment_not_yet_started_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AppointmentNotYetStartedViewModel.class);
        // TODO: Use the ViewModel
    }

}