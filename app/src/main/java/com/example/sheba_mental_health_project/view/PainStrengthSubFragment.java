package com.example.sheba_mental_health_project.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.enums.BodyPartEnum;
import com.google.android.material.button.MaterialButton;
import com.rtugeek.android.colorseekbar.ColorSeekBar;

public class PainStrengthSubFragment extends Fragment {

    private final String TAG = "PainStrengthSubFragment";


    public interface PainStrengthSubFragmentInterface {
        void onPainStrengthChanged(int painStrength, int color);
        void onContinueToPainTypeBtnClick(int painStrength, int color);
    }

    private PainStrengthSubFragmentInterface listener;

    public static PainStrengthSubFragment newInstance(final int position,
                                                      final BodyPartEnum fragmentName) {
        PainStrengthSubFragment fragment = new PainStrengthSubFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putSerializable("fragment_name", fragmentName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (getArguments() != null) {
            setListener((BodyPartEnum) getArguments().getSerializable("fragment_name"));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_pain_strength_sub, container, false);

        final ColorSeekBar painStrengthSb = rootView.findViewById(R.id.pain_strength_sb);
        final MaterialButton continueBtn = rootView.findViewById(R.id.continue_btn);

        if (getArguments() != null) {
            final int position = getArguments().getInt("position");
            painStrengthSb.setColorBarPosition(position);
        }

        Log.d(TAG, "onCreateView: ");
        if (listener != null) {
            listener.onPainStrengthChanged(painStrengthSb.getColorBarPosition(), painStrengthSb.getColor());
        }

        painStrengthSb.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int colorBarPosition, int alphaBarPosition, int color) {
                if (listener != null) {
                    listener.onPainStrengthChanged(colorBarPosition, color);
                }
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onContinueToPainTypeBtnClick(painStrengthSb.getColorBarPosition(),
                            painStrengthSb.getColor());
                }
            }
        });

        return rootView;
    }

    private void setListener(final BodyPartEnum fragmentName) {
        switch (fragmentName) {
            case RightArm:
                listener = (RightArmFragment) getParentFragment();
                break;
            default:
                listener = null;
                throw new ClassCastException("The fragment must implement PainStrengthSubFragmentInterface Listener!");
        }
    }
}
