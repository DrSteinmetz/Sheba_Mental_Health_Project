package com.example.sheba_mental_health_project.view.therapist;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.view.ConfirmationDialog;
import com.example.sheba_mental_health_project.view.WarningDialog;
import com.example.sheba_mental_health_project.view.character.CharacterFragment;
import com.example.sheba_mental_health_project.viewmodel.StartMeetingViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StartMeetingFragment extends Fragment
        implements EditAppointmentDialogFragment.EditAppointmentInterface {

    private StartMeetingViewModel mViewModel;

    private RelativeLayout mMainLayout;
    private TextView mDateTv;
    private TextView mTherapistNameTv;
    private TextView mPatientNameTv;
    private TextView mNoMeetingTv;

    private final String EDIT_APPOINTMENT_DLG_FRAG = "Edit_Appointment_Dialog_Fragment";

    private final String TAG = "StartMeetingFragment";


    public interface StartMeetingTherapistInterface {
        void onTherapistStartMeetingClicked();
    }

    private StartMeetingTherapistInterface listener;

    public static StartMeetingFragment newInstance() {
        return new StartMeetingFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (StartMeetingTherapistInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements StartMeetingTherapistInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.StartMeeting)).get(StartMeetingViewModel.class);

        final Observer<Appointment> onAppointmentSucceedObserver = new Observer<Appointment>() {
            @Override
            public void onChanged(Appointment appointment) {
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm | dd.MM");

                mPatientNameTv.setText(appointment.getPatient().getFullName());
                mTherapistNameTv.setText(appointment.getTherapist().getFullName());
                mDateTv.setText(simpleDateFormat.format(appointment.getAppointmentDate()));
                mMainLayout.setVisibility(View.VISIBLE);
                mNoMeetingTv.setVisibility(View.GONE);

                getChildFragmentManager().beginTransaction()
                        .add(R.id.character_container, CharacterFragment.newInstance(appointment,
                                false, true))
                        .commit();
            }
        };

        final Observer<String> onAppointmentFailedObserver = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);
                mMainLayout.setVisibility(View.GONE);
                mNoMeetingTv.setVisibility(View.VISIBLE);
            }
        };

        final Observer<AppointmentStateEnum> onAppointmentStateSucceedObserver = new Observer<AppointmentStateEnum>() {
            @Override
            public void onChanged(AppointmentStateEnum appointmentStateEnum) {
                if (listener != null) {
                    listener.onTherapistStartMeetingClicked();
                }
            }
        };

        final Observer<String> onAppointmentStateFailedObserver = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);
            }
        };

        final Observer<String> onUpdateAppointmentSucceedObserver = new Observer<String>() {
            @Override
            public void onChanged(String appointmentId) {
                Snackbar.make(requireView(), getString(R.string.appointment_updated_prompt),
                        Snackbar.LENGTH_LONG).show();
            }
        };

        final Observer<String> onUpdateAppointmentFailedObserver = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);
            }
        };

        final Observer<Appointment> onDeleteAppointmentSucceedObserver = new Observer<Appointment>() {
            @Override
            public void onChanged(Appointment appointment) {
                Snackbar.make(requireView(), getString(R.string.appointment_deleted_prompt),
                        Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.undo), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mViewModel.addAppointment(appointment);
                            }
                        }).show();
                requireActivity().onBackPressed();
            }
        };

        final Observer<String> onDeleteAppointmentFailedObserver = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getGetLastAppointmentSucceed().observe(this, onAppointmentSucceedObserver);
        mViewModel.getGetLastAppointmentFailed().observe(this, onAppointmentFailedObserver);
        mViewModel.getGetUpdateStateSucceed().observe(this, onAppointmentStateSucceedObserver);
        mViewModel.getGetUpdateStateFailed().observe(this, onAppointmentStateFailedObserver);
        mViewModel.getUpdateAppointmentSucceed().observe(this, onUpdateAppointmentSucceedObserver);
        mViewModel.getUpdateAppointmentFailed().observe(this, onUpdateAppointmentFailedObserver);
        mViewModel.getDeleteAppointmentSucceed().observe(this, onDeleteAppointmentSucceedObserver);
        mViewModel.getDeleteAppointmentFailed().observe(this, onDeleteAppointmentFailedObserver);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.start_meeting_fragment, container, false);

        mDateTv = rootView.findViewById(R.id.last_meeting_date_tv);
        mTherapistNameTv = rootView.findViewById(R.id.last_therapist_name_tv);
        mPatientNameTv = rootView.findViewById(R.id.patient_name_tv);
        final TextView summaryTv = rootView.findViewById(R.id.summary_tv);
        mNoMeetingTv = rootView.findViewById(R.id.no_meeting_tv);
        mMainLayout = rootView.findViewById(R.id.start_meeting_relative);
        final MaterialButton startMeetingBtn = rootView.findViewById(R.id.start_meeting_btn);
        final FloatingActionButton editFab = rootView.findViewById(R.id.edit_appointment_fab);
        final FloatingActionButton deleteFab = rootView.findViewById(R.id.delete_appointment_fab);

        summaryTv.setPaintFlags(summaryTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        summaryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String diagnosis = mViewModel.getGetLastAppointmentSucceed().getValue()
                        .getDiagnosis();
                String recommendations = mViewModel.getGetLastAppointmentSucceed().getValue()
                        .getRecommendations();

                diagnosis = (diagnosis == null || diagnosis.isEmpty()) ?
                        getString(R.string.no_diagnosis) : diagnosis ;
                recommendations = (recommendations == null || recommendations.isEmpty()) ?
                        getString(R.string.no_recommendations) : recommendations;

                final ConfirmationDialog summaryDialog = new ConfirmationDialog(requireContext());
                summaryDialog.setTitleWarningText(getString(R.string.last_meeting_summary));
                summaryDialog.setPromptText(getString(R.string.diagnosis_prompt) + "\n"
                        + diagnosis + "\n\n"
                        + getString(R.string.recommendations_prompt) + "\n"
                        + recommendations);
                summaryDialog.show();
            }
        });

        editFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditAppointmentDialogFragment.newInstance(mViewModel.getAppointment())
                        .show(getChildFragmentManager(), EDIT_APPOINTMENT_DLG_FRAG);
            }
        });

        deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WarningDialog warningDialog = new WarningDialog(requireContext());
                warningDialog.setPromptText(getString(R.string.appointment_deletion_prompt));
                warningDialog.setOnActionListener(new WarningDialog.WarningDialogActionInterface() {
                    @Override
                    public void onYesBtnClicked() {
                        mViewModel.deleteAppointment();
                    }

                    @Override
                    public void onNoBtnClicked() {}
                });
                warningDialog.show();
            }
        });

        startMeetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WarningDialog warningDialog = new WarningDialog(requireContext());
                warningDialog.setTitleWarningText(getString(R.string.start_meeting_title));
                warningDialog.setPromptText(getString(R.string.start_meeting_prompt));
                warningDialog.setOnActionListener(new WarningDialog.WarningDialogActionInterface() {
                    @Override
                    public void onYesBtnClicked() {
                        mViewModel.updateState(AppointmentStateEnum.OnGoing);
                    }

                    @Override
                    public void onNoBtnClicked() {}
                });
                warningDialog.show();
            }
        });

        mViewModel.getLastMeeting();
        return  rootView;
    }

    @Override
    public void onFinishBtnClicked(Date date) {
        if (!date.equals(mViewModel.getAppointment().getAppointmentDate())) {
            mViewModel.getAppointment().setAppointmentDate(date);
            mViewModel.updateAppointment();
        }
    }
}
