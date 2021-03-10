package com.example.sheba_mental_health_project.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.InquiryViewModel;
import com.example.sheba_mental_health_project.viewmodel.MentalQuestionsViewModel;
import com.example.sheba_mental_health_project.viewmodel.HabitsQuestionsViewModel;
import com.example.sheba_mental_health_project.viewmodel.SocialQuestionsViewModel;
import com.example.sheba_mental_health_project.viewmodel.HistoryViewModel;
import com.example.sheba_mental_health_project.viewmodel.Covid19QuestionsViewModel;
import com.example.sheba_mental_health_project.viewmodel.TherapistPhysicalStateViewModel;
import com.example.sheba_mental_health_project.viewmodel.AppointmentLoungeViewModel;
import com.example.sheba_mental_health_project.viewmodel.AppointmentPatientViewModel;
import com.example.sheba_mental_health_project.viewmodel.AppointmentTherapistViewModel;
import com.example.sheba_mental_health_project.viewmodel.ChatViewModel;
import com.example.sheba_mental_health_project.viewmodel.MentalPatientViewModel;
import com.example.sheba_mental_health_project.viewmodel.PhysicalPatientViewModel;
import com.example.sheba_mental_health_project.viewmodel.PreMeetingCharacterViewModel;
import com.example.sheba_mental_health_project.viewmodel.SanityCheckViewModel;
import com.example.sheba_mental_health_project.viewmodel.BureaucracyViewModel;
import com.example.sheba_mental_health_project.viewmodel.PreQuestionsViewModel;
import com.example.sheba_mental_health_project.viewmodel.SearchPatientViewModel;
import com.example.sheba_mental_health_project.viewmodel.StartMeetingViewModel;
import com.example.sheba_mental_health_project.viewmodel.StatementViewModel;
import com.example.sheba_mental_health_project.viewmodel.TherapistMentalGenericViewModel;
import com.example.sheba_mental_health_project.viewmodel.TherapistMentalStateViewModel;
import com.example.sheba_mental_health_project.viewmodel.TherapistPhysicalStateGenericViewModel;
import com.example.sheba_mental_health_project.viewmodel.TreatyViewModel;
import com.example.sheba_mental_health_project.viewmodel.AddAppointmentViewModel;
import com.example.sheba_mental_health_project.viewmodel.AddPatientViewModel;
import com.example.sheba_mental_health_project.viewmodel.MainActivityViewModel;
import com.example.sheba_mental_health_project.viewmodel.MainTherapistViewModel;
import com.example.sheba_mental_health_project.viewmodel.TherapistLoginViewModel;
import com.example.sheba_mental_health_project.viewmodel.CenterOfMassViewModel;
import com.example.sheba_mental_health_project.viewmodel.GenitalsViewModel;
import com.example.sheba_mental_health_project.viewmodel.LegsViewModel;
import com.example.sheba_mental_health_project.viewmodel.LeftArmViewModel;
import com.example.sheba_mental_health_project.viewmodel.CharacterViewModel;
import com.example.sheba_mental_health_project.viewmodel.HeadViewModel;
import com.example.sheba_mental_health_project.viewmodel.RightArmViewModel;
import com.example.sheba_mental_health_project.viewmodel.MainPatientViewModel;
import com.example.sheba_mental_health_project.viewmodel.PatientLoginViewModel;
import com.example.sheba_mental_health_project.viewmodel.WelcomeViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Context mContext;

    private final ViewModelEnum mViewModelEnum;

    public ViewModelFactory(final Context context, final ViewModelEnum viewModelEnum) {
        this.mContext = context;
        this.mViewModelEnum = viewModelEnum;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        T objectToReturn = null;

        switch (mViewModelEnum) {
            case MainActivity:
                if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
                    objectToReturn = (T) new MainActivityViewModel(mContext);
                }
                break;
            case Welcome:
                if (modelClass.isAssignableFrom(WelcomeViewModel.class)) {
                    objectToReturn = (T) new WelcomeViewModel(mContext);
                }
                break;
            case TherapistLogin:
                if (modelClass.isAssignableFrom(TherapistLoginViewModel.class)) {
                    objectToReturn = (T) new TherapistLoginViewModel(mContext);
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
            case PreMeetingCharacter:
                if (modelClass.isAssignableFrom(PreMeetingCharacterViewModel.class)) {
                    objectToReturn = (T) new PreMeetingCharacterViewModel(mContext);
                }
                break;
            case AppointmentLounge:
                if (modelClass.isAssignableFrom(AppointmentLoungeViewModel.class)) {
                    objectToReturn = (T) new AppointmentLoungeViewModel(mContext);
                }
                break;
            case AppointmentPatient:
                if (modelClass.isAssignableFrom(AppointmentPatientViewModel.class)) {
                    objectToReturn = (T) new AppointmentPatientViewModel(mContext);
                }
                break;
            case PhysicalPatient:
                if (modelClass.isAssignableFrom(PhysicalPatientViewModel.class)) {
                    objectToReturn = (T) new PhysicalPatientViewModel(mContext);
                }
                break;
            case MentalPatient:
                if (modelClass.isAssignableFrom(MentalPatientViewModel.class)) {
                    objectToReturn = (T) new MentalPatientViewModel(mContext);
                }
                break;
            case Chat:
                if (modelClass.isAssignableFrom(ChatViewModel.class)) {
                    objectToReturn = (T) new ChatViewModel(mContext);
                }
                break;
            case PreQuestions:
                if (modelClass.isAssignableFrom(PreQuestionsViewModel.class)) {
                    objectToReturn = (T) new PreQuestionsViewModel(mContext);
                }
                break;
            case Treaty:
                if (modelClass.isAssignableFrom(TreatyViewModel.class)) {
                    objectToReturn = (T) new TreatyViewModel(mContext);
                }
                break;
            case Bureaucracy:
                if (modelClass.isAssignableFrom(BureaucracyViewModel.class)) {
                    objectToReturn = (T) new BureaucracyViewModel(mContext);
                }
                break;
            case CovidQuestions:
                if (modelClass.isAssignableFrom(Covid19QuestionsViewModel.class)) {
                    objectToReturn = (T) new Covid19QuestionsViewModel(mContext);
                }
                break;
            case SanityCheck:
                if (modelClass.isAssignableFrom(SanityCheckViewModel.class)) {
                    objectToReturn = (T) new SanityCheckViewModel(mContext);
                }
                break;
            case Statement:
                if (modelClass.isAssignableFrom(StatementViewModel.class)) {
                    objectToReturn = (T) new StatementViewModel(mContext);
                }
                break;
            case SocialQuestions:
                if (modelClass.isAssignableFrom(SocialQuestionsViewModel.class)) {
                    objectToReturn = (T) new SocialQuestionsViewModel(mContext);
                }
                break;
            case HabitsQuestions:
                if (modelClass.isAssignableFrom(HabitsQuestionsViewModel.class)) {
                    objectToReturn = (T) new HabitsQuestionsViewModel(mContext);
                }
                break;
            case MentalQuestions:
                if (modelClass.isAssignableFrom(MentalQuestionsViewModel.class)) {
                    objectToReturn = (T) new MentalQuestionsViewModel(mContext);
                }
                break;
            case MainTherapist:
                if (modelClass.isAssignableFrom(MainTherapistViewModel.class)) {
                    objectToReturn = (T) new MainTherapistViewModel(mContext);
                }
                break;
            case TherapistPhysicalStateGeneric:
                if (modelClass.isAssignableFrom(TherapistPhysicalStateGenericViewModel.class)) {
                    objectToReturn = (T) new TherapistPhysicalStateGenericViewModel(mContext);
                }
                break;
            case TherapistPhysicalState:
                if (modelClass.isAssignableFrom(TherapistPhysicalStateViewModel.class)) {
                    objectToReturn = (T) new TherapistPhysicalStateViewModel(mContext);
                }
                break;
            case TherapistMentalState:
                if (modelClass.isAssignableFrom(TherapistMentalStateViewModel.class)) {
                    objectToReturn = (T) new TherapistMentalStateViewModel(mContext);
                }
                break;
            case TherapistMentalGeneric:
                if (modelClass.isAssignableFrom(TherapistMentalGenericViewModel.class)) {
                    objectToReturn = (T) new TherapistMentalGenericViewModel(mContext);
                }
                break;
            case Inquiry:
                if (modelClass.isAssignableFrom(InquiryViewModel.class)) {
                    objectToReturn = (T) new InquiryViewModel(mContext);
                }
                break;
            case StartMeeting:
                if (modelClass.isAssignableFrom(StartMeetingViewModel.class)) {
                    objectToReturn = (T) new StartMeetingViewModel(mContext);
                }
                break;
            case AppointmentTherapist:
                if (modelClass.isAssignableFrom(AppointmentTherapistViewModel.class)) {
                    objectToReturn = (T) new AppointmentTherapistViewModel(mContext);
                }
                break;
            case SearchPatient:
                if (modelClass.isAssignableFrom(SearchPatientViewModel.class)) {
                    objectToReturn = (T) new SearchPatientViewModel(mContext);
                }
                break;
            case History:
                if (modelClass.isAssignableFrom(HistoryViewModel.class)) {
                    objectToReturn = (T) new HistoryViewModel(mContext);
                }
                break;
            case AddPatient:
                if (modelClass.isAssignableFrom(AddPatientViewModel.class)) {
                    objectToReturn = (T) new AddPatientViewModel(mContext);
                }
                break;
            case AddAppointment:
                if (modelClass.isAssignableFrom(AddAppointmentViewModel.class)) {
                    objectToReturn = (T) new AddAppointmentViewModel(mContext);
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
            case LeftArm:
                if (modelClass.isAssignableFrom(LeftArmViewModel.class)) {
                    objectToReturn = (T) new LeftArmViewModel(mContext);
                }
                break;
            case RightArm:
                if (modelClass.isAssignableFrom(RightArmViewModel.class)) {
                    objectToReturn = (T) new RightArmViewModel(mContext);
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
