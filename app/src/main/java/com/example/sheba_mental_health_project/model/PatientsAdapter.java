package com.example.sheba_mental_health_project.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sheba_mental_health_project.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.PatientViewHolder>
        implements Filterable {

    private final Context mContext;

    private final List<Patient> mPatients;
    private final List<Patient> mPatientsFiltered;

    private final String TAG = "PatientsAdapter";


    public PatientsAdapter(final Context context, final List<Patient> patients) {
        this.mContext = context;
        this.mPatients = patients;
        this.mPatientsFiltered = new ArrayList<>(patients);
    }

    public interface PatientsAdapterInterface {
        void onPatientClicked(final Patient patient);

        void onEmptyResults();
    }

    private PatientsAdapterInterface listener;

    public void setPatientsAdapterListener(final PatientsAdapterInterface listener) {
        this.listener = listener;
    }

    public class PatientViewHolder extends RecyclerView.ViewHolder {

        final TextView patientNameTv;
        final TextView patientEmailTv;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);

            patientNameTv = itemView.findViewById(R.id.patient_name_tv);
            patientEmailTv = itemView.findViewById(R.id.email_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onPatientClicked(mPatientsFiltered.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults results = new FilterResults();
                final String patientName = constraint != null ? constraint.toString() : null;
                final List<Patient> filteredPatients = new ArrayList<>(mPatients);

                if (patientName != null) {
                    for (Iterator<Patient> iterator = filteredPatients.iterator();
                         iterator.hasNext(); ) {
                        final Patient patient = iterator.next();
                        if (!patient.getFullName().toLowerCase().contains(patientName)) {
                            iterator.remove();
                        }
                    }
                }

                results.values = filteredPatients;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.values != null && !((List<Patient>) results.values).isEmpty()) {
                    mPatientsFiltered.clear();
                    mPatientsFiltered.addAll((List<Patient>) results.values);
                    notifyDataSetChanged();
                } else {
                    Log.e(TAG, "publishResults: Patient not Found");
                    if (listener != null) {
                        listener.onEmptyResults();
                    }
                }
            }
        };
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_cell_layout, parent, false);

        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        final Patient patient = mPatientsFiltered.get(position);

        if (patient != null) {
            holder.patientNameTv.setText(patient.getFullName());
            holder.patientEmailTv.setText(patient.getEmail());
        }
    }

    @Override
    public int getItemCount() {
        return mPatientsFiltered.size();
    }
}
