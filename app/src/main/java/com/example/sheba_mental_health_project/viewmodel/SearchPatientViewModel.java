package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Patient;
import com.example.sheba_mental_health_project.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SearchPatientViewModel extends ViewModel {

    private MutableLiveData<List<Patient>> mGetAllPatientsSucceed;
    private MutableLiveData<String> mGetAllPatientsFailed;
    private final List<String> mPatientsEmails = new ArrayList<>();


    private Repository mRepository;

    public SearchPatientViewModel(final Context context) {
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
                if (!mPatientsEmails.isEmpty()) {
                    mPatientsEmails.clear();
                }
                for (Patient patient : patients) {
                    mPatientsEmails.add(patient.getEmail());
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
        return mPatientsEmails;
    }

    public void getAllPatients() {
        mRepository.getAllPatients();
    }

    public Patient getPatientByEmail(final String patientEmail){

        final int patientIndex = mPatientsEmails.indexOf(patientEmail);
        if( patientIndex != -1){
            return Objects.requireNonNull(mGetAllPatientsSucceed.getValue()).get(patientIndex);
        }
       else{
            return null;
        }
    }

}