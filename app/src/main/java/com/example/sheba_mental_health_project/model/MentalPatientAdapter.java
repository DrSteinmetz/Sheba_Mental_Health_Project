package com.example.sheba_mental_health_project.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sheba_mental_health_project.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MentalPatientAdapter extends RecyclerView.Adapter<MentalPatientAdapter.MentalPatientViewHolder> {

    private final Context mContext;

    private final String TAG = "MentalPatientAdapter";
    private final List<Feeling> mFeelings;
    private final Map<String , Integer> mAnswers;



    public MentalPatientAdapter(final Context context, final List<Feeling> feelings,
                                final Map<String , Integer> answers) {
        this.mContext = context;
        this.mFeelings = feelings;
        this.mAnswers = answers;
    }

    public class MentalPatientViewHolder extends RecyclerView.ViewHolder {

        final private TextView feelingNameTV;
        final private ImageView feelingIm;
        final private SeekBar seekBar;

        public MentalPatientViewHolder(@NonNull View itemView) {
            super(itemView);

            feelingNameTV = itemView.findViewById(R.id.feeling_tv);
            feelingIm = itemView.findViewById(R.id.feeling_iv);
            seekBar = itemView.findViewById(R.id.seek_bar);


            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                       // if (!mAnswers.containsKey(mFeelings.get(getAdapterPosition()).getId())) {
                            mAnswers.put(mFeelings.get(getAdapterPosition()).getId(), progress);
                       // }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

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

        holder.feelingNameTV.setText(mFeelings.get(position).getName());
        holder.feelingIm.setImageResource(mFeelings.get(position).getImageId());
        if (mAnswers.containsKey(mFeelings.get(position).getId())){
            holder.seekBar.setProgress(mAnswers.get(mFeelings.get(position).getId()));


        }

    }

    @Override
    public int getItemCount() {
        return mFeelings.size();
    }
}
