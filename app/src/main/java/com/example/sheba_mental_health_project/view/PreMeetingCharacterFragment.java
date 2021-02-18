package com.example.sheba_mental_health_project.view;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.viewmodel.PreMeetingCharacterViewModel;

public class PreMeetingCharacterFragment extends Fragment {

    private PreMeetingCharacterViewModel mViewModel;

    public static PreMeetingCharacterFragment newInstance(Appointment appointment) {
        PreMeetingCharacterFragment fragment = new PreMeetingCharacterFragment();
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
                    .add(R.id.character_container, CharacterFragment.newInstance(appointment, true))
                    .commit();
        }


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.pre_meeting_character_fragment, container, false);


        return rootView;
    }


}