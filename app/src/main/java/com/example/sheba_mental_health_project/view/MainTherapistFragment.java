package com.example.sheba_mental_health_project.view;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.AppointmentsAdapter;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.MainTherapistViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainTherapistFragment extends Fragment {

    private MainTherapistViewModel mViewModel;

    private AppointmentsAdapter mAppointmentAdapter;

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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.main_therapist_fragment, container, false);

        final FloatingActionButton addAppointFab = rootView.findViewById(R.id.add_appointment_fab);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);

//        mAppointmentAdapter = new AppointmentsAdapter(getContext(), );
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    addAppointFab.hide();
                } else {
                    addAppointFab.show();
                }
            }
        });

        addAppointFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onAddAppointClicked();
                }
            }
        });

        return rootView;
    }
}
