package com.example.sheba_mental_health_project.view.patient;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.view.character.CharacterFragment;
import com.example.sheba_mental_health_project.viewmodel.PhysicalPatientViewModel;
import com.google.android.material.button.MaterialButton;

public class PhysicalPatientFragment extends Fragment {

    private PhysicalPatientViewModel mViewModel;

    private final String TAG = "PhysicalPatientFragment";


    public interface PhysicalPatientFragmentInterface {
        void onHomeBtnClicked();
    }

    private PhysicalPatientFragmentInterface listener;

    public static PhysicalPatientFragment newInstance() {
        return new PhysicalPatientFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (PhysicalPatientFragmentInterface) context;
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
                .add(R.id.character_container,
                        CharacterFragment.newInstance(mViewModel.getCurrentAppointment(),
                                true, false))
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
