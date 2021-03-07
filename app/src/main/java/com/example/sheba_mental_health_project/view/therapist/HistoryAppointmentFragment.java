package com.example.sheba_mental_health_project.view.therapist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.HistoryAppointmentPageAdapter;
import com.example.sheba_mental_health_project.view.character.CharacterFragment;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class HistoryAppointmentFragment extends Fragment {

    private Appointment mAppointment;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private HistoryAppointmentPageAdapter mPageAdapter;

    private static final String TAG = "HistoryAppointmentFrag";


    public HistoryAppointmentFragment() {}

    public static HistoryAppointmentFragment newInstance(final Appointment appointment) {
        HistoryAppointmentFragment fragment = new HistoryAppointmentFragment();
        Bundle args = new Bundle();
        args.putSerializable("appointment", appointment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mAppointment = (Appointment) getArguments().getSerializable("appointment");
        }

        mPageAdapter = new HistoryAppointmentPageAdapter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        mPageAdapter.addFragment(TherapistMentalGenericFragment.newInstance(mAppointment),
                getString(R.string.mental_state));

        mPageAdapter.addFragment(CharacterFragment
                .newInstance(mAppointment, false, true),
                getString(R.string.character));

        mPageAdapter.addFragment(TherapistPhysicalStateGenericFragment.newInstance(mAppointment),
                getString(R.string.physical_state));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_history_appointment, container, false);

        final SimpleDateFormat ddMMYYYY = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        final TextView dateTv = rootView.findViewById(R.id.date_tv);
        final TextView patientNameTv = rootView.findViewById(R.id.patient_name_tv);
        final TextView therapistNameTv = rootView.findViewById(R.id.therapist_name_tv);
        mTabLayout = rootView.findViewById(R.id.tab_layout);
        mViewPager = rootView.findViewById(R.id.view_pager);

        dateTv.setText(ddMMYYYY.format(mAppointment.getAppointmentDate()));
        patientNameTv.setText(mAppointment.getPatient().getFullName());
        therapistNameTv.setText(mAppointment.getTherapist().getFullName());

        mViewPager.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.setCurrentItem(1);

        return rootView;
    }
}