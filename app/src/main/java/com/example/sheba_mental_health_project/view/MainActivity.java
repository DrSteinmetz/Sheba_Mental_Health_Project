package com.example.sheba_mental_health_project.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.Patient;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.view.character.CenterOfMassFragment;
import com.example.sheba_mental_health_project.view.character.CharacterFragment;
import com.example.sheba_mental_health_project.view.character.GenitalsFragment;
import com.example.sheba_mental_health_project.view.character.HeadFragment;
import com.example.sheba_mental_health_project.view.character.LeftArmFragment;
import com.example.sheba_mental_health_project.view.character.LegsFragment;
import com.example.sheba_mental_health_project.view.character.RightArmFragment;
import com.example.sheba_mental_health_project.view.patient.AppointmentLoungeFragment;
import com.example.sheba_mental_health_project.view.patient.AppointmentPatientFragment;
import com.example.sheba_mental_health_project.view.patient.BureaucracyFragment;
import com.example.sheba_mental_health_project.view.patient.Covid19QuestionsFragment;
import com.example.sheba_mental_health_project.view.patient.HabitsQuestionsFragment;
import com.example.sheba_mental_health_project.view.patient.MainPatientFragment;
import com.example.sheba_mental_health_project.view.patient.MentalPatientFragment;
import com.example.sheba_mental_health_project.view.patient.MentalQuestionsFragment;
import com.example.sheba_mental_health_project.view.patient.NotesFragment;
import com.example.sheba_mental_health_project.view.patient.PhysicalPatientFragment;
import com.example.sheba_mental_health_project.view.patient.PreMeetingCharacterFragment;
import com.example.sheba_mental_health_project.view.patient.PreQuestionsFragment;
import com.example.sheba_mental_health_project.view.patient.QuestionsWarningFragment;
import com.example.sheba_mental_health_project.view.patient.SanityCheckFragment;
import com.example.sheba_mental_health_project.view.patient.SocialQuestionsFragment;
import com.example.sheba_mental_health_project.view.patient.StatementFragment;
import com.example.sheba_mental_health_project.view.patient.TreatyFragment;
import com.example.sheba_mental_health_project.view.therapist.AddAppointmentFragment;
import com.example.sheba_mental_health_project.view.therapist.AddPatientFragment;
import com.example.sheba_mental_health_project.view.therapist.AppointmentTherapistFragment;
import com.example.sheba_mental_health_project.view.therapist.HistoryAppointmentFragment;
import com.example.sheba_mental_health_project.view.therapist.HistoryFragment;
import com.example.sheba_mental_health_project.view.therapist.InquiryFragment;
import com.example.sheba_mental_health_project.view.therapist.MainTherapistFragment;
import com.example.sheba_mental_health_project.view.therapist.SearchPatientFragment;
import com.example.sheba_mental_health_project.view.therapist.StartMeetingFragment;
import com.example.sheba_mental_health_project.view.therapist.TherapistMentalStateFragment;
import com.example.sheba_mental_health_project.view.therapist.TherapistPhysicalStateFragment;
import com.example.sheba_mental_health_project.viewmodel.MainActivityViewModel;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener,
        MainTherapistFragment.MainTherapistInterface,
        PreQuestionsFragment.PreQuestionsFragmentInterface,
        TreatyFragment.TreatyFragmentInterface,
        BureaucracyFragment.BureaucracyFragmentInterface,
        Covid19QuestionsFragment.Covid19QuestionsFragmentInterface,
        SanityCheckFragment.SanityCheckFragmentInterface,
        QuestionsWarningFragment.QuestionsWarningFragmentInterface,
        StatementFragment.StatementFragmentInterface,
        SocialQuestionsFragment.SocialQuestionsFragmentInterface,
        HabitsQuestionsFragment.HabitsQuestionsFragmentInterface,
        MentalQuestionsFragment.MentalQuestionsFragmentInterface,
        MainPatientFragment.MainPatientInterface,
        CharacterFragment.CharacterFragmentInterface,
        StartMeetingFragment.StartMeetingTherapistInterface,
        AppointmentTherapistFragment.AppointmentTherapistInterface,
        SearchPatientFragment.SearchPatientFragmentInterface,
        HistoryFragment.HistoryFragmentInterface,
        PhysicalPatientFragment.PhysicalPatientFragmentInterface,
        AppointmentPatientFragment.AppointmentPatientInterface,
        MentalPatientFragment.MentalPatientFragmentInterface,
        PreMeetingCharacterFragment.PreMeetingCharacterInterface,
        AppointmentLoungeFragment.AppointmentLoungeFragmentInterface
{

    private MainActivityViewModel mViewModel;

    private DrawerLayout mDrawerLayout;

    private CoordinatorLayout mCoordinatorLayout;
    private NavigationView mNavigationView;

    private final String MAIN_THERAPIST_FRAG = "Main_Therapist_Fragment";
    private final String ADD_PATIENT_FRAG = "Add_Patient_Fragment";
    private final String SEARCH_PATIENT_FRAG = "Search_Patient_Fragment";
    private final String ADD_APPOINTMENT_FRAG = "Add_Appointment_Fragment";
    private final String START_MEETING_FRAG = "Start_Meeting_Fragment";
    private final String APPOINTMENT_THERAPIST_FRAG = "Appointment_Therapist_Fragment";
    private final String THERAPIST_MENTAL_STATE_FRAG = "Therapist_Mental_State_Fragment";
    private final String THERAPIST_PHYSICAL_STATE_FRAG = "Therapist_Physical_State_Fragment";
    private final String INQUIRY_FRAG = "Inquiry_Fragment";
    private final String HISTORY_FRAG = "History_Fragment";
    private final String HISTORY_APPOINTMENT_FRAG = "History_Appointment_Fragment";

    private final String PRE_QUESTIONS_FRAG = "Pre_Questions_Fragment";
    private final String TREATY_FRAG = "Treaty_Fragment";
    private final String BUREAUCRACY_FRAG = "Bureaucracy_Fragment";
    private final String COVID19_QUESTIONS_FRAG = "Covid-19_Questions_Fragment";
    private final String SANITY_CHECK_FRAG = "Sanity_Check_Fragment";
    private final String QUESTIONS_WARNING_FRAG = "Questions_Warning_Fragment";
    private final String STATEMENT_FRAG = "Statement_Fragment";
    private final String SOCIAL_QUESTIONS_FRAG = "Social_Questions_Fragment";
    private final String HABITS_QUESTIONS_FRAG = "Habits_Questions_Fragment";
    private final String MENTAL_QUESTIONS_FRAG = "Mental_Questions_Fragment";
    private final String CATEGORY_FRAG = "Category_Fragment";
    private final String PRE_MEETING_CHARACTER_FRAG = "Pre_Meeting_Character_Frag";
    private final String NOTES_FRAG = "Notes_Fragment";
    private final String MAIN_PATIENT_FRAG = "Main_Patient_Fragment";
    private final String PATIENT_APPOINTMENT_FRAG = "Patient_Appointment_Fragment";
    private final String PHYSICAL_PATIENT_FRAG = "Physical_Patient_Fragment";
    private final String MENTAL_PATIENT_FRAG = "Mental_Patient_Fragment";
    private final String APPOINTMENT_LOUNGE_FRAG = "Appointment_Lounge_Fragment";

    private final String CHAT_FRAG = "Chat_Fragment";

    private final String CHARACTER_FRAG = "Character_Fragment";
    private final String HEAD_FRAG = "Head_Fragment";
    private final String CENTER_OF_MASS_FRAG = "Center_Of_Mass_Fragment";
    private final String LEFT_ARM_FRAG = "Left_Arm_Fragment";
    private final String RIGHT_ARM_FRAG = "Right_Arm_Fragment";
    private final String GENITALS_FRAG = "Genitals_Fragment";
    private final String LEGS_FRAG = "Legs_Fragment";

    private final String IS_THERAPIST = "is_therapist";

    private final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(this,
                ViewModelEnum.MainActivity)).get(MainActivityViewModel.class);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mCoordinatorLayout = findViewById(R.id.coordinator_layout);
        mNavigationView = findViewById(R.id.navigation_view);

        /*getSupportFragmentManager().beginTransaction()
                //TODO: add enter and exit animations
                .add(R.id.container, WelcomeFragment.newInstance(), WELCOME_FRAG)
                .addToBackStack(null)
                .commit();*/

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_round_menu_24);

        mDrawerLayout.addDrawerListener(this);
        final SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        mViewModel.setIsTherapist(sharedPreferences.getBoolean(IS_THERAPIST, false));

        mNavigationView.getMenu().clear();
        mNavigationView.inflateMenu(mViewModel.isTherapist() ?
                R.menu.therapist_drawer_menu : R.menu.patient_drawer_menu);
        mNavigationView.setNavigationItemSelectedListener(this);

        if (mViewModel.isTherapist()) {
            getSupportFragmentManager().beginTransaction()
                    //TODO: add enter and exit animations
                    .replace(R.id.container, MainTherapistFragment.newInstance(), MAIN_THERAPIST_FRAG)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    //TODO: add enter and exit animations
                    .replace(R.id.container, MainPatientFragment.newInstance(), MAIN_PATIENT_FRAG)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        mNavigationView.getMenu().findItem(R.id.inquiry_action).setVisible(isInquiryShown());
        mNavigationView.invalidate();
    }

    public boolean isInquiryShown() {

        boolean isInAppointment = false;
        boolean isAppointmentBegun = false;
        boolean isFinishedPreQuestions = false;

        final Fragment mainTherapistFrag = getSupportFragmentManager()
                .findFragmentByTag(MAIN_THERAPIST_FRAG);
        final Fragment mainPatientFrag = getSupportFragmentManager()
                .findFragmentByTag(MAIN_PATIENT_FRAG);

        if (mViewModel.isTherapist()) {
            if (mainTherapistFrag != null) {
                isInAppointment = !mainTherapistFrag.isVisible();
            }
        } else {
            if (mainPatientFrag != null) {
                isInAppointment = !mainPatientFrag.isVisible();
            }
        }

        if (mViewModel.getCurrentAppointment() != null) {
            isAppointmentBegun = mViewModel.getCurrentAppointment()
                    .getState().equals(AppointmentStateEnum.Ongoing);

            isFinishedPreQuestions = mViewModel.getCurrentAppointment().getIsFinishedPreQuestions();
        }

        return isInAppointment && isAppointmentBegun && isFinishedPreQuestions;
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search_patient_action:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, SearchPatientFragment.newInstance(),
                                SEARCH_PATIENT_FRAG)
                        .addToBackStack(null)
                        .commit();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.add_patient_action:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, AddPatientFragment.newInstance(),
                                ADD_PATIENT_FRAG)
                        .addToBackStack(null)
                        .commit();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.inquiry_action:
                onMoveToPreQuestions();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.settings_action:
                getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, SettingsFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.logout_action:
                if (mViewModel != null) {
                    mViewModel.logout();
                }
                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
                mDrawerLayout.closeDrawers();
                finish();
                break;
        }

        return true;
    }


    /**<------ Patient Pre-Questions ------>*/
    @Override
    public void onMoveToPreQuestions() {
        getSupportFragmentManager().beginTransaction()
                //TODO: add enter and exit animations
                .replace(R.id.container, PreQuestionsFragment.newInstance(), PRE_QUESTIONS_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onEnterAppointment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, AppointmentPatientFragment
                        .newInstance(mViewModel.getCurrentAppointment()), PATIENT_APPOINTMENT_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onMoveToLounge() {
        onMoveToAppointmentLounge();
    }

    @Override
    public void onContinueFromPreQuestions() {
        getSupportFragmentManager().beginTransaction()
                //TODO: add enter and exit animations
                .replace(R.id.container, TreatyFragment.newInstance(), TREATY_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onContinueFromTreaty() {
        getSupportFragmentManager().beginTransaction()
                //TODO: add enter and exit animations
                .replace(R.id.container, BureaucracyFragment.newInstance(), BUREAUCRACY_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onContinueFromBureaucracy() {
        getSupportFragmentManager().beginTransaction()
                //TODO: add enter and exit animations
                .replace(R.id.container, Covid19QuestionsFragment.newInstance(), COVID19_QUESTIONS_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onContinueFromCovid19Questions() {
        getSupportFragmentManager().beginTransaction()
                //TODO: add enter and exit animations
                .replace(R.id.container, SanityCheckFragment.newInstance(), SANITY_CHECK_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onContinueFromSanityCheck() {
        getSupportFragmentManager().beginTransaction()
                //TODO: add enter and exit animations
                .replace(R.id.container, QuestionsWarningFragment.newInstance(), QUESTIONS_WARNING_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onContinueFromQuestionsWarning() {
        getSupportFragmentManager().beginTransaction()
                //TODO: add enter and exit animations
                .replace(R.id.container, StatementFragment.newInstance(), STATEMENT_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onContinueFromStatement() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, SocialQuestionsFragment.newInstance(), SOCIAL_QUESTIONS_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onContinueFromSocialQuestions() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, HabitsQuestionsFragment.newInstance(), HABITS_QUESTIONS_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onContinueFromHabitsQuestions() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MentalQuestionsFragment.newInstance(), MENTAL_QUESTIONS_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onContinueFromMentalQuestions() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, PreMeetingCharacterFragment.newInstance(), PRE_MEETING_CHARACTER_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onTherapistStartMeetingClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, AppointmentTherapistFragment.newInstance(), APPOINTMENT_THERAPIST_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onLeaveNoteClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, NotesFragment.newInstance(), NOTES_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onMoveToAppointmentPatient() {
        onEnterAppointment();
    }

    @Override
    public void onMoveToAppointmentLounge() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, AppointmentLoungeFragment.newInstance(), APPOINTMENT_LOUNGE_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackToAppointmentsBtnClicked() {
        getSupportFragmentManager().popBackStack(null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MainPatientFragment.newInstance(), MAIN_PATIENT_FRAG)
                .commit();
    }

    @Override
    public void onEditAnswersBtnClicked() {
        onBackToAppointmentsBtnClicked();
        onMoveToPreQuestions();
    }

    /**<------ Character ------>*/
    @Override
    public void onHeadClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, HeadFragment.newInstance(), HEAD_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCenterOfMassClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, CenterOfMassFragment.newInstance(), CENTER_OF_MASS_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onRightArmClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, LeftArmFragment.newInstance(), LEFT_ARM_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onLeftArmClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, RightArmFragment.newInstance(), RIGHT_ARM_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onGenitalsClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, GenitalsFragment.newInstance(), GENITALS_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onLegsClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, LegsFragment.newInstance(), LEGS_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onChatClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, ChatFragment.newInstance(), CHAT_FRAG)
                .addToBackStack(null)
                .commit();
    }

    /**<------ Appointment Therapist ------>*/
    @Override
    public void onTherapistAppointmentClicked() {
        getSupportFragmentManager().beginTransaction()
                //TODO: add enter and exit animations
                .replace(R.id.container, StartMeetingFragment.newInstance(), START_MEETING_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAddAppointClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, AddAppointmentFragment.newInstance(), ADD_APPOINTMENT_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onMentalStateClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, TherapistMentalStateFragment.newInstance(), THERAPIST_MENTAL_STATE_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onPhysicalStateClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, TherapistPhysicalStateFragment.newInstance(), THERAPIST_PHYSICAL_STATE_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onInquiryClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, InquiryFragment.newInstance(), INQUIRY_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onPatientHistoryClicked(Patient patient) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, HistoryFragment.newInstance(patient), HISTORY_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onHistoryAppointmentClicked(final Appointment appointment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, HistoryAppointmentFragment.newInstance(appointment), HISTORY_APPOINTMENT_FRAG)
                .addToBackStack(null)
                .commit();
    }

    /**<------ Physical Patient ------>*/
    @Override
    public void onHomeBtnClicked() {
        onBackPressed();
    }

    /**<------ MentalPatient ------>*/
    @Override
    public void onSaveFeelings() {
        onBackPressed();
    }

    /**<------ Appointment Patient ------>*/
    @Override
    public void onPhysicalClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PhysicalPatientFragment.newInstance(), PHYSICAL_PATIENT_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onMentalClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, MentalPatientFragment.newInstance(), MENTAL_PATIENT_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        final Fragment headFragment = getSupportFragmentManager().findFragmentByTag(HEAD_FRAG);
        final Fragment centerOfMassFragment = getSupportFragmentManager().findFragmentByTag(CENTER_OF_MASS_FRAG);
        final Fragment leftArmFragment = getSupportFragmentManager().findFragmentByTag(LEFT_ARM_FRAG);
        final Fragment rightArmFragment = getSupportFragmentManager().findFragmentByTag(RIGHT_ARM_FRAG);
        final Fragment genitalsFragment = getSupportFragmentManager().findFragmentByTag(GENITALS_FRAG);
        final Fragment legsFragment = getSupportFragmentManager().findFragmentByTag(LEGS_FRAG);

        if (headFragment != null && headFragment.isVisible() &&
                headFragment.getChildFragmentManager().getBackStackEntryCount() > 1) {
            headFragment.getChildFragmentManager().popBackStackImmediate();
        } else if (centerOfMassFragment != null && centerOfMassFragment.isVisible() &&
                centerOfMassFragment.getChildFragmentManager().getBackStackEntryCount() > 1) {
            centerOfMassFragment.getChildFragmentManager().popBackStackImmediate();
        } else if (leftArmFragment != null && leftArmFragment.isVisible() &&
                leftArmFragment.getChildFragmentManager().getBackStackEntryCount() > 1) {
            leftArmFragment.getChildFragmentManager().popBackStackImmediate();
        } else if (rightArmFragment != null && rightArmFragment.isVisible() &&
                rightArmFragment.getChildFragmentManager().getBackStackEntryCount() > 1) {
            rightArmFragment.getChildFragmentManager().popBackStackImmediate();
        } else if (genitalsFragment != null && genitalsFragment.isVisible() &&
                genitalsFragment.getChildFragmentManager().getBackStackEntryCount() > 1) {
            genitalsFragment.getChildFragmentManager().popBackStackImmediate();
        } else if (legsFragment != null && legsFragment.isVisible() &&
                legsFragment.getChildFragmentManager().getBackStackEntryCount() > 1) {
            legsFragment.getChildFragmentManager().popBackStackImmediate();
        }
        else {
            super.onBackPressed();
        }
    }


}
