package com.example.sheba_mental_health_project.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.example.sheba_mental_health_project.R;
import com.google.android.material.button.MaterialButton;

public class AddDescriptionFragment extends DialogFragment {

    private String mDescription;

    private final String TAG = "AddDescriptionFragment";

    public interface AddDescriptionFragmentInterface {
        void onClick(String description);
    }

    public AddDescriptionFragment() {
    }

    public static AddDescriptionFragment newInstance(final String description) {
        AddDescriptionFragment fragment = new AddDescriptionFragment();
        Bundle args = new Bundle();
        args.putString("description", description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDescription = getArguments().getString("description");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_add_description, container, false);

        final EditText descriptionEt = rootView.findViewById(R.id.description_content_et);
        final MaterialButton cancelBtn = rootView.findViewById(R.id.cancel_btn);
        final MaterialButton addBtn = rootView.findViewById(R.id.add_btn);

        if (mDescription == null) {
            addBtn.setText("Add");
            addBtn.setEnabled(false);
        } else {
            addBtn.setText("Update");
            addBtn.setEnabled(true);
        }

        descriptionEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addBtn.setEnabled(s.toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() != null) {
            final Window window = getDialog().getWindow();
            if (window != null) {
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }
    }
}
