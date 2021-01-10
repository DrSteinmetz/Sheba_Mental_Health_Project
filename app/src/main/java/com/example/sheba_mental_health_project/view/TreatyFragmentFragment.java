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
import com.example.sheba_mental_health_project.viewmodel.TreatyViewModel;
import com.google.android.material.button.MaterialButton;

public class TreatyFragmentFragment extends Fragment {

    private TreatyViewModel mViewModel;

    private final String TAG = "TreatyFragmentFragment";


    public static TreatyFragmentFragment newInstance() {
        return new TreatyFragmentFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.Treaty)).get(TreatyViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.treaty_fragment, container, false);

        final MaterialButton continueBtn = rootView.findViewById(R.id.continue_btn);

        return rootView;
    }
}
