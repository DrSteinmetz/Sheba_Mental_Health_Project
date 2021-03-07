package com.example.sheba_mental_health_project.view.patient;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.AppointmentLoungeViewModel;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AppointmentLoungeFragment extends Fragment {

    private AppointmentLoungeViewModel mViewModel;

    final SimpleDateFormat ddMMYYYY = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    private final String TAG = "AppointmentLoungeFragment";


    public interface AppointmentLoungeFragmentInterface {
        void onBackToAppointmentsBtnClicked();
        void onEditAnswersBtnClicked();
    }

    private AppointmentLoungeFragmentInterface listener;

    public static AppointmentLoungeFragment newInstance() {
        return new AppointmentLoungeFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AppointmentLoungeFragmentInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements PreMeetingCharacterInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this,new ViewModelFactory(getContext(),
                ViewModelEnum.AppointmentLounge)).get(AppointmentLoungeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.appointment_lounge_fragment, container, false);

        final TextView dateTv = rootView.findViewById(R.id.date_tv);
        final TextView mainTv = rootView.findViewById(R.id.main_tv);
        final MaterialButton backBtn = rootView.findViewById(R.id.back_btn);
        final MaterialButton editBtn = rootView.findViewById(R.id.edit_btn);

        final Appointment appointment = mViewModel.getCurrentAppointment();

        final String date = ddMMYYYY.format(appointment.getAppointmentDate());
        dateTv.setText(date);

        final String text = getString(R.string.hello) + ' ' +
                appointment.getPatient().getFirstName() + ",\n" +
                getString(R.string.lounge_text_1) + appointment.getTherapist().getLastName() +
                ' ' + getString(R.string.lounge_text_2);
        mainTv.setText(text);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onBackToAppointmentsBtnClicked();
                }
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onEditAnswersBtnClicked();
                }
            }
        });

        return rootView;
    }
}
