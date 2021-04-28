package com.example.sheba_mental_health_project.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.fragment.app.DialogFragment;

import com.example.sheba_mental_health_project.R;
import com.google.android.material.button.MaterialButton;

public class WellDoneDialogFragment extends DialogFragment {

    private final String TAG = "WellDoneDialogFragment";


    public WellDoneDialogFragment() {}

    public static WellDoneDialogFragment newInstance() {
        return new WellDoneDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_well_done_dialog, container, false);

        final MaterialButton btn = rootView.findViewById(R.id.ok_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        final Window window = requireDialog().getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
