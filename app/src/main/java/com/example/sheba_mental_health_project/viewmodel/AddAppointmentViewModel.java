package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.Patient;
import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;
import com.example.sheba_mental_health_project.repository.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AddAppointmentViewModel extends ViewModel {

    private final Repository mRepository;

    private Patient mPatient;

    private MutableLiveData<List<Patient>> mGetAllPatientsSucceed;
    private MutableLiveData<String> mGetAllPatientsFailed;

    private  MutableLiveData<String> mAddAppointmentSucceed;
    private  MutableLiveData<String> mAddAppointmentFailed;

    private final List<String> mPatientsNames = new ArrayList<>();

    private long mChosenDate = -1;
    private int mHourOfDay = -1;
    private int mMinutes = -1;

    private final String TAG = "AddAppointmentViewModel";


    public AddAppointmentViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
    }

    public MutableLiveData<List<Patient>> getGetAllPatientsSucceed() {
        if (mGetAllPatientsSucceed == null) {
            mGetAllPatientsSucceed = new MutableLiveData<>();
            attachSetGetAllPatientsListener();
        }
        return mGetAllPatientsSucceed;
    }

    public MutableLiveData<String> getGetAllPatientsFailed() {
        if (mGetAllPatientsFailed == null) {
            mGetAllPatientsFailed = new MutableLiveData<>();
            attachSetGetAllPatientsListener();
        }
        return mGetAllPatientsFailed;
    }

    private void attachSetGetAllPatientsListener() {
        mRepository.setGetAllPatientsInterface(new Repository.RepositoryGetAllPatientsInterface() {
            @Override
            public void onGetAllPatientsSucceed(List<Patient> patients) {
                if (!mPatientsNames.isEmpty()) {
                    mPatientsNames.clear();
                }

                for (Patient patient : patients) {
                    mPatientsNames.add(patient.getFullName());
                }

                mGetAllPatientsSucceed.setValue(patients);
            }

            @Override
            public void onGetAllPatientsFailed(String error) {
                mGetAllPatientsFailed.setValue(error);
            }
        });
    }

    public MutableLiveData<String> getAddAppointmentSucceed() {
        if (mAddAppointmentSucceed == null) {
            mAddAppointmentSucceed = new MutableLiveData<>();
            attachAddAppointmentListener();
        }
        return mAddAppointmentSucceed;
    }
    public MutableLiveData<String> getAddAppointmentFailed() {
        if (mAddAppointmentFailed == null) {
            mAddAppointmentFailed = new MutableLiveData<>();
            attachAddAppointmentListener();
        }
        return mAddAppointmentFailed;
    }

    private void attachAddAppointmentListener() {
        mRepository.setAddAppointmentInterface(new Repository.RepositoryAddAppointmentInterface() {
            @Override
            public void onAddAppointmentSucceed(String appointmentId) {
                mAddAppointmentSucceed.setValue(appointmentId);
            }

            @Override
            public void onAddAppointmentFailed(String error) {
                mAddAppointmentFailed.setValue(error);
            }
        });
    }


    public Patient getPatient() {
        return mPatient;
    }

    public void setPatient(final Patient patient) {
        this.mPatient = patient;
    }

    public List<String> getPatientsNames() {
        return mPatientsNames;
    }

    public long getChosenDate() {
        return mChosenDate;
    }

    public void setChosenDate(long mChosenDate) {
        this.mChosenDate = mChosenDate;
    }

    public int getHourOfDay() {
        return mHourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.mHourOfDay = hourOfDay;
    }

    public int getMinutes() {
        return mMinutes;
    }

    public void setMinutes(int minutes) {
        this.mMinutes = minutes;
    }

    public void getAllPatients() {
        mRepository.getAllPatients();
    }

    public void addAppointment() {
        final Appointment appointmentToAdd = new Appointment(getCalculatedDate(),
                mPatient, AppointmentStateEnum.PreMeeting);

        mRepository.addAppointment(appointmentToAdd);
    }

    private Date getCalculatedDate() {
        final Date chosenDate = new Date(mChosenDate);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(chosenDate);
        calendar.set(Calendar.HOUR_OF_DAY, mHourOfDay);
        calendar.set(Calendar.MINUTE, mMinutes);
        chosenDate.setTime(calendar.getTimeInMillis());
        return chosenDate;
    }

    public void resetDateFields() {
        mChosenDate = mHourOfDay = mMinutes = -1;
    }
}
