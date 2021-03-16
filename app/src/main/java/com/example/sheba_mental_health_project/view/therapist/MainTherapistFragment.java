package com.example.sheba_mental_health_project.view.therapist;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
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
import com.example.sheba_mental_health_project.model.TherapistAppointmentsAdapter;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.view.MainActivity;
import com.example.sheba_mental_health_project.view.WelcomeActivity;
import com.example.sheba_mental_health_project.viewmodel.MainTherapistViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainTherapistFragment extends Fragment {

    private MainTherapistViewModel mViewModel;

    private TherapistAppointmentsAdapter mAppointmentAdapter;

    private RecyclerView mRecyclerView;

    private TextView mTherapistName;

    private final String TAG = "MainTherapistFragment";


    public interface MainTherapistInterface {
        void onTherapistAppointmentClicked();
        void onTherapistOnGoingAppointmentClicked();
        void onAddAppointClicked();
    }

    private MainTherapistInterface listener;

    public static MainTherapistFragment newInstance() {
        return new MainTherapistFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (MainTherapistInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements MainTherapistInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.MainTherapist)).get(MainTherapistViewModel.class);

        final Observer<List<Appointment>> onGetMyAppointmentsSucceed = new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments) {
                mAppointmentAdapter = new TherapistAppointmentsAdapter(requireContext(), mViewModel.getAppointments());
                mAppointmentAdapter.setAppointmentListener(new TherapistAppointmentsAdapter.AppointmentListener() {
                    @Override
                    public void onAppointmentClicked(int position, View view) {
                        final Appointment appointment = appointments.get(position);
                        mViewModel.setCurrentAppointment(appointment);

                        enterAppointmentByState(appointment);
                    }
                });
                mRecyclerView.setAdapter(mAppointmentAdapter);
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
                mTherapistName.setText(mViewModel.getTherapistFullName());
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
        mViewModel.getTherapistLoginSucceed().observe(this, loginObserverSuccess);
        mViewModel.getTherapistLoginFailed().observe(this, loginObserverFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.main_therapist_fragment, container, false);

        mTherapistName = rootView.findViewById(R.id.therapist_name_tv);

        final FloatingActionButton addAppointFab = rootView.findViewById(R.id.add_appointment_fab);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);

        // TODO: Show some text if the appointments list is empty.

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        mViewModel.attachGetMyAppointmentsListener();

        /*mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    addAppointFab.hide();
                } else {
                    addAppointFab.show();
                }
            }
        });*/

        addAppointFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onAddAppointClicked();
                }
            }
        });


        if (mViewModel.getAuthUser() != null) {
            mTherapistName.setText(mViewModel.getTherapistFullName());
            mViewModel.getMyAppointments();
        } else {
            mViewModel.getTherapistForLogin();
        }

        return rootView;
    }

    public void enterAppointmentByState(final Appointment appointment) {
        if (listener != null) {
            if (appointment.getState().equals(AppointmentStateEnum.OnGoing)) {
                listener.onTherapistOnGoingAppointmentClicked();
            } else {
                listener.onTherapistAppointmentClicked();
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

        mViewModel.removeTherapistAppointmentsListener();
    }
}
