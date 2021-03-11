package com.example.sheba_mental_health_project.view.therapist;

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
import android.widget.TextView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.view.character.CharacterFragment;
import com.example.sheba_mental_health_project.view.WarningDialog;
import com.example.sheba_mental_health_project.viewmodel.AppointmentTherapistViewModel;
import com.google.android.material.button.MaterialButton;

public class AppointmentTherapistFragment extends Fragment {

    private AppointmentTherapistViewModel mViewModel;

    private final String TAG = "AppointmentTherapist";


    public static AppointmentTherapistFragment newInstance() {
        return new AppointmentTherapistFragment();
    }

    public interface AppointmentTherapistInterface {
        void onChatClicked();
        void onMentalStateClicked();
        void onPhysicalStateClicked();
        void onInquiryClicked();
        void onEndMeetingBtnClicked();
    }

    private AppointmentTherapistInterface listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AppointmentTherapistInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements AppointmentTherapistInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.AppointmentTherapist)).get(AppointmentTherapistViewModel.class);

        getChildFragmentManager().beginTransaction()
                .add(R.id.character_container,
                        CharacterFragment.newInstance(mViewModel.getCurrentAppointment(),
                                false, true))
                .commit();

        final Observer<AppointmentStateEnum> onUpdateAppointmentStateSucceed = new Observer<AppointmentStateEnum>() {
            @Override
            public void onChanged(AppointmentStateEnum appointmentStateEnum) {
                if (listener != null) {
                    listener.onEndMeetingBtnClicked();
                }
            }
        };

        final Observer<String> onUpdateAppointmentStateFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getUpdateAppointmentStateSucceed().observe(this, onUpdateAppointmentStateSucceed);
        mViewModel.getUpdateAppointmentStateFailed().observe(this, onUpdateAppointmentStateFailed);
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

        final TextView patientNameTv = rootView.findViewById(R.id.patient_name_tv);
        final TextView therapistNameTv = rootView.findViewById(R.id.therapist_name_tv);

        patientNameTv.setText(mViewModel.getCurrentAppointment().getPatient().getFullName());
        therapistNameTv.setText(mViewModel.getCurrentAppointment().getTherapist().getFullName());

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onChatClicked();
                }
            }
        });

        mentalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onMentalStateClicked();
                }
            }
        });

        physicalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPhysicalStateClicked();
                }
            }
        });

        inquiryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onInquiryClicked();
                }
            }
        });

        endMeetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WarningDialog warningDialog = new WarningDialog(requireContext());
                
                warningDialog.setTitleWarningText(getString(R.string.end_meeting_question));
                warningDialog.setPromptText(getString(R.string.end_meeting_msg));
                warningDialog.setOnActionListener(new WarningDialog.WarningDialogActionInterface() {
                    @Override
                    public void onYesBtnClicked() {
                        mViewModel.updateState(AppointmentStateEnum.Ended);
                    }

                    @Override
                    public void onNoBtnClicked() {}
                });
                warningDialog.show();
            }
        });

        return rootView;
    }
}
