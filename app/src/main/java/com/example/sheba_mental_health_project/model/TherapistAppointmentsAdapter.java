package com.example.sheba_mental_health_project.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sheba_mental_health_project.R;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TherapistAppointmentsAdapter extends RecyclerView.Adapter<TherapistAppointmentsAdapter.AppointmentViewHolder> {

    private final Context mContext;

    private final List<Appointment> mAppointments;

    final SimpleDateFormat ddMMYYYY = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    final SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private final String TAG = "AppointmentsAdapter";


    public TherapistAppointmentsAdapter(final Context mContext,
                                        final List<Appointment> mAppointments) {
        this.mContext = mContext;
        this.mAppointments = mAppointments;
    }

    public interface AppointmentListener {
        void onAppointmentClicked(int position, View view);
    }

    private AppointmentListener listener;

    public void setAppointmentListener(final AppointmentListener listener) {
        this.listener = listener;
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardLayout;
        private final TextView nameTv;
        private final TextView dateTv;
        private final TextView timeTv;
        private final ImageView questionnaireIv;
        private final TextView questionnaireTv;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);

            cardLayout = itemView.findViewById(R.id.card_layout);
            nameTv = itemView.findViewById(R.id.patient_name_tv);
            dateTv = itemView.findViewById(R.id.date_tv);
            timeTv = itemView.findViewById(R.id.time_tv);
            questionnaireIv = itemView.findViewById(R.id.questionnaire_iv);
            questionnaireTv = itemView.findViewById(R.id.questionnaire_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onAppointmentClicked(getAdapterPosition(), v);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.therapist_appointment_cell_layout, parent,false);

        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        final Appointment appointment = mAppointments.get(position);

        if (appointment != null) {
            final String patientName = appointment.getPatient().getFullName();
            holder.nameTv.setText(patientName);

            final String date = ddMMYYYY.format(appointment.getAppointmentDate());
            holder.dateTv.setText(date);

            final String time = HHmm.format(appointment.getAppointmentDate());
            holder.timeTv.setText(time);

            holder.questionnaireIv.setImageResource(appointment.getIsFinishedPreQuestions() ?
                    R.drawable.ic_questionnaire_done : R.drawable.ic_questionnaire);

            if (appointment.getIsFinishedPreQuestions()) {
                holder.questionnaireTv.setText(R.string.questionnaire_done);
                holder.questionnaireTv.setTextColor(mContext.getColor(R.color.light_blue));
            } else {
                holder.questionnaireTv.setText(R.string.undone_questionnaire);
                holder.questionnaireTv.setTextColor(mContext.getColor(R.color.light_gray));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mAppointments.size();
    }
}
