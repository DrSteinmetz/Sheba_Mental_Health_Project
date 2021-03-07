package com.example.sheba_mental_health_project.view.patient;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.view.character.CharacterFragment;
import com.example.sheba_mental_health_project.viewmodel.PreMeetingCharacterViewModel;
import com.google.android.material.button.MaterialButton;

public class PreMeetingCharacterFragment extends Fragment {

    private PreMeetingCharacterViewModel mViewModel;

    private final String TAG = "PreMeetingCharacterFrag";


    public static PreMeetingCharacterFragment newInstance() {
       return new PreMeetingCharacterFragment();
    }

    public interface PreMeetingCharacterInterface {
        void onLeaveNoteClicked();
        void onMoveToAppointmentPatient();
        void onMoveToAppointmentLounge();
    }

    private PreMeetingCharacterInterface listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (PreMeetingCharacterInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements PreMeetingCharacterInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.PreMeetingCharacter)).get(PreMeetingCharacterViewModel.class);

        final Observer<Boolean> onUpdateFinishedPreQuestionsSucceed = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (listener != null) {
                    if (mViewModel.getCurrentAppointment().getState() == AppointmentStateEnum.Ongoing) {
                        listener.onMoveToAppointmentPatient();
                    } else {
                        listener.onMoveToAppointmentLounge();
                    }
                }
            }
        };

        final Observer<String> onUpdateFinishedPreQuestionsFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getOnUpdateFinishedPreQuestionsSucceed().observe(this, onUpdateFinishedPreQuestionsSucceed);
        mViewModel.getOnUpdateFinishedPreQuestionsFailed().observe(this, onUpdateFinishedPreQuestionsFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.pre_meeting_character_fragment, container, false);

        final MaterialButton leaveNoteBtn = rootView.findViewById(R.id.leave_note_btn);
        final MaterialButton finishBtn = rootView.findViewById(R.id.finish_btn);

        getChildFragmentManager().beginTransaction()
                .add(R.id.character_container,
                        CharacterFragment.newInstance(mViewModel.getCurrentAppointment(),
                                true, false))
                .commit();

        leaveNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLeaveNoteClicked();
                }
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.updateFinishedPreQuestions();
            }
        });

        return rootView;
    }
}
