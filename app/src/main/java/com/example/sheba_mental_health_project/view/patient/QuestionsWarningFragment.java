package com.example.sheba_mental_health_project.view.patient;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sheba_mental_health_project.R;
import com.google.android.material.button.MaterialButton;

public class QuestionsWarningFragment extends Fragment {

    private final String TAG = "QuestionsWarningFrag";


    public QuestionsWarningFragment() {}

    public interface QuestionsWarningFragmentInterface {
        void onContinueFromQuestionsWarning();
    }

    private QuestionsWarningFragmentInterface listener;

    public static QuestionsWarningFragment newInstance() {
        return new QuestionsWarningFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (QuestionsWarningFragmentInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements QuestionsWarningFragmentInterface listener!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_questions_warning, container, false);

        final MaterialButton continueBtn = rootView.findViewById(R.id.continue_btn);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onContinueFromQuestionsWarning();
                }
            }
        });

        return rootView;
    }
}
