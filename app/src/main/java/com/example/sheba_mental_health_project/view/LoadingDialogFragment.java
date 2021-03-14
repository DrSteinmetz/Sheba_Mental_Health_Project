package com.example.sheba_mental_health_project.view;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.sheba_mental_health_project.R;

public class LoadingDialogFragment extends DialogFragment {

    private final String TAG = "LoadingDialogFragment";


    public LoadingDialogFragment() {}

    public static LoadingDialogFragment newInstance() {
        return new LoadingDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loading_dialog, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        final Window window = requireDialog().getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }
}
