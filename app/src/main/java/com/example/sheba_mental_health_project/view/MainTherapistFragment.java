package com.example.sheba_mental_health_project.view;

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

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.TherapistAppointmentsAdapter;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.MainTherapistViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainTherapistFragment extends Fragment {

    private final String TAG = "MainTherapistFragment";

    private MainTherapistViewModel mViewModel;

    private TherapistAppointmentsAdapter mAppointmentAdapter;

    private RecyclerView mRecyclerView;

    public interface MainTherapistInterface {
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
                if (mAppointmentAdapter == null) {
                    mAppointmentAdapter = new TherapistAppointmentsAdapter(requireContext(), mViewModel.getAppointments());
                    mRecyclerView.setAdapter(mAppointmentAdapter);
                } else {
                    mAppointmentAdapter.notifyDataSetChanged();
                }
                Log.d(TAG, "onChanged: " + appointments);
            }
        };

        final Observer<String> onGetMyAppointmentsFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.d(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getMyAppointmentsSucceed().observe(this,onGetMyAppointmentsSucceed);
        mViewModel.getMyAppointmentsFailed().observe(this,onGetMyAppointmentsFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.main_therapist_fragment, container, false);

        final FloatingActionButton addAppointFab = rootView.findViewById(R.id.add_appointment_fab);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);

        //TODO: Show some text if the appointments list is empty.

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
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

        mViewModel.getMyAppointments();

        return rootView;
    }
}
