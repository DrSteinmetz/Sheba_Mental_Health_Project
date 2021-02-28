package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;
import com.example.sheba_mental_health_project.repository.Repository;

public class AppointmentTherapistViewModel extends ViewModel {

    final private Repository mRepository;

    public AppointmentTherapistViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
    }

    public void updateState(AppointmentStateEnum stateEnum) {
        mRepository.updateAppointmentState(stateEnum);
    }

    public Appointment getCurrentAppointment() {
        return mRepository.getCurrentAppointment();
    }
}