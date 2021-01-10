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
import com.example.sheba_mental_health_project.viewmodel.SanityCheckViewModel;

public class SanityCheckFragment extends Fragment {

    private SanityCheckViewModel mViewModel;

    private final String TAG = "SanityCheckFragment";


    public static SanityCheckFragment newInstance() {
        return new SanityCheckFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(SanityCheckViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.sanity_check_fragment, container, false);

        return rootView;
    }
}
