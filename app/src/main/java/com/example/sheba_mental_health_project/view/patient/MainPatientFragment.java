package com.example.sheba_mental_health_project.view.patient;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.PatientAppointmentsAdapter;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.view.MainActivity;
import com.example.sheba_mental_health_project.view.WelcomeActivity;
import com.example.sheba_mental_health_project.viewmodel.MainPatientViewModel;

import java.util.List;

public class MainPatientFragment extends Fragment {

    private MainPatientViewModel mViewModel;

    private PatientAppointmentsAdapter mAppointmentAdapter;

    private RecyclerView mRecyclerView;

    private final String TAG = "MainPatientFragment";


    public interface MainPatientInterface {
        void onMoveToPreQuestions();
        void onEnterAppointment();
        void onMoveToLounge();
    }

    private MainPatientInterface listener;

    public static MainPatientFragment newInstance() {
        return new MainPatientFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (MainPatientInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements MainPatientInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.MainPatient)).get(MainPatientViewModel.class);

        final Observer<List<Appointment>> onGetMyAppointmentsSucceed = new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments) {
                mAppointmentAdapter = new PatientAppointmentsAdapter(requireContext(), mViewModel.getAppointments());
                mAppointmentAdapter.setAppointmentListener(new PatientAppointmentsAdapter.AppointmentListener() {
                    @Override
                    public void onAppointmentClicked(int position, View view) {
                        final Appointment appointment = appointments.get(position);
                        mViewModel.setCurrentAppointment(appointment);

                        enterAppointmentByState(appointment);
                    }
                });
                mRecyclerView.setAdapter(mAppointmentAdapter);
                Log.d(TAG, "onChanged: " + appointments.size());
            }
        };

        final Observer<String> onGetMyAppointmentsFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.d(TAG, "onChanged: " + error);
            }
        };

        final Observer<Void> loginObserverSuccess = new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                mViewModel.getMyAppointments();
            }
        };

        final Observer<String> loginObserverFailed = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String error) {
                Log.d(TAG, "onChanged: " + error);
            }
        };


        mViewModel.getMyAppointmentsSucceed().observe(this,onGetMyAppointmentsSucceed);
        mViewModel.getMyAppointmentsFailed().observe(this,onGetMyAppointmentsFailed);
        mViewModel.getPatientLoginSucceed().observe(this, loginObserverSuccess);
        mViewModel.getPatientLoginFailed().observe(this, loginObserverFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.main_patient_fragment, container, false);

        mViewModel.attachGetMyAppointmentsListener();

        mRecyclerView = rootView.findViewById(R.id.recycler_view);

        // TODO: Show some text if the appointments list is empty.

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        if (mViewModel.getAuthUser() != null) {
            mViewModel.getMyAppointments();
        } else {
            mViewModel.getPatientForLogin();
        }

        return rootView;
    }

    public void enterAppointmentByState(final Appointment appointment) {
        if (listener != null) {
            if (appointment.getState() == AppointmentStateEnum.PreMeeting) {
                if (appointment.getIsFinishedPreQuestions()) {
                    listener.onMoveToLounge();
                } else {
                    listener.onMoveToPreQuestions();
                }
            } else {
                if (appointment.getIsFinishedPreQuestions()) {
                    listener.onEnterAppointment();
                } else {
                    listener.onMoveToPreQuestions();
                }
            }
        }
    }

    public final List<Appointment> getAppointmentsList() {
        final List<Appointment> appointments;

        if (mViewModel != null) {
            appointments = mViewModel.getAppointments();
        } else {
            appointments = null;
        }

        return appointments;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mViewModel.removePatientAppointmentsListener();
    }
}
