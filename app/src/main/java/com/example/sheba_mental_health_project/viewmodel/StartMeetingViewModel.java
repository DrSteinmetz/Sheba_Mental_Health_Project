package com.example.sheba_mental_health_project.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;
import com.example.sheba_mental_health_project.repository.Repository;

public class StartMeetingViewModel extends ViewModel {

    final private Repository mRepository;

    final private Appointment mAppointment;

    private MutableLiveData<Appointment> mGetLastAppointmentSucceed;
    private MutableLiveData<String> mGetLastAppointmentFailed;

    private MutableLiveData<AppointmentStateEnum> mGetUpdateStateSucceed;
    private MutableLiveData<String> mGetUpdateStateFailed;

    private MutableLiveData<String> mUpdateAppointmentSucceed;
    private MutableLiveData<String> mUpdateAppointmentFailed;

    private MutableLiveData<Appointment> mDeleteAppointmentSucceed;
    private MutableLiveData<String> mDeleteAppointmentFailed;

    public StartMeetingViewModel(final Context context) {
        mRepository = Repository.getInstance(context);
        mAppointment = new Appointment(mRepository.getCurrentAppointment());
    }

    public MutableLiveData<Appointment> getGetLastAppointmentSucceed() {
        if (mGetLastAppointmentSucceed == null) {
            mGetLastAppointmentSucceed = new MutableLiveData<>();
            attachSetGetLastAppointmentListener();
        }
        return mGetLastAppointmentSucceed;
    }

    public MutableLiveData<String> getGetLastAppointmentFailed() {
        if (mGetLastAppointmentFailed == null) {
            mGetLastAppointmentFailed = new MutableLiveData<>();
            attachSetGetLastAppointmentListener();
        }
        return mGetLastAppointmentFailed;
    }

    private void attachSetGetLastAppointmentListener() {
        mRepository.setGetLastAppointmentInterface(new Repository.RepositoryGetLastAppointmentInterface() {
            @Override
            public void onGetLastAppointmentSucceed(Appointment lastAppointment) {
                mGetLastAppointmentSucceed.setValue(lastAppointment);
            }

            @Override
            public void onGetLastAppointmentFailed(String error) {
                mGetLastAppointmentFailed.setValue(error);
            }
        });
    }

    public MutableLiveData<AppointmentStateEnum> getGetUpdateStateSucceed() {
        if (mGetUpdateStateSucceed == null) {
            mGetUpdateStateSucceed = new MutableLiveData<>();
            attachSetUpdateStateListener();
        }
        return mGetUpdateStateSucceed;
    }

    public MutableLiveData<String> getGetUpdateStateFailed() {
        if (mGetUpdateStateFailed == null) {
            mGetUpdateStateFailed = new MutableLiveData<>();
            attachSetUpdateStateListener();
        }
        return mGetUpdateStateFailed;
    }

    private void attachSetUpdateStateListener() {
        mRepository.setUpdateStateOfAppointmentInterface(new Repository.RepositoryUpdateStateOfAppointmentInterface() {
            @Override
            public void onUpdateStateOfAppointmentSucceed(AppointmentStateEnum appointmentState) {
                mGetUpdateStateSucceed.setValue(appointmentState);
            }

            @Override
            public void onUpdateAnswersOfAppointmentFailed(String error) {
                mGetUpdateStateFailed.setValue(error);
            }
        });
    }

    public MutableLiveData<String> getUpdateAppointmentSucceed() {
        if (mUpdateAppointmentSucceed == null) {
            mUpdateAppointmentSucceed = new MutableLiveData<>();
            attachSetUpdateAppointmentListener();
        }
        return mUpdateAppointmentSucceed;
    }

    public MutableLiveData<String> getUpdateAppointmentFailed() {
        if (mUpdateAppointmentFailed == null) {
            mUpdateAppointmentFailed = new MutableLiveData<>();
            attachSetUpdateAppointmentListener();
        }
        return mUpdateAppointmentFailed;
    }

    private void attachSetUpdateAppointmentListener() {
        mRepository.setUpdateAppointmentInterface(new Repository.RepositoryUpdateAppointmentInterface() {
            @Override
            public void onUpdateAppointmentSucceed(String appointmentId) {
                mUpdateAppointmentSucceed.setValue(appointmentId);
            }

            @Override
            public void onUpdateAppointmentFailed(String error) {
                mUpdateAppointmentFailed.setValue(error);
            }
        });
    }

    public MutableLiveData<Appointment> getDeleteAppointmentSucceed() {
        if (mDeleteAppointmentSucceed == null) {
            mDeleteAppointmentSucceed = new MutableLiveData<>();
            attachSetDeleteAppointmentListener();
        }
        return mDeleteAppointmentSucceed;
    }

    public MutableLiveData<String> getDeleteAppointmentFailed() {
        if (mDeleteAppointmentFailed == null) {
            mDeleteAppointmentFailed = new MutableLiveData<>();
            attachSetDeleteAppointmentListener();
        }
        return mDeleteAppointmentFailed;
    }

    private void attachSetDeleteAppointmentListener() {
        mRepository.setDeleteAppointmentInterface(new Repository.RepositoryDeleteAppointmentInterface() {
            @Override
            public void onDeleteAppointmentSucceed(Appointment appointment) {
                mDeleteAppointmentSucceed.setValue(appointment);
            }

            @Override
            public void onDeleteAppointmentFailed(String error) {
                mDeleteAppointmentFailed.setValue(error);
            }
        });
    }


    public Appointment getAppointment() {
        return mAppointment;
    }

    public void getLastMeeting() {
        mRepository.getLastAppointment();
    }

    public void updateState(AppointmentStateEnum stateEnum) {
        mRepository.updateAppointmentState(stateEnum);
    }

    public void addAppointment(final Appointment appointment) {
        mRepository.addAppointment(appointment);
    }

    public void updateAppointment() {
        mRepository.updateAppointment(mAppointment);
    }

    public void deleteAppointment() {
        mRepository.deleteAppointment();
    }
}
