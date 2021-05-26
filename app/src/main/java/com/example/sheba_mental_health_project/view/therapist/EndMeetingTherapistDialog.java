package com.example.sheba_mental_health_project.view.therapist;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sheba_mental_health_project.R;
import com.google.android.material.button.MaterialButton;

public class EndMeetingTherapistDialog extends Dialog {

    private TextView mDiagnosisTv;
    private TextView mRecommendationsTv;

    private final String TAG = "WarningDialog";


    public interface WarningDialogActionInterface {
        void onYesBtnClicked();

        void onNoBtnClicked();
    }

    private WarningDialogActionInterface listener;

    public void setOnActionListener(WarningDialogActionInterface warningDialogActionInterface) {
        this.listener = warningDialogActionInterface;
    }

    public EndMeetingTherapistDialog(@NonNull Context context) {
        super(context);

        initialize();
    }

    private void initialize() {
        final View rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.end_meeting_thrapist_dialog, null);

        mDiagnosisTv = rootView.findViewById(R.id.diagnosis_tv);
        mRecommendationsTv = rootView.findViewById(R.id.recommendations_tv);

        final MaterialButton yesBtn = rootView.findViewById(R.id.yes_btn);
        final MaterialButton noBtn = rootView.findViewById(R.id.no_btn);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onYesBtnClicked();
                }
                dismiss();
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onNoBtnClicked();
                }
                dismiss();
            }
        });

        setCancelable(false);
        setContentView(rootView);
    }

    public void setRecommendationsText(final String recommendationsText) {
        if (mRecommendationsTv != null) {
            mRecommendationsTv.setText(recommendationsText == null ? "" : recommendationsText);
        }
    }

    public void setDiagnosisText(final String diagnosisText) {
        if (mDiagnosisTv != null) {
            mDiagnosisTv.setText(diagnosisText == null ? "" : diagnosisText);
        }
    }

    @Override
    public void show() {
        super.show();

//        mTitleWarningTv.setVisibility(mTitleWarningTv.getText().toString().trim().isEmpty() ?
//                View.GONE : View.VISIBLE);

        final Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
        }
    }
}
