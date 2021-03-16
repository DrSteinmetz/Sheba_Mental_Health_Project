package com.example.sheba_mental_health_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.User;
import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;
import com.example.sheba_mental_health_project.repository.AuthRepository;
import com.example.sheba_mental_health_project.repository.Repository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainPatientViewModel extends ViewModel {

    private final Repository mRepository;

    private final AuthRepository mAuthRepository;

    private final List<Appointment> mAppointments = new ArrayList<>();

    private MutableLiveData<List<Appointment>> mGetMyAppointmentsSucceed;
    private MutableLiveData<String> mGetMyAppointmentsFailed;

    private MutableLiveData<Void> mPatientLoginSucceed;
    private  MutableLiveData<String> mPatientLoginFailed;

    private final String TAG = "MainPatientViewModel";


    public MainPatientViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
        mAuthRepository = AuthRepository.getInstance(context);
    }

    public MutableLiveData<List<Appointment>> getMyAppointmentsSucceed() {
        if (mGetMyAppointmentsSucceed == null) {
            mGetMyAppointmentsSucceed = new MutableLiveData<>();
            attachGetMyAppointmentsListener();
        }
        return mGetMyAppointmentsSucceed;
    }

    public MutableLiveData<String> getMyAppointmentsFailed() {
        if (mGetMyAppointmentsFailed == null) {
            mGetMyAppointmentsFailed = new MutableLiveData<>();
            attachGetMyAppointmentsListener();
        }
        return mGetMyAppointmentsFailed;
    }

    public void attachGetMyAppointmentsListener() {
        mRepository.setGetAppointmentOfSpecificPatient(new Repository.RepositoryGetAppointmentOfSpecificPatientInterface() {
            @Override
            public void onGetAppointmentOfSpecificPatientSucceed(List<Appointment> appointments) {
                mAppointments.clear();
                mAppointments.addAll(appointments);
                mGetMyAppointmentsSucceed.setValue(appointments);
            }

            @Override
            public void onGetAppointmentOfSpecificPatientFailed(String error) {
                mGetMyAppointmentsFailed.setValue(error);
            }
        });
    }

    public MutableLiveData<Void> getPatientLoginSucceed() {
        if (mPatientLoginSucceed == null) {
            mPatientLoginSucceed = new MutableLiveData<>();
            attachSetOnPatientLoginListener();
        }
        return mPatientLoginSucceed;
    }

    public MutableLiveData<String> getPatientLoginFailed() {
        if (mPatientLoginFailed == null) {
            mPatientLoginFailed = new MutableLiveData<>();
            attachSetOnPatientLoginListener();
        }
        return mPatientLoginFailed;
    }

    private void attachSetOnPatientLoginListener() {
        mAuthRepository.setLoginPatientListener(new AuthRepository.AuthRepoLoginPatientInterface() {
            @Override
            public void onPatientLoginSucceed() {
                mPatientLoginSucceed.setValue(null);
            }

            @Override
            public void onPatientLoginFailed(String message) {
                mPatientLoginFailed.setValue(message);
            }
        });
    }


    public final List<Appointment> getAppointments() {
        return mAppointments;
    }

    public void getMyAppointments() {
        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final List<AppointmentStateEnum> stateQuery = new ArrayList<>();
        stateQuery.add(AppointmentStateEnum.PreMeeting);
        stateQuery.add(AppointmentStateEnum.OnGoing);

        mRepository.getAppointmentsOfSpecificPatient(id, stateQuery);
    }

    public void setCurrentAppointment(final Appointment appointment) {
        mRepository.setCurrentAppointment(appointment);
    }

    public void removePatientAppointmentsListener() {
        mRepository.removePatientAppointmentsListener();
    }

    public User getAuthUser() {
        return mAuthRepository.getUser();
    }

    public void getPatientForLogin() {
        mAuthRepository.getPatientForLogin(mAuthRepository.getFirebaseUserId());
    }
}
