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
import com.example.sheba_mental_health_project.model.enums.PainFrequencyEnum;
import com.google.android.material.button.MaterialButton;

public class PainFrequencySubFragment extends Fragment {

    private final String TAG = "PainFrequencyFragment";


    public interface PainFrequencySubFragmentInterface {
        void onContinueToDescriptionBtnClicked(PainFrequencyEnum painFrequency);
    }

    private PainFrequencySubFragmentInterface listener;

    public static PainFrequencySubFragment newInstance(final PainFrequencyEnum painFrequency,
                                                       final BodyPartEnum fragmentName) {
        PainFrequencySubFragment fragment = new PainFrequencySubFragment();
        Bundle args = new Bundle();
        args.putSerializable("pain_frequency", painFrequency);
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
        final View rootView = inflater.inflate(R.layout.fragment_pain_frequency_sub, container, false);

        final String[] frequencies = requireContext().getResources().getStringArray(R.array.pain_frequency_spinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                R.layout.spinner_text, frequencies);
        final AppCompatSpinner spinner = rootView.findViewById(R.id.spinner);
        final MaterialButton finishBtn = rootView.findViewById(R.id.finish_btn);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);

        if (getArguments() != null) {
            final PainFrequencyEnum painFrequency = (PainFrequencyEnum) getArguments()
                    .getSerializable("pain_frequency");
            if (painFrequency != null) {
                spinner.setSelection(painFrequency.ordinal() + 1);
            }
        }

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    final int position = spinner.getSelectedItemPosition() - 1;
                    final PainFrequencyEnum painFrequency = position < 0 ? null :
                            PainFrequencyEnum.values()[position];
                    listener.onContinueToDescriptionBtnClicked(painFrequency);
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
