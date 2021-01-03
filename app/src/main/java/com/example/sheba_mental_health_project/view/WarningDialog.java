package com.example.sheba_mental_health_project.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sheba_mental_health_project.R;
import com.google.android.material.button.MaterialButton;

public class WarningDialog extends Dialog {

    private TextView mPromptTv;

    public interface WarningDialogActionInterface {
        void onYesBtnClicked();

        void onNoBtnClicked();
    }

    private WarningDialogActionInterface listener;

    public void setOnActionListener(WarningDialogActionInterface warningDialogActionInterface) {
        this.listener = warningDialogActionInterface;
    }

    public WarningDialog(@NonNull Context context) {
        super(context);

        initialize();
    }

    private void initialize() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.warning_dialog, null);
        mPromptTv = view.findViewById(R.id.prompt_tv);
        final MaterialButton yesBtn = view.findViewById(R.id.yes_btn);
        final MaterialButton noBtn = view.findViewById(R.id.no_btn);

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
        setContentView(view);
    }

    @Override
    public void show() {
        super.show();

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
    }

    public void setPromptText(final String prompt) {
        mPromptTv.setText(prompt);
    }
}
