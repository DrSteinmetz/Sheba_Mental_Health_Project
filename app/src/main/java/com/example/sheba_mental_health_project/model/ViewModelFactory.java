package com.example.sheba_mental_health_project.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.CenterOfMassViewModel;
import com.example.sheba_mental_health_project.viewmodel.GenitalsViewModel;
import com.example.sheba_mental_health_project.viewmodel.LegsViewModel;
import com.example.sheba_mental_health_project.viewmodel.RightArmViewModel;
import com.example.sheba_mental_health_project.viewmodel.CharacterViewModel;
import com.example.sheba_mental_health_project.viewmodel.HeadViewModel;
import com.example.sheba_mental_health_project.viewmodel.LeftArmViewModel;
import com.example.sheba_mental_health_project.viewmodel.MainPatientViewModel;
import com.example.sheba_mental_health_project.viewmodel.PatientLoginViewModel;
import com.example.sheba_mental_health_project.viewmodel.WelcomeViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Context mContext;

    private ViewModelEnum mViewModelEnum;

    public ViewModelFactory(final Context context, final ViewModelEnum viewModelEnum) {
        this.mContext = context;
        this.mViewModelEnum = viewModelEnum;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        T objectToReturn = null;

        switch (mViewModelEnum) {
            case Welcome:
                if (modelClass.isAssignableFrom(WelcomeViewModel.class)) {
                    objectToReturn = (T) new WelcomeViewModel(mContext);
                }
                break;
            case PatientLogin:
                if (modelClass.isAssignableFrom(PatientLoginViewModel.class)) {
                    objectToReturn = (T) new PatientLoginViewModel(mContext);
                }
                break;
            case MainPatient:
                if (modelClass.isAssignableFrom(MainPatientViewModel.class)) {
                    objectToReturn = (T) new MainPatientViewModel(mContext);
                }
                break;
            case Character:
                if (modelClass.isAssignableFrom(CharacterViewModel.class)) {
                    objectToReturn = (T) new CharacterViewModel(mContext);
                }
                break;
            case Head:
                if (modelClass.isAssignableFrom(HeadViewModel.class)) {
                    objectToReturn = (T) new HeadViewModel(mContext);
                }
                break;
            case CenterOfMass:
                if (modelClass.isAssignableFrom(CenterOfMassViewModel.class)) {
                    objectToReturn = (T) new CenterOfMassViewModel(mContext);
                }
                break;
            case RightArm:
                if (modelClass.isAssignableFrom(RightArmViewModel.class)) {
                    objectToReturn = (T) new RightArmViewModel(mContext);
                }
                break;
            case LeftArm:
                if (modelClass.isAssignableFrom(LeftArmViewModel.class)) {
                    objectToReturn = (T) new LeftArmViewModel(mContext);
                }
                break;
            case Genitals:
                if (modelClass.isAssignableFrom(GenitalsViewModel.class)) {
                    objectToReturn = (T) new GenitalsViewModel(mContext);
                }
                break;
            case Legs:
                if (modelClass.isAssignableFrom(LegsViewModel.class)) {
                    objectToReturn = (T) new LegsViewModel(mContext);
                }
                break;
        }

        return objectToReturn;
    }
}
