package com.example.sheba_mental_health_project.view.therapist;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.ChatMessage;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.view.character.CharacterFragment;
import com.example.sheba_mental_health_project.view.WarningDialog;
import com.example.sheba_mental_health_project.viewmodel.AppointmentTherapistViewModel;
import com.google.android.material.button.MaterialButton;

public class AppointmentTherapistFragment extends Fragment {

    private AppointmentTherapistViewModel mViewModel;

    private MaterialButton mChatBtn;

    private final String TAG = "AppointmentTherapist";


    public static AppointmentTherapistFragment newInstance() {
        return new AppointmentTherapistFragment();
    }

    public interface AppointmentTherapistInterface {
        void onChatClicked();
        void onMentalStateClicked();
        void onPhysicalStateClicked();
        void onInquiryClicked();
        void onSummaryClicked();
        void onDocumentsClicked(final boolean isTherapist, Appointment appointment);
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

        final Observer<ChatMessage> onGetLastChatMessageSucceed = new Observer<ChatMessage>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onChanged(ChatMessage lastMessage) {
                if (lastMessage != null && mChatBtn != null) {
                    final Drawable drawableTop = lastMessage.getIsSeen() ?
                            requireContext().getDrawable(R.drawable.ic_chat_icon) :
                            requireContext().getDrawable(R.drawable.ic_chat_icon_w_badge);

                    mChatBtn.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop,
                            null, null);
                } else if (mChatBtn != null) {
                    mChatBtn.setCompoundDrawablesWithIntrinsicBounds(null,
                            requireContext().getDrawable(R.drawable.ic_chat_icon),
                            null, null);
                }
            }
        };

        final Observer<String> onGetLastChatMessageFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getUpdateAppointmentStateSucceed().observe(this, onUpdateAppointmentStateSucceed);
        mViewModel.getUpdateAppointmentStateFailed().observe(this, onUpdateAppointmentStateFailed);
        mViewModel.getGetLastChatMessageSucceed().observe(this, onGetLastChatMessageSucceed);
        mViewModel.getGetLastChatMessageFailed().observe(this, onGetLastChatMessageFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.appointment_therapist_fragment, container, false);

        mChatBtn = rootView.findViewById(R.id.chat_btn);
        final MaterialButton mentalBtn = rootView.findViewById(R.id.mental_btn);
        final MaterialButton physicalBtn = rootView.findViewById(R.id.physical_btn);
        final MaterialButton inquiryBtn = rootView.findViewById(R.id.inquiry_btn);
        final MaterialButton summaryBtn = rootView.findViewById(R.id.summary_btn);
        final MaterialButton endMeetingBtn = rootView.findViewById(R.id.end_meeting_btn);
        final MaterialButton documentsBtn = rootView.findViewById(R.id.documents_btn);

        final TextView patientNameTv = rootView.findViewById(R.id.patient_name_tv);
        final TextView therapistNameTv = rootView.findViewById(R.id.therapist_name_tv);

        patientNameTv.setText(mViewModel.getCurrentAppointment().getPatient().getFullName());
        therapistNameTv.setText(mViewModel.getCurrentAppointment().getTherapist().getFullName());

        mChatBtn.setOnClickListener(new View.OnClickListener() {
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

        summaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSummaryClicked();
                }
            }
        });

        documentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onDocumentsClicked(true,mViewModel.getCurrentAppointment());
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



        mViewModel.getLastChatMessage();

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mViewModel != null) {
            mViewModel.removeGetLastChatMessageListener();
        }
    }
}
