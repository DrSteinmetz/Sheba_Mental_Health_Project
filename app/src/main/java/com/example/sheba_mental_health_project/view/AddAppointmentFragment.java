package com.example.sheba_mental_health_project.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Patient;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.AddAppointmentViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddAppointmentFragment extends Fragment {

    private AddAppointmentViewModel mViewModel;

    private ArrayAdapter<String> mPatientsEmailsAdapter;
    private MaterialAutoCompleteTextView mPatientEmailAutoTV;
    private TextView mDateTv;
    private TextView mTimeTv;

    private final String DATE_PICKER = "date_picker";

    private final String TAG = "AddAppointmentFragment";


    public static AddAppointmentFragment newInstance() {
        return new AddAppointmentFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.AddAppointment)).get(AddAppointmentViewModel.class);

        final Observer<List<Patient>> onGetAllPatientsSucceed = new Observer<List<Patient>>() {
            @Override
            public void onChanged(List<Patient> patients) {
                mPatientsEmailsAdapter = new ArrayAdapter<>(requireContext(),
                        android.R.layout.select_dialog_singlechoice, mViewModel.getPatientsEmails());
                mPatientEmailAutoTV.setAdapter(mPatientsEmailsAdapter);
                Log.d(TAG, "onChanged: "+mViewModel.getPatientsEmails().size());
            }
        };

        final Observer<String> onGetAllPatientsFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.d(TAG, "onChanged: "+error); //TODO
            }
        };

        final Observer<String> onAddAppointmentSucceed = new Observer<String>() {
            @Override
            public void onChanged(String appointmentId) {
                mPatientEmailAutoTV.setText("");
                mDateTv.setText("Appointment Date");
                mTimeTv.setText("Appointment Time");
                mViewModel.resetDateFields();
                //TODO notify that appointment added
            }
        };

        final Observer<String> onAddAppointmentFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.d(TAG, "onChanged: "+error); //TODO
            }
        };

        mViewModel.getGetAllPatientsSucceed().observe(this, onGetAllPatientsSucceed);
        mViewModel.getGetAllPatientsFailed().observe(this, onGetAllPatientsFailed);

        mViewModel.getAddAppointmentSucceed().observe(this,onAddAppointmentSucceed);
        mViewModel.getAddAppointmentFailed().observe(this,onAddAppointmentFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.add_appointment_fragment, container, false);

        mPatientEmailAutoTV = rootView.findViewById(R.id.patient_email_auto_tv);
        final MaterialButton dateBtn = rootView.findViewById(R.id.date_dialog_btn);
        mDateTv = rootView.findViewById(R.id.date_tv);
        final MaterialButton timeBtn = rootView.findViewById(R.id.time_dialog_btn);
        mTimeTv = rootView.findViewById(R.id.time_tv);
        final MaterialButton createAppointmentBtn = rootView.findViewById(R.id.create_btn);

        mPatientEmailAutoTV.setThreshold(1);
        mPatientEmailAutoTV.setTextColor(Color.BLACK);

        final SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        final Calendar calendar = Calendar.getInstance();
        final Date today = new Date();

        mViewModel.getAllPatients();

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
                constraintBuilder.setOpenAt(calendar.getTimeInMillis());
                constraintBuilder.setValidator(DateValidatorPointForward.from(calendar.getTimeInMillis()));
                final MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("Appointment's date");
                builder.setSelection(calendar.getTimeInMillis());
                final MaterialDatePicker datePicker = builder.build();
                datePicker.show(getChildFragmentManager(), DATE_PICKER);
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        final Long selectedTime = (Long) selection;
                        mViewModel.setChosenDate(selectedTime);
                        calendar.setTimeInMillis(selectedTime);
                     //   mDateTv.setText(ddMMyyyy.format(calendar));
                    }
                });
            }
        });

        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                        android.R.style.Theme_Holo_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mViewModel.setHourOfDay(hourOfDay);
                        mViewModel.setMinutes(minute);
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

        createAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String patientEmail = mPatientEmailAutoTV.getText().toString();

                 if(validateFields(patientEmail)){
                     mViewModel.addAppointment(patientEmail);
                 }else{
                     Log.d(TAG, "onClick: "+generateErrorMessage(patientEmail));   //TODO
                 }
            }
        });

        return rootView;
    }

    public boolean validateFields(final String patientEmail) {
        return !patientEmail.isEmpty() && mViewModel.getChosenDate() != -1 &&
                mViewModel.getHourOfDay() != -1 && mViewModel.getMinutes() != -1 &&
                mViewModel.getPatientsEmails().contains(patientEmail) ;
    }

    public String generateErrorMessage(final String patientEmail) {
        String errorMessage;
        if(patientEmail.isEmpty()){
            errorMessage = "Please enter Patient email";
        } else if(mViewModel.getChosenDate() == -1){
            errorMessage = "Please Pick a date";
        } else if(mViewModel.getHourOfDay()  == -1 || mViewModel.getMinutes() == -1){
            errorMessage = "Please Pick a time";
        } else {
            errorMessage = "Patient Doesn't Exist";
        }
        return errorMessage;
    }

}
