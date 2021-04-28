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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.ChatMessage;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.view.character.CharacterFragment;
import com.example.sheba_mental_health_project.viewmodel.AppointmentPatientViewModel;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AppointmentPatientFragment extends Fragment {

    private AppointmentPatientViewModel mViewModel;

    private ImageView mChatBadgeIv;

    private final SimpleDateFormat ddMMYYYY = new SimpleDateFormat("dd.MM.yyyy",
            Locale.getDefault());

    private final String TAG = "AppointmentPatientFrag";


    public static AppointmentPatientFragment newInstance(Appointment appointment) {
            AppointmentPatientFragment fragment = new AppointmentPatientFragment();
            Bundle args = new Bundle();
            args.putSerializable("appointment", appointment);
            fragment.setArguments(args);
            return fragment;
    }

    public interface AppointmentPatientInterface {
        void onPhysicalClicked();
        void onMentalClicked();
        void onChatClicked();
    }

    private AppointmentPatientFragment.AppointmentPatientInterface listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AppointmentPatientFragment.AppointmentPatientInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements AppointmentPatientInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this,new ViewModelFactory(getContext(),
                ViewModelEnum.AppointmentPatient)).get(AppointmentPatientViewModel.class);

        if (getArguments() != null) {
            final Appointment appointment = (Appointment) getArguments()
                    .getSerializable("appointment");

            getChildFragmentManager().beginTransaction()
                    .add(R.id.character_container, CharacterFragment.newInstance(appointment,
                            false, true))
                    .commit();
        }

        final Observer<ChatMessage> onGetLastChatMessageSucceed = new Observer<ChatMessage>() {
            @Override
            public void onChanged(ChatMessage lastMessage) {
                if (mChatBadgeIv != null && lastMessage != null) {
                    mChatBadgeIv.setVisibility(lastMessage.getIsSeen() ? View.GONE : View.VISIBLE);
                } else if (mChatBadgeIv != null) {
                    mChatBadgeIv.setVisibility(View.GONE);
                }
            }
        };

        final Observer<String> onGetLastChatMessageFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getGetLastChatMessageSucceed().observe(this, onGetLastChatMessageSucceed);
        mViewModel.getGetLastChatMessageFailed().observe(this, onGetLastChatMessageFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.appointment_patient_fragment, container, false);

        final TextView dateTv = rootView.findViewById(R.id.date_tv);
        final MaterialButton physicalMentalBtn = rootView.findViewById(R.id.feeling_btn);
        final MaterialButton chatBtn = rootView.findViewById(R.id.chat_btn);
        mChatBadgeIv = rootView.findViewById(R.id.chat_badge_iv);

        final String date = ddMMYYYY.format(mViewModel.getCurrentAppointment().getAppointmentDate());
        dateTv.setText(date);

        physicalMentalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhysicalMentalDialog physicalMentalDialog = new PhysicalMentalDialog(requireContext());
                physicalMentalDialog.setOnActionListener(new PhysicalMentalDialog.PhysicalMentalDialogActionInterface() {
                    @Override
                    public void onPhysicalBtnClicked() {
                        listener.onPhysicalClicked();
                    }

                    @Override
                    public void onMentalBtnClicked() {
                        listener.onMentalClicked();
                    }
                });
                physicalMentalDialog.show();
            }
        });

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onChatClicked();
                }
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
