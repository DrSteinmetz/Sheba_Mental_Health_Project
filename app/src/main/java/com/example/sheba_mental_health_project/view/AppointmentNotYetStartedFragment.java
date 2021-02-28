package com.example.sheba_mental_health_project.view;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.AppointmentNotYetStartedViewModel;
import com.example.sheba_mental_health_project.viewmodel.StartMeetingViewModel;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AppointmentNotYetStartedFragment extends Fragment {

    private AppointmentNotYetStartedViewModel mViewModel;

    final SimpleDateFormat ddMMYYYY = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    public static AppointmentNotYetStartedFragment newInstance() {
        return new AppointmentNotYetStartedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this,new ViewModelFactory(getContext(),
                ViewModelEnum.AppointmentNotYetStarted)).get(AppointmentNotYetStartedViewModel.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.appointment_not_yet_started_fragment, container, false);

        final TextView dateTv = rootView.findViewById(R.id.date_tv);
        final String date = ddMMYYYY.format(mViewModel.getCurrentAppointment().getAppointmentDate());

        dateTv.setText(date);

        return rootView;
    }


}