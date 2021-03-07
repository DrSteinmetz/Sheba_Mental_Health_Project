package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.repository.Repository;

public class TherapistPhysicalStateViewModel extends ViewModel {

    private final Repository mRepository;

    private final String TAG = "TherapistPhysicStateVM";


    public TherapistPhysicalStateViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
    }


    public final Appointment getCurrentAppointment() {
        return mRepository.getCurrentAppointment();
    }
}
