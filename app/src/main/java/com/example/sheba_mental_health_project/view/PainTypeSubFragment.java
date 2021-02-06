package com.example.sheba_mental_health_project.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.enums.BodyPartEnum;
import com.example.sheba_mental_health_project.model.enums.PainTypeEnum;
import com.google.android.material.button.MaterialButton;

public class PainTypeSubFragment extends Fragment {

    private final String TAG = "PainTypeSubFragment";


    public interface PainTypeSubFragmentInterface {
        void onContinueToOtherFeelingsBtnClicked(PainTypeEnum painType);
    }

    private PainTypeSubFragmentInterface listener;

    public static PainTypeSubFragment newInstance(final PainTypeEnum painType,
                                                  final BodyPartEnum fragmentName) {
        PainTypeSubFragment fragment = new PainTypeSubFragment();
        Bundle args = new Bundle();
        args.putSerializable("pain_type", painType);
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
        final View rootView = inflater.inflate(R.layout.fragment_pain_type_sub, container, false);

        final String[] types = requireContext().getResources().getStringArray(R.array.pain_type_spinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                R.layout.spinner_text, types);
        final AppCompatSpinner spinner = rootView.findViewById(R.id.spinner);
        final MaterialButton continueBtn = rootView.findViewById(R.id.continue_btn);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);

        if (getArguments() != null) {
            final PainTypeEnum painType = (PainTypeEnum) getArguments()
                    .getSerializable("pain_type");
            if (painType != null) {
                spinner.setSelection(painType.ordinal() + 1);
            }
        }

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    final int position = spinner.getSelectedItemPosition() - 1;
                    final PainTypeEnum painType = position < 0 ? null : PainTypeEnum.values()[position];
                    listener.onContinueToOtherFeelingsBtnClicked(painType);
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
            case Legs:
                listener = (LegsFragment) getParentFragment();
                break;
            default:
                listener = null;
                throw new ClassCastException("The fragment must implement PainStrengthSubFragmentInterface Listener!");
        }
    }
}
