package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.Patient;
import com.example.sheba_mental_health_project.repository.Repository;

import java.util.List;

public class MainPatientViewModel extends ViewModel {

    private Repository mRepository;

    public MainPatientViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
    }


}
