package com.example.sheba_mental_health_project.model;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PatientAppointmentsAdapter extends RecyclerView.Adapter<PatientAppointmentsAdapter.AppointmentViewHolder> {

    private final Context mContext;

    private final List<Appointment> mAppointments;

    final SimpleDateFormat ddMMYYYY = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
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
        private final LinearLayout questionnaireLayout;
        private final ImageView cellIv;
        private final TextView cellStatusIv;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);

            cardLayout = itemView.findViewById(R.id.card_layout);
            nameTv = itemView.findViewById(R.id.patient_name_tv);
            dateTv = itemView.findViewById(R.id.date_tv);
            timeTv = itemView.findViewById(R.id.time_tv);
            questionnaireLayout = itemView.findViewById(R.id.questionnaire_layout);
            cellIv = itemView.findViewById(R.id.patient_cell_image);
            cellStatusIv = itemView.findViewById(R.id.patient_cell_status);

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
                .inflate(R.layout.patient_appointment_cell_layout, parent,false);

        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        final Appointment appointment = mAppointments.get(position);

        if (appointment != null) {
            final String therapistName = appointment.getTherapist().getFirstName() +
                    " " + appointment.getTherapist().getLastName();
            holder.nameTv.setText(therapistName);

            final String date = ddMMYYYY.format(appointment.getAppointmentDate());
            holder.dateTv.setText(date);

            final String time = HHmm.format(appointment.getAppointmentDate());
            holder.timeTv.setText(time);

            holder.questionnaireLayout.setVisibility(appointment.getIsFinishedPreQuestions() ?
                    View.GONE : View.VISIBLE);

            if (appointment.getState() == AppointmentStateEnum.OnGoing) {
                holder.cellIv.setImageResource(R.drawable.arrows_anim);
                final AnimationDrawable arrowsAnim = (AnimationDrawable) holder.cellIv.getDrawable();
                arrowsAnim.start();
                holder.cellStatusIv.setText(mContext.getString(R.string.enter));
                holder.cellStatusIv.setTextColor(mContext.getColor(R.color.light_blue));
            } else {
                holder.cellIv.setImageResource(R.drawable.ic_clock);
                holder.cellStatusIv.setText(mContext.getString(R.string.upcoming));
                holder.cellStatusIv.setTextColor(mContext.getColor(R.color.light_gray));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mAppointments.size();
    }
}
