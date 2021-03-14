package com.example.sheba_mental_health_project.view;

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

public class ConfirmationDialog extends Dialog {

    private TextView mTitleTv;
    private TextView mPromptTv;

    private final String TAG = "ConfirmationDialog";


    public interface ConfirmationDialogActionInterface {
        void onOkBtnClicked();
    }

    private ConfirmationDialogActionInterface listener;

    public void setOnActionListener(ConfirmationDialogActionInterface warningDialogActionInterface) {
        this.listener = warningDialogActionInterface;
    }

    public ConfirmationDialog(@NonNull Context context) {
        super(context);

        initialize();
    }

    private void initialize() {
        final View rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.confirmation_dialog, null);

        mTitleTv = rootView.findViewById(R.id.title_tv);
        mPromptTv = rootView.findViewById(R.id.prompt_tv);

        final MaterialButton okBtn = rootView.findViewById(R.id.ok_btn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onOkBtnClicked();
                }
                dismiss();
            }
        });

        setCancelable(false);
        setContentView(rootView);
    }

    public void setTitleWarningText(final String title) {
        if (mTitleTv != null) {
            mTitleTv.setText(title);
        }
    }

    public void setPromptText(final String prompt) {
        if (mPromptTv != null) {
            mPromptTv.setText(prompt);
        }
    }

    @Override
    public void show() {
        super.show();

        mTitleTv.setVisibility(mTitleTv.getText().toString().trim().isEmpty() ?
                View.GONE : View.VISIBLE);

        final Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
        }
    }
}
