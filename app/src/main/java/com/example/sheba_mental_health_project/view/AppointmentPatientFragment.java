package com.example.sheba_mental_health_project.view;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.AppointmentNotYetStartedViewModel;
import com.example.sheba_mental_health_project.viewmodel.AppointmentPatientViewModel;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AppointmentPatientFragment extends Fragment {

    private AppointmentPatientViewModel mViewModel;
    final SimpleDateFormat ddMMYYYY = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    public static AppointmentPatientFragment newInstance(Appointment appointment) {
            AppointmentPatientFragment fragment = new AppointmentPatientFragment();
            Bundle args = new Bundle();
            args.putSerializable("appointment", appointment);
            fragment.setArguments(args);
            return fragment;

    }

    public interface AppointmentPatientInterface {
        void onPhysicalClicked();
        void onMentalClicked();
    }

    private AppointmentPatientFragment.AppointmentPatientInterface listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AppointmentPatientFragment.AppointmentPatientInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements AppointmentPatientInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this,new ViewModelFactory(getContext(),
                ViewModelEnum.AppointmentPatient)).get(AppointmentPatientViewModel.class);

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

        final TextView dateTv = rootView.findViewById(R.id.date_tv);
        final MaterialButton physicalMentalBtn = rootView.findViewById(R.id.feeling_btn);

        final String date = ddMMYYYY.format(mViewModel.getCurrentAppointment().getAppointmentDate());
        dateTv.setText(date);

        physicalMentalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhysicalMentalDialog physicalMentalDialog = new PhysicalMentalDialog(requireContext());
                physicalMentalDialog.setOnActionListener(new PhysicalMentalDialog.PhysicalMentalDialogActionInterface() {
                    @Override
                    public void onPhysicalBtnClicked() {
                        listener.onPhysicalClicked();
                    }

                    @Override
                    public void onMentalBtnClicked() {
                        listener.onMentalClicked();
                    }
                });
                physicalMentalDialog.show();
            }
        });

        return rootView;
    }


}