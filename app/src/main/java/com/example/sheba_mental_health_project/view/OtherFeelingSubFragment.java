package com.example.sheba_mental_health_project.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.enums.BodyPartEnum;
import com.example.sheba_mental_health_project.model.enums.PainLocationEnum;
import com.example.sheba_mental_health_project.model.enums.PainOtherFeelingsEnum;
import com.google.android.material.button.MaterialButton;

import java.util.Arrays;

public class OtherFeelingSubFragment extends Fragment {

    private final String TAG = "OtherFeelingFragment";


    public interface OtherFeelingSubFragmentInterface {
        void onContinueToPainFrequencyBtnClicked(PainOtherFeelingsEnum otherFeeling);
    }

    private OtherFeelingSubFragmentInterface listener;

    public static OtherFeelingSubFragment newInstance(final String otherFeelingLocal,
                                                      final BodyPartEnum fragmentName) {
        OtherFeelingSubFragment fragment = new OtherFeelingSubFragment();
        Bundle args = new Bundle();
        args.putString("other_feeling_local", otherFeelingLocal);
        args.putSerializable("fragment_name", fragmentName);
        fragment.setArguments(args);
        return fragment;
    }

    public static OtherFeelingSubFragment newInstance(final String otherFeelingLocal,
                                                      final PainLocationEnum chosenPoint,
                                                      final BodyPartEnum fragmentName) {
        OtherFeelingSubFragment fragment = new OtherFeelingSubFragment();
        Bundle args = new Bundle();
        args.putString("other_feeling_local", otherFeelingLocal);
        args.putSerializable("chosen_point", chosenPoint);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_other_feeling_sub, container, false);

        PainLocationEnum chosenPoint = null;
        if (getArguments() != null) {
            chosenPoint = (PainLocationEnum) getArguments().getSerializable("chosen_point");
        }
        String[] feelings;

        if (chosenPoint == PainLocationEnum.LeftEye || chosenPoint == PainLocationEnum.RightEye) {
            feelings = requireContext().getResources().getStringArray(R.array.other_feeling_spinner_eyes);
        } else if (chosenPoint == PainLocationEnum.Mouth) {
            feelings = requireContext().getResources().getStringArray(R.array.other_feeling_spinner_mouth);
        } else if (chosenPoint == PainLocationEnum.UpperLeftAbdomen ||
                chosenPoint == PainLocationEnum.UpperRightAbdomen ||
                chosenPoint == PainLocationEnum.LowerLeftAbdomen ||
                chosenPoint == PainLocationEnum.LowerRightAbdomen ||
                chosenPoint == PainLocationEnum.Navel) {
            feelings = requireContext().getResources().getStringArray(R.array.other_feeling_spinner_abdomen);
        } else if (chosenPoint == PainLocationEnum.PrivatePart) {
            feelings = requireContext().getResources().getStringArray(R.array.other_feeling_spinner_genitals);
        } else {
            feelings = requireContext().getResources().getStringArray(R.array.other_feeling_spinner);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_text, feelings);
        final AppCompatSpinner spinner = rootView.findViewById(R.id.spinner);
        final MaterialButton continueBtn = rootView.findViewById(R.id.continue_btn);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);

        if (getArguments() != null) {
            final String otherFeeling = getArguments().getString("other_feeling_local");
            if (otherFeeling != null) {
                final int selectedPosition = Arrays.asList(feelings).indexOf(otherFeeling);

                spinner.setSelection(selectedPosition != -1 ? selectedPosition : 0);
            }
        }

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PainOtherFeelingsEnum otherFeeling;
                final String selectedItemStr = spinner.getSelectedItem().toString();

                if (getString(R.string.bleeding).equals(selectedItemStr)) {
                    otherFeeling = PainOtherFeelingsEnum.Bleeding;
                } else if (getString(R.string.itching).equals(selectedItemStr)) {
                    otherFeeling = PainOtherFeelingsEnum.Itching;
                } else if (getString(R.string.sting).equals(selectedItemStr)) {
                    otherFeeling = PainOtherFeelingsEnum.Sting;
                } else if (getString(R.string.strange).equals(selectedItemStr)) {
                    otherFeeling = PainOtherFeelingsEnum.Strange;
                } else if (getString(R.string.numb).equals(selectedItemStr)) {
                    otherFeeling = PainOtherFeelingsEnum.Numb;
                } else if (getString(R.string.color_change).equals(selectedItemStr)) {
                    otherFeeling = PainOtherFeelingsEnum.ColorChange;
                } else if (getString(R.string.dryness).equals(selectedItemStr)) {
                    otherFeeling = PainOtherFeelingsEnum.Dryness;
                } else if (getString(R.string.tears_excess).equals(selectedItemStr)) {
                    otherFeeling = PainOtherFeelingsEnum.TearsExcess;
                } else if (getString(R.string.constipation).equals(selectedItemStr)) {
                    otherFeeling = PainOtherFeelingsEnum.Constipation;
                } else if (getString(R.string.diarrhea).equals(selectedItemStr)) {
                    otherFeeling = PainOtherFeelingsEnum.Diarrhea;
                } else if (getString(R.string.heartburn).equals(selectedItemStr)) {
                    otherFeeling = PainOtherFeelingsEnum.Heartburn;
                } else if (getString(R.string.drooling).equals(selectedItemStr)) {
                    otherFeeling = PainOtherFeelingsEnum.Drooling;
                } else if (getString(R.string.urinary_retention).equals(selectedItemStr)) {
                    otherFeeling = PainOtherFeelingsEnum.UrinaryRetention;
                } else if (getString(R.string.frequent_urinary).equals(selectedItemStr)) {
                    otherFeeling = PainOtherFeelingsEnum.FrequentUrinary;
                } else if (getString(R.string.urgent_urinary).equals(selectedItemStr)) {
                    otherFeeling = PainOtherFeelingsEnum.UrgentUrinary;
                } else if (getString(R.string.other).equals(selectedItemStr)) {
                    otherFeeling = PainOtherFeelingsEnum.Other;
                } else {
                    otherFeeling = null;
                }

                if (listener != null) {
                    /*final int position = spinner.getSelectedItemPosition() - 1;
                    final PainOtherFeelingsEnum otherFeeling = position < 0 ? null :
                            PainOtherFeelingsEnum.values()[position];*/
                    listener.onContinueToPainFrequencyBtnClicked(otherFeeling);
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
            case CenterOfMass:
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
                break;
            default:
                listener = null;
                throw new ClassCastException("The fragment must implement OtherFeelingSubFragmentInterface Listener!");
        }
    }
}
