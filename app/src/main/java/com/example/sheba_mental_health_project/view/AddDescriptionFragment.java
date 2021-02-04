package com.example.sheba_mental_health_project.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.enums.BodyPartEnum;
import com.google.android.material.button.MaterialButton;

public class AddDescriptionFragment extends DialogFragment {

    private String mDescription;

    private final String TAG = "AddDescriptionFragment";

    public interface AddDescriptionFragmentInterface {
        void onFinishBtnClicked(String description);
    }

    private AddDescriptionFragmentInterface listener;

    public static AddDescriptionFragment newInstance(final String description,
                                                     final BodyPartEnum fragmentName) {
        AddDescriptionFragment fragment = new AddDescriptionFragment();
        Bundle args = new Bundle();
        args.putString("description", description);
        args.putSerializable("fragment_name", fragmentName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (getArguments() != null) {
            setListener((BodyPartEnum) getArguments().getSerializable("fragment_name"));
        }
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
        final MaterialButton finishBtn = rootView.findViewById(R.id.finish_btn);

        if (mDescription != null && mDescription.trim().length() > 0) {
            descriptionEt.setText(mDescription);
        }

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onFinishBtnClicked(descriptionEt.getText().toString());
                }
            }
        });

        return rootView;
    }

    private void setListener(final BodyPartEnum fragmentName) {
        switch (fragmentName) {
            case RightArm:
                listener = (RightArmFragment) getParentFragment();
                break;
            default:
                listener = null;
                throw new ClassCastException("The fragment must implement PainStrengthSubFragmentInterface Listener!");
        }
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
