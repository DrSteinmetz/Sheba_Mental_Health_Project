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
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.PhysicalPatientViewModel;
import com.example.sheba_mental_health_project.viewmodel.PreMeetingCharacterViewModel;

public class PreMeetingCharacterFragment extends Fragment {

    private PreMeetingCharacterViewModel mViewModel;

    public static PreMeetingCharacterFragment newInstance() {
       return new PreMeetingCharacterFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.PreMeetingCharacter)).get(PreMeetingCharacterViewModel.class);


            getChildFragmentManager().beginTransaction()
                    .add(R.id.character_container, CharacterFragment.newInstance(mViewModel.getCurrentAppointment(), true))
                    .commit();



    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.pre_meeting_character_fragment, container, false);


        return rootView;
    }


}