package com.example.sheba_mental_health_project.view;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.TherapistMentalStateViewModel;

public class TherapistMentalStateFragment extends Fragment {

    private TherapistMentalStateViewModel mViewModel;

    private final String TAG = "TherapistMentalState";


    public static TherapistMentalStateFragment newInstance() {
        return new TherapistMentalStateFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.TherapistMentalState)).get(TherapistMentalStateViewModel.class);

        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container,
                        TherapistMentalGenericFragment.newInstance(mViewModel.getCurrentAppointment()))
                .commit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.therapist_mental_state_fragment, container, false);

        return rootView;
    }
}
