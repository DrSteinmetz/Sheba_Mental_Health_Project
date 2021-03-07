package com.example.sheba_mental_health_project.view.character;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.enums.BodyPartEnum;
import com.google.android.material.button.MaterialButton;

public class LeftOrRightDialogFragment extends DialogFragment {

    private String mChosenBodyPartStr;
    private String mChosenSideStr;

    private final String CHOSEN_BODY_PART = "chosen_body_part";
    private final String CHOSEN_SIDE = "chosen_side";

    private final String TAG = "LeftOrRightDialogFragment";


    public interface LeftOrRightDialogFragmentInterface {
        void onOkBtnClicked(boolean isBoth);
    }

    private LeftOrRightDialogFragmentInterface listener;

    public LeftOrRightDialogFragment() {}

    public static LeftOrRightDialogFragment newInstance(final BodyPartEnum fragmentName) {
        LeftOrRightDialogFragment fragment = new LeftOrRightDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("fragment_name", fragmentName);
        /*args.putString("chosen_body_part", chosenBodyPart);
        args.putString("chosen_side", chosenSide);*/
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mChosenBodyPartStr = getArguments().getString(CHOSEN_BODY_PART);
            mChosenSideStr = getArguments().getString(CHOSEN_SIDE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_left_or_right_dialog, container, false);

        final RadioGroup leftRightRg = rootView.findViewById(R.id.left_right_rg);
        final MaterialButton okBtn = rootView.findViewById(R.id.ok_btn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean isBoth = (leftRightRg.getCheckedRadioButtonId() == R.id.both_rb);
                if (listener != null) {
                    listener.onOkBtnClicked(isBoth);
                    dismiss();
                }
            }
        });

        return rootView;
    }

    private void setListener(final BodyPartEnum fragmentName) {
        switch (fragmentName) {
            case Head:
                listener = (HeadFragment) getParentFragment();
                break;
            /*case CenterOfMass:
                listener = (CenterOfMassFragment) getParentFragment();
                break;
            case RightArm:
                listener = (RightArmFragment) getParentFragment();
                break;
            case LeftArm:
                listener = (LeftArmFragment) getParentFragment();
                break;
            case Genitals:
                listener = (GenitalsFragment) getParentFragment();
                break;
            case Legs:
                listener = (LegsFragment) getParentFragment();
                break;*/
            default:
                listener = null;
                throw new ClassCastException("The fragment must implement LeftOrRightDialogFragment Listener!");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() != null) {
            final Window window = getDialog().getWindow();
            if (window != null) {
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }
    }
}
