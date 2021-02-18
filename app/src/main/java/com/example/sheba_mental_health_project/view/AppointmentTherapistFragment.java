package com.example.sheba_mental_health_project.view;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.AppointmentTherapistViewModel;
import com.example.sheba_mental_health_project.viewmodel.StartMeetingViewModel;
import com.google.android.material.button.MaterialButton;

public class AppointmentTherapistFragment extends Fragment {

    private AppointmentTherapistViewModel mViewModel;

    public static AppointmentTherapistFragment newInstance() {
        return new AppointmentTherapistFragment();
    }

    public interface AppointmentTherapistInterface {
        void onChatClicked();
        void onPhysicalStateClicked();
    }

    private AppointmentTherapistFragment.AppointmentTherapistInterface listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AppointmentTherapistFragment.AppointmentTherapistInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements AppointmentTherapistInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this,new ViewModelFactory(getContext(),
                ViewModelEnum.AppointmentTherapist)).get(AppointmentTherapistViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.appointment_therapist_fragment, container, false);

        final MaterialButton chatBtn = rootView.findViewById(R.id.chat_btn);
        final MaterialButton mentalBtn = rootView.findViewById(R.id.mental_btn);
        final MaterialButton physicalBtn = rootView.findViewById(R.id.physical_btn);
        final MaterialButton inquiryBtn = rootView.findViewById(R.id.inquiry_btn);
        final MaterialButton endMeetingBtn = rootView.findViewById(R.id.end_meeting_btn);

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onChatClicked();
            }
        });

        physicalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPhysicalStateClicked();
            }
        });

        endMeetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WarningDialog warningDialog = new WarningDialog(requireContext());
                warningDialog.setTitleWarningText("End Meeting?");
                warningDialog.setPromptText("pressing ok will end this meeting for you and for the patient");
                warningDialog.setOnActionListener(new WarningDialog.WarningDialogActionInterface() {
                    @Override
                    public void onYesBtnClicked() {
                        mViewModel.updateState(AppointmentStateEnum.Ended);
                    }

                    @Override
                    public void onNoBtnClicked() {
                        warningDialog.dismiss();
                    }
                });
                warningDialog.show();
            }
        });
        return rootView;
    }


}