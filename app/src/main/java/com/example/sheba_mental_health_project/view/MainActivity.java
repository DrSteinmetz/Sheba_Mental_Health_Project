package com.example.sheba_mental_health_project.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.MainActivityViewModel;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener,
        MainTherapistFragment.MainTherapistInterface,
        PreQuestionsFragment.PreQuestionsFragmentInterface,
        TreatyFragment.TreatyFragmentInterface,
        BureaucracyFragment.BureaucracyFragmentInterface,
        SanityCheckFragment.SanityCheckFragmentInterface,
        StatementFragment.StatementFragmentInterface,
        MainPatientFragment.MainPatientInterface,
        CharacterFragment.CharacterFragmentInterface {

    private MainActivityViewModel mViewModel;

    private DrawerLayout mDrawerLayout;

    private CoordinatorLayout mCoordinatorLayout;
    private NavigationView mNavigationView;

    private final String MAIN_THERAPIST_FRAG = "Main_Therapist_Fragment";
    private final String ADD_PATIENT_FRAG = "Add_Patient_Fragment";
    private final String ADD_APPOINTMENT_FRAG = "Add_Appointment_Fragment";

    private final String PRE_QUESTIONS_FRAG = "Pre_Questions_Fragment";
    private final String TREATY_FRAG = "Treaty_Fragment";
    private final String BUREAUCRACY_FRAG = "Bureaucracy_Fragment";
    private final String SANITY_CHECK_FRAG = "Sanity_Check_Fragment";
    private final String STATEMENT_FRAG = "Statement_Fragment";
    private final String CATEGORY_FRAG = "Category_Fragment";
    private final String MAIN_PATIENT_FRAG = "Main_Patient_Fragment";

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
        actionBar.setHomeAsUpIndicator(R.drawable.ic_round_menu_white_24);

        mDrawerLayout.addDrawerListener(this);
        final SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        mViewModel.setIsTherapist(sharedPreferences.getBoolean(IS_THERAPIST, false));
//        final boolean isTherapist = getIntent().getBooleanExtra(IS_THERAPIST, false);
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
        final boolean isInAppointment = isInAppointment();

        mNavigationView.getMenu().findItem(R.id.chat_action).setVisible(isInAppointment);
        mNavigationView.invalidate();
    }

    public boolean isInAppointment() {
        boolean isInAppointment = true;
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

        return isInAppointment;
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
            case R.id.add_patient_action:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, AddPatientFragment.newInstance(), ADD_PATIENT_FRAG)
                        .addToBackStack(null)
                        .commit();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.chat_action:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, ChatFragment.newInstance(), CHAT_FRAG)
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

    /**<------ Appointments ------>*/
    @Override
    public void onTherapistAppointmentClicked() {
    }

    @Override
    public void onAddAppointClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, AddAppointmentFragment.newInstance(), ADD_APPOINTMENT_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onPatientAppointmentClicked() {
        getSupportFragmentManager().beginTransaction()
                //TODO: add enter and exit animations
                .replace(R.id.container, PreQuestionsFragment.newInstance(), PRE_QUESTIONS_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onContinueToTreaty() {
        getSupportFragmentManager().beginTransaction()
                //TODO: add enter and exit animations
                .replace(R.id.container, TreatyFragment.newInstance(), TREATY_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onContinueToBureaucracy() {
        getSupportFragmentManager().beginTransaction()
                //TODO: add enter and exit animations
                .replace(R.id.container, BureaucracyFragment.newInstance(), BUREAUCRACY_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onContinueToSanityCheck() {
        getSupportFragmentManager().beginTransaction()
                //TODO: add enter and exit animations
                .replace(R.id.container, SanityCheckFragment.newInstance(), SANITY_CHECK_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onContinueToStatement() {
        getSupportFragmentManager().beginTransaction()
                //TODO: add enter and exit animations
                .replace(R.id.container, StatementFragment.newInstance(), STATEMENT_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onContinueToCategoryQuestions() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, CharacterFragment.newInstance(), CHARACTER_FRAG)
                .addToBackStack(null)
                .commit();
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
                .add(R.id.container, LeftArmFragment.newInstance(), RIGHT_ARM_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onLeftArmClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, RightArmFragment.newInstance(), LEFT_ARM_FRAG)
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
    public void onBackPressed() {
        final Fragment leftArmFragment;
        final Fragment rightArmFragment;
        leftArmFragment = getSupportFragmentManager().findFragmentByTag(LEFT_ARM_FRAG);
        rightArmFragment = getSupportFragmentManager().findFragmentByTag(RIGHT_ARM_FRAG);

        if (leftArmFragment != null && leftArmFragment.isVisible() &&
                leftArmFragment.getChildFragmentManager().getBackStackEntryCount() > 1) {
            leftArmFragment.getChildFragmentManager().popBackStackImmediate();
        }
        else if (rightArmFragment != null && rightArmFragment.isVisible() &&
                rightArmFragment.getChildFragmentManager().getBackStackEntryCount() > 1) {
            rightArmFragment.getChildFragmentManager().popBackStackImmediate();
        }
        else {
            super.onBackPressed();
        }



    }
}
