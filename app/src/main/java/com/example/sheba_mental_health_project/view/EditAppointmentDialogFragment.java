package com.example.sheba_mental_health_project.view;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TimePicker;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.RangeTimePickerDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditAppointmentDialogFragment extends DialogFragment {

    private Appointment mAppointment;

    private long mChosenDate = -1;
    private int mChosenHourOfDay = -1;
    private int mChosenMinutes = -1;

    private final String DATE_PICKER = "date_picker";

    private final String TAG = "EditAppointmentDialog";


    public interface EditAppointmentInterface {
        void onFinishBtnClicked(Date date);
    }

    private EditAppointmentInterface listener;

    public static EditAppointmentDialogFragment newInstance(final Appointment appointment) {
        EditAppointmentDialogFragment fragment = new EditAppointmentDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("appointment", appointment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (EditAppointmentInterface) getParentFragment();
        } catch (Exception ex) {
            throw new ClassCastException("The fragment must implement EditAppointmentInterface Listener!");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAppointment = (Appointment) getArguments().getSerializable("appointment");

            final Calendar calendar = Calendar.getInstance();
            mChosenDate = mAppointment.getAppointmentDate().getTime();
            calendar.setTimeInMillis(mChosenDate);
            mChosenHourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
            mChosenMinutes = calendar.get(Calendar.MINUTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_edit_appointment_dialog, container, false);

        final MaterialTextView patientNameTv = rootView.findViewById(R.id.patient_name_tv);
        final MaterialTextView pickDateTv = rootView.findViewById(R.id.date_dialog_tv);
        final MaterialTextView pickTimeTv = rootView.findViewById(R.id.time_dialog_tv);
        final MaterialButton finishBtn = rootView.findViewById(R.id.finish_btn);

        final SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        final SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm", Locale.getDefault());
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        final Date today = new Date();
        final Date today00 = new Date(calendar.getTimeInMillis());
        calendar.setTime(today.after(mAppointment.getAppointmentDate()) ?
                today : mAppointment.getAppointmentDate());

        patientNameTv.setText(mAppointment.getPatient().getFullName());
        pickDateTv.setText(ddMMyyyy.format(mAppointment.getAppointmentDate()));
        pickTimeTv.setText(HHmm.format(mAppointment.getAppointmentDate()));

        pickDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
                constraintBuilder.setOpenAt(calendar.getTimeInMillis());
                constraintBuilder.setValidator(DateValidatorPointForward.from(today00.getTime()));
                final MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
                builder.setCalendarConstraints(constraintBuilder.build());
                builder.setTitleText(getString(R.string.appointments_date));
                builder.setSelection(calendar.getTimeInMillis());
                final MaterialDatePicker datePicker = builder.build();
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        final Long selectedTime = (Long) selection;
                        mChosenDate = selectedTime;
                        final int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                        final int minute = calendar.get(Calendar.MINUTE);
                        calendar.setTimeInMillis(selectedTime);
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        pickDateTv.setText(ddMMyyyy.format(calendar.getTime()));
                    }
                });
                datePicker.show(getChildFragmentManager(), DATE_PICKER);
            }
        });

        pickTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar nowCalendar = Calendar.getInstance();
                nowCalendar.setTime(today);
                final RangeTimePickerDialog timePickerDlg = new RangeTimePickerDialog(requireContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                mChosenHourOfDay = hourOfDay;
                                mChosenMinutes = minute;
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                pickTimeTv.setText(HHmm.format(calendar.getTime()));
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                        true);
                if (mChosenDate <= today00.getTime() + 86_400_000L) {
                    timePickerDlg.setMin(nowCalendar.get(Calendar.HOUR_OF_DAY),
                            nowCalendar.get(Calendar.MINUTE));
                }
                timePickerDlg.show();
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Date chosenDate = new Date(mChosenDate != -1 ?
                        mChosenDate : mAppointment.getAppointmentDate().getTime());
                final Calendar calendar = Calendar.getInstance();
                calendar.setTime(chosenDate);
                calendar.set(Calendar.HOUR_OF_DAY, mChosenHourOfDay);
                calendar.set(Calendar.MINUTE, mChosenMinutes);
                chosenDate.setTime(calendar.getTimeInMillis());
                if (listener != null) {
                    listener.onFinishBtnClicked(chosenDate);
                    dismiss();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        final Window window = requireDialog().getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
