package com.example.sheba_mental_health_project.view.therapist;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sheba_mental_health_project.model.PatientsAdapter;
import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Patient;
import com.example.sheba_mental_health_project.model.RangeTimePickerDialog;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.AddAppointmentViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddAppointmentFragment extends Fragment
        implements CreateAppointmentDialogFragment.CreateAppointmentInterface {

    private AddAppointmentViewModel mViewModel;

    private ArrayAdapter<String> mPatientsNamesAdapter;

    private RecyclerView mRecyclerView;
    private PatientsAdapter mAdapter;

    private MaterialAutoCompleteTextView mPatientNameAutoTV;
    /*private MaterialTextView mDateTV;
    private MaterialTextView mTimeTV;
    private TextView mPatientFoundTv;
    private TextView mPatientNameTv;
    private MaterialButton mCreateAppointmentBtn;*/

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
                mPatientsNamesAdapter = new ArrayAdapter<>(requireContext(),
                        android.R.layout.select_dialog_item, mViewModel.getPatientsNames());
                mPatientNameAutoTV.setAdapter(mPatientsNamesAdapter);

                mAdapter = new PatientsAdapter(requireContext(), patients);
                mAdapter.setPatientsAdapterListener(new PatientsAdapter.PatientsAdapterInterface() {
                    @Override
                    public void onPatientClicked(Patient patient) {
                        mViewModel.setPatient(patient);

                        /*mPatientFoundTv.setVisibility(View.VISIBLE);
                        if (patient == null) {
                            mPatientFoundTv.setText(getString(R.string.patient_not_found));
                            mPatientNameTv.setVisibility(View.INVISIBLE);
                            mDateTV.setVisibility(View.INVISIBLE);
                            mTimeTV.setVisibility(View.INVISIBLE);
                            mCreateAppointmentBtn.setVisibility(View.INVISIBLE);
                        } else {
                            mPatientNameTv.setVisibility(View.VISIBLE);
                            mPatientFoundTv.setText(getString(R.string.patient_found_prompt));
                            mPatientNameTv.setText(patient.getFullName());
                            mDateTV.setVisibility(View.VISIBLE);
                            mTimeTV.setVisibility(View.VISIBLE);
                            mCreateAppointmentBtn.setVisibility(View.VISIBLE);
                        }*/
                        final CreateAppointmentDialogFragment dialog = CreateAppointmentDialogFragment
                                .newInstance(patient.getFullName(), patient.getEmail());
                        dialog.show(getChildFragmentManager(),
                                "Create_Appointment_Dialog_Fragment");
                    }

                    @Override
                    public void onEmptyResults() {
                        mPatientNameAutoTV.setError(getString(R.string.patient_does_not_exist));
                    }
                });
                mRecyclerView.setAdapter(mAdapter);

                Log.d(TAG, "onChanged: " + mViewModel.getPatientsNames().size());
            }
        };

        final Observer<String> onGetAllPatientsFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.w(TAG, "onChanged: " + error);
            }
        };

        final Observer<String> onAddAppointmentSucceed = new Observer<String>() {
            @Override
            public void onChanged(String appointmentId) {
                mPatientNameAutoTV.setText("");
                /*mPatientFoundTv.setVisibility(View.GONE);
                mPatientNameTv.setVisibility(View.GONE);
                mDateTV.setVisibility(View.GONE);
                mTimeTV.setVisibility(View.GONE);
                mDateTV.setText("");
                mTimeTV.setText("");
                mCreateAppointmentBtn.setVisibility(View.GONE);*/
                mViewModel.resetDateFields();

                Snackbar.make(requireView(), getString(R.string.appointment_added_prompt),
                        Snackbar.LENGTH_LONG).show();
            }
        };

        final Observer<String> onAddAppointmentFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.w(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getGetAllPatientsSucceed().observe(this, onGetAllPatientsSucceed);
        mViewModel.getGetAllPatientsFailed().observe(this, onGetAllPatientsFailed);

        mViewModel.getAddAppointmentSucceed().observe(this, onAddAppointmentSucceed);
        mViewModel.getAddAppointmentFailed().observe(this, onAddAppointmentFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.add_appointment_fragment, container, false);

        mPatientNameAutoTV = rootView.findViewById(R.id.patient_name_auto_tv);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        /*final TextInputLayout dateLayout = rootView.findViewById(R.id.date_layout);
        mDateTV = rootView.findViewById(R.id.date_dialog_btn);
        final TextInputLayout timeLayout = rootView.findViewById(R.id.time_layout);
        mTimeTV = rootView.findViewById(R.id.time_dialog_btn);
        mPatientFoundTv = rootView.findViewById(R.id.patient_found_title);
        mPatientNameTv = rootView.findViewById(R.id.patient_name);*/
//        final ImageButton searchBtn = rootView.findViewById(R.id.search_btn);
//        mCreateAppointmentBtn = rootView.findViewById(R.id.create_btn);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        mPatientNameAutoTV.setThreshold(1);
        mPatientNameAutoTV.setTextColor(Color.BLACK);

        // TODO: Add loading animation.
        mViewModel.getAllPatients();


        mPatientNameAutoTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mAdapter != null) {
                    mAdapter.getFilter().filter(s);
                }
            }
        });


        /*final SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        final SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm", Locale.getDefault());
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        final Date today = new Date();
        final Date today00 = new Date(calendar.getTimeInMillis());
        calendar.setTime(today);*/

        /*mDateTV.setOnClickListener(new View.OnClickListener() {
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
                datePicker.show(getChildFragmentManager(), DATE_PICKER);
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        final Long selectedTime = (Long) selection;
                        mViewModel.setChosenDate(selectedTime);
                        final int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                        final int minute = calendar.get(Calendar.MINUTE);
                        calendar.setTimeInMillis(selectedTime);
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        mDateTV.setText(ddMMyyyy.format(calendar.getTime()));
                        dateLayout.setError(null);
                        mDateTV.setError(null);
                    }
                });
            }
        });*/

        // TODO: Fix the TimePicker
        /*mTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final RangeTimePickerDialog timePickerDlg = new RangeTimePickerDialog(requireContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                mViewModel.setHourOfDay(hourOfDay);
                                mViewModel.setMinutes(minute);
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                mTimeTV.setText(HHmm.format(calendar.getTime()));
                                timeLayout.setError(null);
                                mTimeTV.setError(null);
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                        true);
                if (mViewModel.getChosenDate() <= today.getTime()) {
                    timePickerDlg.setMin(calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE));
                }
                timePickerDlg.show();
            }
        });*/

        /*mCreateAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String patientEmail = mViewModel.getPatient().getEmail();

                if (validateFields(patientEmail)) {
                    mViewModel.addAppointment();
                } else {
                    if (mViewModel.getChosenDate() == -1) {
                        final String error = getString(R.string.please_pick_a_date);
                        dateLayout.setError(error);
                        mDateTV.setError(error);
                    }

                    if (mViewModel.getHourOfDay() == -1 || mViewModel.getMinutes() == -1) {
                        final String error = getString(R.string.please_pick_a_time);
                        timeLayout.setError(error);
                        mTimeTV.setError(error);
                    }
                }
            }
        });*/

        return rootView;
    }

    private boolean validateFields(final String patientEmail) {
        return !patientEmail.isEmpty() && mViewModel.getChosenDate() != -1 &&
                mViewModel.getHourOfDay() != -1 && mViewModel.getMinutes() != -1;
    }

    private String generateErrorMessage(final String patientEmail) {
        final String errorMessage;

        if (patientEmail.isEmpty()) {
            errorMessage = getString(R.string.please_enter_patient_email);
        } else if (mViewModel.getChosenDate() == -1) {
            errorMessage = getString(R.string.please_pick_a_date);
        } else if (mViewModel.getHourOfDay() == -1 || mViewModel.getMinutes() == -1) {
            errorMessage = getString(R.string.please_pick_a_time);
        } else {
            errorMessage = getString(R.string.patient_does_not_exist);
        }

        return errorMessage;
    }

    @Override
    public void onCreateBtnClicked(final Date chosenDate) {
        if (mViewModel != null) {
            mViewModel.addAppointment(chosenDate);
        }
    }
}
