package com.example.sheba_mental_health_project.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sheba_mental_health_project.R;
import com.google.android.material.slider.Slider;

import java.util.List;
import java.util.Map;

public class MentalPatientAdapter extends RecyclerView.Adapter<MentalPatientAdapter.MentalPatientViewHolder> {

    private final Context mContext;

    private final List<Feeling> mFeelings;
    private final Map<String , Integer> mAnswers;

    private final String TAG = "MentalPatientAdapter";


    public MentalPatientAdapter(final Context context, final List<Feeling> feelings,
                                final Map<String, Integer> answers) {
        this.mContext = context;
        this.mFeelings = feelings;
        this.mAnswers = answers;
    }

    public class MentalPatientViewHolder extends RecyclerView.ViewHolder {

        private final TextView feelingNameTV;
        private final ImageView feelingIv;
        private final Slider slider;

        public MentalPatientViewHolder(@NonNull View itemView) {
            super(itemView);

            feelingNameTV = itemView.findViewById(R.id.feeling_tv);
            feelingIv = itemView.findViewById(R.id.feeling_iv);
            slider = itemView.findViewById(R.id.slider);

            slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
                @Override
                public void onStartTrackingTouch(@NonNull Slider slider) {}

                @Override
                public void onStopTrackingTouch(@NonNull Slider slider) {
                    mAnswers.put(mFeelings.get(getAdapterPosition()).getId(), (int) slider.getValue());
                }
            });
        }
    }

    @NonNull
    @Override
    public MentalPatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mental_patient_state_cell_layout, null);

        return new MentalPatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MentalPatientViewHolder holder, int position) {
        final Feeling feeling = mFeelings.get(position);

        holder.feelingNameTV.setText(feeling.getName());
        setImageByFeelingId(feeling.getId(), holder.feelingIv);
        if (mAnswers.containsKey(feeling.getId())) {
            holder.slider.setValue(mAnswers.get(feeling.getId()));
        } else {
            holder.slider.setValue(0);
        }
    }

    @Override
    public int getItemCount() {
        return mFeelings.size();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setImageByFeelingId(final String id, final ImageView imageView) {
        Drawable drawable;

        switch (id) {
            case "1":
                drawable = mContext.getResources().getDrawable(R.drawable.fear, null);
                break;
            case "2":
                drawable = mContext.getResources().getDrawable(R.drawable.sadness, null);
                break;
            case "3":
                drawable = mContext.getResources().getDrawable(R.drawable.anger, null);
                break;
            case "4":
                drawable = mContext.getResources().getDrawable(R.drawable.anxiety, null);
                break;
            case "5":
                drawable = mContext.getResources().getDrawable(R.drawable.depression, null);
                break;
            case "6":
                drawable = mContext.getResources().getDrawable(R.drawable.disturbed, null);
                break;
            case "7":
                drawable = mContext.getResources().getDrawable(R.drawable.embarrassment, null);
                break;
            case "8":
                drawable = mContext.getResources().getDrawable(R.drawable.confussion, null);
                break;
            case "9":
                drawable = mContext.getResources().getDrawable(R.drawable.aggressive, null);
                break;
            case "10":
                drawable = mContext.getResources().getDrawable(R.drawable.tension, null);
                break;
            default:
                drawable = mContext.getResources().getDrawable(R.drawable.ic_head, null);
                break;
        }

        imageView.setImageDrawable(drawable);
    }
}
