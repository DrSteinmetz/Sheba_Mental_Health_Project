package com.example.sheba_mental_health_project.view;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.BodyPartEnum;
import com.example.sheba_mental_health_project.model.enums.PainFrequencyEnum;
import com.example.sheba_mental_health_project.model.enums.PainOtherFeelingsEnum;
import com.example.sheba_mental_health_project.model.enums.PainTypeEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.CenterOfMassViewModel;

public class CenterOfMassFragment extends Fragment
        implements PainStrengthSubFragment.PainStrengthSubFragmentInterface,
        PainTypeSubFragment.PainTypeSubFragmentInterface,
        OtherFeelingSubFragment.OtherFeelingSubFragmentInterface,
        PainFrequencySubFragment.PainFrequencySubFragmentInterface,
        DescriptionDialogFragment.AddDescriptionFragmentInterface {

    private CenterOfMassViewModel mViewModel;

    private ImageView mSelectedIv;

    private final Animation alphaAnimation = new AlphaAnimation(1, 0);

    private final String SUB_FRAGS_STACK = "Center_Of_Mass_Fragments_Stack";

    private final String PAIN_STRENGTH_FRAG = "Pain_Strength_Fragment";
    private final String PAIN_TYPE_FRAG = "Pain_Type_Fragment";
    private final String OTHER_FEELING_FRAG = "Other_Feeling_Fragment";
    private final String PAIN_FREQUENCY_FRAG = "Pain_Frequency_Fragment";
    private final String DESCRIPTION_FRAG = "Description_Dialog_Fragment";

    private final String TAG = "CenterOfMassFragment";


    public static CenterOfMassFragment newInstance() {
        return new CenterOfMassFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.CenterOfMass)).get(CenterOfMassViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.center_of_mass_fragment, container, false);

        return rootView;
    }

    private void openPainStrengthFragment(final int position) {
        getChildFragmentManager().popBackStack(SUB_FRAGS_STACK, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container, PainStrengthSubFragment.newInstance(position,
                        BodyPartEnum.CenterOfMass), PAIN_STRENGTH_FRAG)
                .addToBackStack(SUB_FRAGS_STACK)
                .commit();
    }

    /**<------ Pain Strength Sub Fragment ------>*/
    @Override
    public void onPainStrengthChanged(int painStrength, int color) {
        if (mSelectedIv != null) {
            mSelectedIv.setColorFilter(color);
        }
    }

    @Override
    public void onContinueToPainTypeBtnClick(int painStrength, int color) {
        mViewModel.getPainPoint().setPainStrength(painStrength);
        mViewModel.getPainPoint().setColor(color);

        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container,
                        PainTypeSubFragment.newInstance(mViewModel.getPainPoint().getPainType(),
                                BodyPartEnum.CenterOfMass), PAIN_TYPE_FRAG)
                .addToBackStack(SUB_FRAGS_STACK)
                .commit();
    }

    /**<------ Pain Type Sub Fragment ------>*/
    @Override
    public void onContinueToOtherFeelingsBtnClicked(PainTypeEnum painType) {
        mViewModel.getPainPoint().setPainType(painType);

        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container,
                        OtherFeelingSubFragment.newInstance(mViewModel.getPainPoint().getOtherFeeling(),
                                BodyPartEnum.CenterOfMass), OTHER_FEELING_FRAG)
                .addToBackStack(SUB_FRAGS_STACK)
                .commit();
    }

    /**<------ Other Feelings Sub Fragment ------>*/
    @Override
    public void onContinueToPainFrequencyBtnClicked(PainOtherFeelingsEnum otherFeeling) {
        mViewModel.getPainPoint().setOtherFeeling(otherFeeling);

        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container,
                        PainFrequencySubFragment.newInstance(mViewModel.getPainPoint().getFrequency(),
                                BodyPartEnum.CenterOfMass), PAIN_FREQUENCY_FRAG)
                .addToBackStack(SUB_FRAGS_STACK)
                .commit();
    }

    /**<------ Pain Frequency Sub Fragment ------>*/
    @Override
    public void onContinueToDescriptionBtnClicked(PainFrequencyEnum painFrequency) {
        mViewModel.getPainPoint().setFrequency(painFrequency);

        DescriptionDialogFragment.newInstance(mViewModel.getPainPoint().getDescription(),
                BodyPartEnum.CenterOfMass)
                .show(getChildFragmentManager().beginTransaction(), DESCRIPTION_FRAG);
    }

    /**<------ Description Sub Fragment ------>*/
    @Override
    public void onFinishBtnClicked(String description) {
        mViewModel.getPainPoint().setDescription(description);
        mViewModel.setPainPointsInDB();
    }
}
