package com.example.sheba_mental_health_project.view.therapist;

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
import com.example.sheba_mental_health_project.viewmodel.TherapistPhysicalStateViewModel;

public class TherapistPhysicalStateFragment extends Fragment {

    private TherapistPhysicalStateViewModel mViewModel;

    private final String TAG = "TherapistPhyStateFrag";


    public static TherapistPhysicalStateFragment newInstance() {
        return new TherapistPhysicalStateFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.TherapistPhysicalState)).get(TherapistPhysicalStateViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.therapist_physical_state_fragment,
                container, false);

        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container, TherapistPhysicalStateGenericFragment
                        .newInstance(mViewModel.getCurrentAppointment()))
                .commit();

        return rootView;
    }
}
