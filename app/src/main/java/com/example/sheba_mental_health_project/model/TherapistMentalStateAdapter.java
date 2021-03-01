package com.example.sheba_mental_health_project.model;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sheba_mental_health_project.R;

import java.util.List;
import java.util.Map;

public class TherapistMentalStateAdapter extends RecyclerView.Adapter<TherapistMentalStateAdapter.TherapistMentalStateViewHolder> {

    private final List<Feeling> mFeelings;
    private final Map<String, Integer> mAnswers;

    private final String TAG = "TherapistMentalStateAdapter";


    public TherapistMentalStateAdapter(final List<Feeling> feelings,
                                       final Map<String, Integer> answers) {
        mFeelings = feelings;
        mAnswers = answers;
    }

    public class TherapistMentalStateViewHolder extends RecyclerView.ViewHolder {

        private final TextView feelingNameTv;
        private final AppCompatSeekBar feelingSb;
        private final TextView feelingStrengthTv;

        public TherapistMentalStateViewHolder(@NonNull View itemView) {
            super(itemView);

            feelingNameTv = itemView.findViewById(R.id.feeling_name_tv);
            feelingSb = itemView.findViewById(R.id.feeling_strength_sb);
            feelingStrengthTv = itemView.findViewById(R.id.feeling_strength_tv);

            feelingSb.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }

    @NonNull
    @Override
    public TherapistMentalStateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.therapist_mental_state_cell_layout, parent, false);

        return new TherapistMentalStateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TherapistMentalStateViewHolder holder, int position) {
        final Feeling feeling = mFeelings.get(position);

        holder.feelingNameTv.setText(feeling.getName());

        if (mAnswers.containsKey(feeling.getId())) {
            final int feelingStrength = mAnswers.get(feeling.getId());
            holder.feelingSb.setProgress(feelingStrength);
            holder.feelingStrengthTv.setText(feelingStrength + "");
        } else {
            holder.feelingSb.setProgress(0);
            holder.feelingStrengthTv.setText("0");
        }
    }

    @Override
    public int getItemCount() {
        return mFeelings.size();
    }
}
