package com.example.sheba_mental_health_project.view.therapist;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.sheba_mental_health_project.view.ConfirmationDialog;
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

    public interface HistoryAppointmentFragmentInterface{
        void onClickedDocumentsFromHistoryFragment(boolean isTherapist, Appointment appointment);
    }
    HistoryAppointmentFragmentInterface listener;

    public HistoryAppointmentFragment() {}

    public static HistoryAppointmentFragment newInstance(final Appointment appointment) {
        HistoryAppointmentFragment fragment = new HistoryAppointmentFragment();
        Bundle args = new Bundle();
        args.putSerializable("appointment", appointment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (HistoryAppointmentFragmentInterface) context;
        }catch (Exception ex){
            throw new ClassCastException("The Activity Must Implements HistoryAppointmentFragmentInterface listener!");
        }
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
        final TextView summaryTv = rootView.findViewById(R.id.summary_tv);
        final TextView documentsTv = rootView.findViewById(R.id.documents_tv);
        mTabLayout = rootView.findViewById(R.id.tab_layout);
        mViewPager = rootView.findViewById(R.id.view_pager);

        dateTv.setText(ddMMYYYY.format(mAppointment.getAppointmentDate()));
        patientNameTv.setText(mAppointment.getPatient().getFullName());
        therapistNameTv.setText(mAppointment.getTherapist().getFullName());
        summaryTv.setPaintFlags(summaryTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        mViewPager.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.setCurrentItem(1);

        summaryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String diagnosis = mAppointment.getDiagnosis();
                String recommendations = mAppointment.getRecommendations();

                diagnosis = (diagnosis == null || diagnosis.isEmpty()) ?
                        getString(R.string.no_diagnosis) : diagnosis ;
                recommendations = (recommendations == null || recommendations.isEmpty()) ?
                        getString(R.string.no_recommendations) : recommendations;

                final ConfirmationDialog summaryDialog = new ConfirmationDialog(requireContext());
                summaryDialog.setTitleWarningText(getString(R.string.last_meeting_summary));
                summaryDialog.setPromptText(getString(R.string.diagnosis_prompt) + "\n"
                        + diagnosis + "\n\n"
                        + getString(R.string.recommendations_prompt) + "\n"
                        + recommendations);
                summaryDialog.show();
            }
        });

        documentsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( listener != null ){
                    listener.onClickedDocumentsFromHistoryFragment(true,mAppointment);
                }
            }
        });

        return rootView;
    }
}
