package com.example.sheba_mental_health_project.view.patient;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sheba_mental_health_project.R;
import com.google.android.material.button.MaterialButton;


public class PhysicalMentalDialog extends Dialog {


    public interface PhysicalMentalDialogActionInterface {
        void onPhysicalBtnClicked();
        void onMentalBtnClicked();
    }

    private PhysicalMentalDialog.PhysicalMentalDialogActionInterface listener;

    public void setOnActionListener(PhysicalMentalDialog.PhysicalMentalDialogActionInterface physicalMentalDialogActionInterface) {
        this.listener = physicalMentalDialogActionInterface;
    }

    public PhysicalMentalDialog(@NonNull Context context) {
        super(context);
        initialize();
    }

    private void initialize() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_physical_mental_dialog, null);

        final MaterialButton physicalBtn = view.findViewById(R.id.physical_btn);
        final MaterialButton mentalBtn = view.findViewById(R.id.mental_btn);

        physicalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPhysicalBtnClicked();
                }
                dismiss();
            }
        });

        mentalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onMentalBtnClicked();
                }
                dismiss();
            }
        });

        setCancelable(true);
        setContentView(view);
    }

    @Override
    public void show() {
        super.show();

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
    }

}