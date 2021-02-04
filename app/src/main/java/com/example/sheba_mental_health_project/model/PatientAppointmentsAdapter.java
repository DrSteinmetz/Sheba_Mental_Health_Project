package com.example.sheba_mental_health_project.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sheba_mental_health_project.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PatientAppointmentsAdapter extends RecyclerView.Adapter<PatientAppointmentsAdapter.AppointmentViewHolder> {

    private final Context mContext;

    private final List<Appointment> mAppointments;

    final SimpleDateFormat ddMMYYYY = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    final SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private final String TAG = "AppointmentsAdapter";

    public PatientAppointmentsAdapter(final Context mContext,
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

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);

            cardLayout = itemView.findViewById(R.id.card_layout);
            nameTv = itemView.findViewById(R.id.patient_name_tv);
            dateTv = itemView.findViewById(R.id.date_tv);
            timeTv = itemView.findViewById(R.id.time_tv);

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
                .inflate(R.layout.patient_appointment_cell_layout, null);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        final Appointment appointment = mAppointments.get(position);

        final String therapistName = appointment.getTherapist().getFirstName() +
                " " + appointment.getTherapist().getLastName();
        holder.nameTv.setText(therapistName);

        final String date = ddMMYYYY.format(appointment.getAppointmentDate());
        holder.dateTv.setText(date);

        final String time = HHmm.format(appointment.getAppointmentDate());
        holder.timeTv.setText(time);
    }

    @Override
    public int getItemCount() {
        return mAppointments.size();
    }
}
