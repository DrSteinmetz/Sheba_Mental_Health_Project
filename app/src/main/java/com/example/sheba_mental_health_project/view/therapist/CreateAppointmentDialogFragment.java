package com.example.sheba_mental_health_project.view.therapist;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.RangeTimePickerDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateAppointmentDialogFragment extends DialogFragment {

    private MaterialTextView mDateTV;
    private MaterialTextView mTimeTV;

    private String mPatientName;
    private String mPatientEmail;

    private long mChosenDate = -1;
    private int mHourOfDay = -1;
    private int mMinutes = -1;

    private final String TAG = "CreateAppointmentDlgFr";

    public CreateAppointmentDialogFragment() {}

    public interface CreateAppointmentInterface {
        void onCreateBtnClicked(final Date chosenDate);
    }

    private CreateAppointmentInterface listener;

    public static CreateAppointmentDialogFragment newInstance(final String patientName,
                                                              final String patientEmail) {
        final CreateAppointmentDialogFragment fragment = new CreateAppointmentDialogFragment();
        final Bundle args = new Bundle();
        args.putString("patient_name", patientName);
        args.putString("patient_email", patientEmail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (CreateAppointmentInterface) getParentFragment();
        } catch (Exception ex) {
            throw new ClassCastException("The fragment must implement CreateAppointmentInterface Listener!");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mPatientName = getArguments().getString("patient_name");
            mPatientEmail = getArguments().getString("patient_email");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_create_appointment_dialog,
                container, false);

        final TextView mPatientNameTv = rootView.findViewById(R.id.patient_name_tv);
        final TextView mPatientEmailTv = rootView.findViewById(R.id.patient_email_tv);
        mDateTV = rootView.findViewById(R.id.date_dialog_btn);
        final TextInputLayout dateLayout = rootView.findViewById(R.id.date_input_layout);
        mTimeTV = rootView.findViewById(R.id.time_dialog_btn);
        final TextInputLayout timeLayout = rootView.findViewById(R.id.time_input_layout);
        final MaterialButton mCreateAppointmentBtn = rootView.findViewById(R.id.create_btn);

        mPatientNameTv.setText(mPatientName);
        mPatientEmailTv.setText(mPatientEmail);

        final SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        final SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm", Locale.getDefault());
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        final Date today = new Date();
        final Date today00 = new Date(calendar.getTimeInMillis());
        calendar.setTime(today);

        mDateTV.setOnClickListener(new View.OnClickListener() {
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
                datePicker.show(getChildFragmentManager(), "date_picker");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        mChosenDate = (Long) selection;
                        final int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                        final int minute = calendar.get(Calendar.MINUTE);
                        calendar.setTimeInMillis(mChosenDate);
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        mDateTV.setText(ddMMyyyy.format(calendar.getTime()));
                        dateLayout.setError(null);
                        mDateTV.setError(null);
                    }
                });
            }
        });

        // TODO: Fix the TimePicker
        mTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final RangeTimePickerDialog timePickerDlg = new RangeTimePickerDialog(requireContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                mHourOfDay = hourOfDay;
                                mMinutes = minute;
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                mTimeTV.setText(HHmm.format(calendar.getTime()));
                                timeLayout.setError(null);
                                mTimeTV.setError(null);
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                        true);
                if (mChosenDate <= today.getTime()) {
                    timePickerDlg.setMin(calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE));
                }
                timePickerDlg.show();
            }
        });

        mCreateAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields(mPatientEmail)) {
                    if (listener != null) {
                        final Date chosenDate = new Date(mChosenDate);
                        final Calendar calendar = Calendar.getInstance();
                        calendar.setTime(chosenDate);
                        calendar.set(Calendar.HOUR_OF_DAY, mHourOfDay);
                        calendar.set(Calendar.MINUTE, mMinutes);
                        chosenDate.setTime(calendar.getTimeInMillis());
                        listener.onCreateBtnClicked(chosenDate);
                        dismiss();
                    }
                } else {
                    if (mChosenDate == -1) {
                        final String error = getString(R.string.please_pick_a_date);
                        dateLayout.setError(error);
                        mDateTV.setError(error);
                    }

                    if (mHourOfDay == -1 || mMinutes == -1) {
                        final String error = getString(R.string.please_pick_a_time);
                        timeLayout.setError(error);
                        mTimeTV.setError(error);
                    }
                }
            }
        });

        return rootView;
    }

    private boolean validateFields(final String patientEmail) {
        return !patientEmail.isEmpty() && mChosenDate != -1 && mHourOfDay != -1 && mMinutes != -1;
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
