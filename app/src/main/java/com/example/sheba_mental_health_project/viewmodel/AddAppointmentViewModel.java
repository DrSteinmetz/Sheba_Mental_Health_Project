package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Patient;
import com.example.sheba_mental_health_project.repository.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddAppointmentViewModel extends ViewModel {

    private Repository mRepository;

    private MutableLiveData<List<Patient>> mGetAllPatientsSucceed;
    private MutableLiveData<String> mGetAllPatientsFailed;

    private final List<String> patientsEmails = new ArrayList<>();

    private long mChosenDate;
    private int mHourOfDay;
    private int mMinutes;

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
                if (!patientsEmails.isEmpty()) {
                    patientsEmails.clear();
                }
                for (Patient patient : patients) {
                    patientsEmails.add(patient.getEmail());
                }
                mGetAllPatientsSucceed.setValue(patients);
            }

            @Override
            public void onGetAllPatientsFailed(String error) {
                mGetAllPatientsFailed.setValue(error);
            }
        });
    }


    public List<String> getPatientsEmails() {
        return patientsEmails;
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

    private void calculateDate() {
        final Date chosenDate = new Date(mChosenDate);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(chosenDate);
        calendar.set(Calendar.HOUR_OF_DAY, mHourOfDay);
        calendar.set(Calendar.MINUTE, mMinutes);
        chosenDate.setTime(calendar.getTimeInMillis());
    }

    public void getAllPatients() {
        mRepository.getAllPatients();
    }
}
