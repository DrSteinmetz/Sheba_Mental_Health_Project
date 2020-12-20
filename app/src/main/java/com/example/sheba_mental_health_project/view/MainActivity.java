package com.example.sheba_mental_health_project.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.MainActivityViewModel;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MainTherapistFragment.MainTherapistInterface {

    private MainActivityViewModel mViewModel;

    private DrawerLayout mDrawerLayout;

    private CoordinatorLayout mCoordinatorLayout;
    private NavigationView mNavigationView;

    private final String MAIN_THERAPIST_FRAG = "Main_Therapist_Fragment";
    private final String MAIN_PATIENT_FRAG = "Main_Patient_Fragment";
    private final String ADD_PATIENT_FRAG = "Add_Patient_Fragment";
    private final String ADD_APPOINTMENT_FRAG = "Add_Appointment_Fragment";

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

//        mDrawerLayout.addDrawerListener(this);
        final boolean isTherapist = getIntent().getBooleanExtra(IS_THERAPIST, false);
        mNavigationView.getMenu().clear();
        mNavigationView.inflateMenu(isTherapist ? R.menu.therapist_drawer_menu : R.menu.patient_drawer_menu);
        mNavigationView.setNavigationItemSelectedListener(this);

        if (isTherapist) {
            getSupportFragmentManager().beginTransaction()
                    //TODO: add enter and exit animations
                    .add(R.id.container, MainTherapistFragment.newInstance(), MAIN_THERAPIST_FRAG)
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    //TODO: add enter and exit animations
                    .add(R.id.container, MainPatientFragment.newInstance(), MAIN_PATIENT_FRAG)
                    .addToBackStack(null)
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

    @Override
    public void onAddAppointClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, AddAppointmentFragment.newInstance(), ADD_APPOINTMENT_FRAG)
                .addToBackStack(null)
                .commit();
    }
}
