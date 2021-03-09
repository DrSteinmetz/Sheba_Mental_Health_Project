package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.repository.Repository;

public class PreQuestionsViewModel extends ViewModel {

    private final Repository mRepository;

    private final String TAG = "PreQuestionsViewModel";


    public PreQuestionsViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
    }


    public String getPatientFirstName() {
        return mRepository.getCurrentAppointment().getPatient().getFirstName();
    }

    public String getTherapistFullName() {
        return mRepository.getCurrentAppointment().getTherapist().getFullName();
    }
}
