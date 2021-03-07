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
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.TreatyViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

public class TreatyFragment extends Fragment {

    private TreatyViewModel mViewModel;

    private final String TAG = "TreatyFragment";


    public interface TreatyFragmentInterface {
        void onContinueToBureaucracy();
    }

    private TreatyFragmentInterface listener;

    public static TreatyFragment newInstance() {
        return new TreatyFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (TreatyFragmentInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements TreatyFragmentInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.Treaty)).get(TreatyViewModel.class);

        final Observer<Appointment> onUpdateAnswersOfAppointmentSucceed = new Observer<Appointment>() {
            @Override
            public void onChanged(Appointment appointment) {
                if (listener != null) {
                    listener.onContinueToBureaucracy();
                }
            }
        };

        final Observer<String> onUpdateAnswersOfAppointmentFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getUpdateAnswersOfAppointmentSucceed().observe(this, onUpdateAnswersOfAppointmentSucceed);
        mViewModel.getUpdateAnswersOfAppointmentFailed().observe(this, onUpdateAnswersOfAppointmentFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.treaty_fragment, container, false);

        final RadioGroup meetingTimePrefRg = rootView.findViewById(R.id.meeting_time_pref_rg);
        final MaterialCheckBox anotherPersonCb = rootView.findViewById(R.id.another_person_cb);
        final MaterialButton continueBtn = rootView.findViewById(R.id.continue_btn);

        mViewModel.attachSetUpdateAnswersOfAppointmentListener();

        /**<------ Questions Initialization ------>*/
        for (int i = 0; i < meetingTimePrefRg.getChildCount(); i++) {
            final RadioButton radioButton = (RadioButton) meetingTimePrefRg.getChildAt(i);
            radioButton.setChecked(mViewModel.isQuestionChecked(radioButton.getTag().toString()));
        }
        anotherPersonCb.setChecked(mViewModel.isQuestionChecked(anotherPersonCb.getTag().toString()));


        meetingTimePrefRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    final RadioButton radioButton = (RadioButton) group.getChildAt(i);
                    mViewModel.updateAnswers(radioButton.isChecked(),
                            radioButton.getTag().toString());
                }
            }
        });

        anotherPersonCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mViewModel.updateAnswers(isChecked, anotherPersonCb.getTag().toString());
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    mViewModel.updateAnswerInCloud();
                }
            }
        });

        return rootView;
    }
}
