package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.repository.Repository;

public class AppointmentLoungeViewModel extends ViewModel {

    private final Repository mRepository;

    private final String TAG = "AppointmentLoungeViewModel";


    public AppointmentLoungeViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
    }

    public Appointment getCurrentAppointment() {
        return mRepository.getCurrentAppointment();
    }
}