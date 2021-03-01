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

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.PainTypeEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.PhysicalPatientViewModel;
import com.example.sheba_mental_health_project.viewmodel.RightArmViewModel;
import com.google.android.material.button.MaterialButton;

public class PhysicalPatientFragment extends Fragment {

    private PhysicalPatientViewModel mViewModel;

    public interface PhysicalPatientFragmentInterface {
        void onHomeBtnClicked();
    }

    private PhysicalPatientFragment.PhysicalPatientFragmentInterface listener;

    public static PhysicalPatientFragment newInstance() {
        return new PhysicalPatientFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


        try {
            listener = (PhysicalPatientFragment.PhysicalPatientFragmentInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements PhysicalPatientFragmentInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.PhysicalPatient)).get(PhysicalPatientViewModel.class);

        getChildFragmentManager().beginTransaction()
                .add(R.id.character_container, CharacterFragment.newInstance(mViewModel.getCurrentAppointment(), true))
                .commit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.physical_patient_fragment, container, false);

        final MaterialButton homeBtn = rootView.findViewById(R.id.home_screen_btn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onHomeBtnClicked();
            }
        });


        return rootView;
    }


}