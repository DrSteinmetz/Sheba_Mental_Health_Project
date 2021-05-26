package com.example.sheba_mental_health_project.view.patient;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RatingBar;

import androidx.annotation.NonNull;

import com.example.sheba_mental_health_project.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RatingPatientDialog extends Dialog {

    private RatingBar mRatingBar;
    private TextInputEditText mRatingExplanationEt;

    private float mRating;

    private final String TAG = "ConfirmationDialog";


    public interface ConfirmationDialogActionInterface {
        void onOkBtnClicked(float rating, String ratingExplanation);
    }

    private RatingPatientDialog.ConfirmationDialogActionInterface listener;

    public void setOnActionListener(RatingPatientDialog.ConfirmationDialogActionInterface warningDialogActionInterface) {
        this.listener = warningDialogActionInterface;
    }

    public RatingPatientDialog(@NonNull Context context) {
        super(context);

        initialize();
    }

    private void initialize() {
        final View rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.rating_patient_dialog, null);
        mRating = 0;

        mRatingBar = rootView.findViewById(R.id.ratingbar);
        mRatingExplanationEt = rootView.findViewById(R.id.elaboration_content_et);


        final MaterialButton okBtn = rootView.findViewById(R.id.ok_btn);

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mRating = rating;
            }
        });


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    final String ratingExplanation = mRatingExplanationEt.getText().toString();
                    listener.onOkBtnClicked(mRating, ratingExplanation);
                }
                dismiss();
            }
        });

        setCancelable(false);
        setContentView(rootView);
    }



    @Override
    public void show() {
        super.show();


        final Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
        }
    }
}
