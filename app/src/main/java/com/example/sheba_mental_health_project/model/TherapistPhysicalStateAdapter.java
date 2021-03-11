package com.example.sheba_mental_health_project.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.enums.PainLocationEnum;
import com.rtugeek.android.colorseekbar.ColorSeekBar;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TherapistPhysicalStateAdapter extends RecyclerView.Adapter<TherapistPhysicalStateAdapter.TherapistPhysicalStateViewHolder> {

    private final Context mContext;

    private final List<PainPoint> mPainPoints;

    private final String TAG = "TherapistPhysicalStateAdapter";


    public TherapistPhysicalStateAdapter(final Context mContext,
                                         final List<PainPoint> mPainPoints) {
        this.mContext = mContext;
        this.mPainPoints = mPainPoints;
    }

    public class TherapistPhysicalStateViewHolder extends RecyclerView.ViewHolder {

        private final TextView painLocationTv;
        private final ColorSeekBar painStrengthSb;
        private final TextView painStrengthValueTv;
        private final TextView painTypeTv;
        private final TextView otherFeelingsTv;
        private final TextView painFrequencyTv;
        private final TextView descriptionTv;

        public TherapistPhysicalStateViewHolder(@NonNull View itemView) {
            super(itemView);

            painLocationTv = itemView.findViewById(R.id.pain_location_tv);
            painStrengthSb = itemView.findViewById(R.id.pain_strength_sb);
            painStrengthValueTv = itemView.findViewById(R.id.pain_strength_value_tv);
            painTypeTv = itemView.findViewById(R.id.pain_type_tv);
            otherFeelingsTv = itemView.findViewById(R.id.other_feelings_tv);
            painFrequencyTv = itemView.findViewById(R.id.feeling_frequency_tv);
            descriptionTv = itemView.findViewById(R.id.description_tv);

            painStrengthSb.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }

    @NonNull
    @Override
    public TherapistPhysicalStateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.therapist_physical_state_cell_layout, parent, false);

        return new TherapistPhysicalStateViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TherapistPhysicalStateViewHolder holder, int position) {

        final PainPoint painPoint = mPainPoints.get(position);

        if (painPoint != null) {
            holder.painStrengthSb.setColorBarPosition(painPoint.getPainStrength());
            holder.painStrengthValueTv.setText(painPoint.getPainStrength() + "");

            if (painPoint.getPainLocation() != null) {
                final String painLocation = painPoint.getPainPointLocationLocalString(mContext);
                holder.painLocationTv.setText(painLocation);
            }

            if (painPoint.getPainType() != null) {
                final String painType = painPoint.getPainPointTypeLocalString(mContext);
                holder.painTypeTv.setText(painType);
            }

            if (painPoint.getOtherFeeling() != null) {
                final String otherFeelings = painPoint.getOtherFeelingLocalString(mContext);
                holder.otherFeelingsTv.setText(otherFeelings);
            }

            if (painPoint.getFrequency() != null) {
                final String painFrequency = painPoint.getPainPointFrequencyLocalString(mContext);
                holder.painFrequencyTv.setText(painFrequency);
            }

            if (painPoint.getDescription() != null) {
                final String description = painPoint.getDescription();
                holder.descriptionTv.setText(description);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mPainPoints.size();
    }
}
