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

    private final Repository mRepository;

    private Patient mPatient;

    private final List<String> mPatientsNames = new ArrayList<>();

    private MutableLiveData<List<Patient>> mGetPatientsOfSpecificTherapistSucceed;
    private MutableLiveData<String> mGetPatientsOfSpecificTherapistFailed;

    private final String TAG = "SearchPatientViewModel";

    public SearchPatientViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
    }

    public MutableLiveData<List<Patient>> getGetPatientsOfSpecificTherapistSucceed() {
        if (mGetPatientsOfSpecificTherapistSucceed == null) {
            mGetPatientsOfSpecificTherapistSucceed = new MutableLiveData<>();
            attachSetGetAllPatientsListener();
        }
        return mGetPatientsOfSpecificTherapistSucceed;
    }

    public MutableLiveData<String> getGetPatientsOfSpecificTherapistFailed() {
        if (mGetPatientsOfSpecificTherapistFailed == null) {
            mGetPatientsOfSpecificTherapistFailed = new MutableLiveData<>();
            attachSetGetAllPatientsListener();
        }
        return mGetPatientsOfSpecificTherapistFailed;
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

                mGetPatientsOfSpecificTherapistSucceed.setValue(patients);
            }

            @Override
            public void onGetAllPatientsFailed(String error) {
                mGetPatientsOfSpecificTherapistFailed.setValue(error);
            }
        });
    }


    public Patient getPatient() {
        return mPatient;
    }

    public void setPatient(Patient mPatient) {
        this.mPatient = mPatient;
    }

    public List<String> getPatientsNames() {
        return mPatientsNames;
    }

    public void getAllMyPatients() {
        mRepository.getPatientsOfSpecificTherapist();
    }

    /*public Patient getPatientByEmail(final String patientEmail){
        final int patientIndex = mPatientsNames.indexOf(patientEmail);

        if (patientIndex != -1) {
            return Objects.requireNonNull(mGetPatientsOfSpecificTherapistSucceed.getValue()).get(patientIndex);
        } else {
            return null;
        }
    }*/
}