package com.example.sheba_mental_health_project.view;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.PainPoint;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.BodyPartEnum;
import com.example.sheba_mental_health_project.model.enums.PainFrequencyEnum;
import com.example.sheba_mental_health_project.model.enums.PainLocationEnum;
import com.example.sheba_mental_health_project.model.enums.PainOtherFeelingsEnum;
import com.example.sheba_mental_health_project.model.enums.PainTypeEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.LeftArmViewModel;

import org.jetbrains.annotations.NotNull;

public class LeftArmFragment extends Fragment implements
        PainStrengthSubFragment.PainStrengthSubFragmentInterface,
        PainTypeSubFragment.PainTypeSubFragmentInterface,
        OtherFeelingSubFragment.OtherFeelingSubFragmentInterface,
        PainFrequencySubFragment.PainFrequencySubFragmentInterface,
        AddDescriptionFragment.AddDescriptionFragmentInterface {

    private final String TAG = "LeftArmFragment";

    private LeftArmViewModel mViewModel;
    private ImageView mShoulderIv;
    private ImageView mElbowIv;
    private ImageView mPalmIv;
    private ImageView mSelectedIv;

    private final Animation alphaAnimation = new AlphaAnimation(1, 0);

    private final String SUB_FRAGS_STACK = "Left_Arm_Fragments_Stack";

    private final String PAIN_STRENGTH_FRAG = "Pain_Strength_Fragment";
    private final String PAIN_TYPE_FRAG = "Pain_Type_Fragment";
    private final String OTHER_FEELING_FRAG = "Other_Feeling_Fragment";
    private final String PAIN_FREQUENCY_FRAG = "Pain_Frequency_Fragment";
    private final String DESCRIPTION_FRAG = "Description_Dialog_Fragment";

    public static LeftArmFragment newInstance() {
        return new LeftArmFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.LeftArm)).get(LeftArmViewModel.class);

        final Observer<PainPoint> onSetPainPointsSucceed = new Observer<PainPoint>() {
            @Override
            public void onChanged(PainPoint painPoint) {
                getChildFragmentManager().popBackStack(SUB_FRAGS_STACK, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getActivity().onBackPressed();
                getActivity().onBackPressed();
            }
        };

        final Observer<String> onSetPainPointsFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.d(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getSetPainPointsSucceed().observe(this, onSetPainPointsSucceed);
        mViewModel.getSetPainPointsFailed().observe(this, onSetPainPointsFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.left_arm_fragment, container, false);

        final View shoulderV = rootView.findViewById(R.id.shoulder_v);
        final View elbowV = rootView.findViewById(R.id.elbow_v);
        final View palmV = rootView.findViewById(R.id.palm_v);
        mShoulderIv = rootView.findViewById(R.id.shoulder_iv);
        mElbowIv = rootView.findViewById(R.id.elbow_iv);
        mPalmIv = rootView.findViewById(R.id.palm_iv);

        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setDuration(700);

        showPainPoints();

        shoulderV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mShoulderIv,PainLocationEnum.LeftShoulder);
            }
        });

        elbowV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mElbowIv,PainLocationEnum.LeftElbow);
            }
        });

        palmV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mPalmIv,PainLocationEnum.LeftPalm);
            }
        });

        return rootView;
    }

    private void showPainPoints() {
        PainPoint painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.LeftShoulder);
        mShoulderIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mShoulderIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.LeftElbow);
        mElbowIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mElbowIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.LeftPalm);
        mPalmIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mPalmIv.setColorFilter(painPoint.getColor());
        }
    }

    private void onPainPointClicked(@NotNull final View view, final PainLocationEnum painLocationEnum) {
        showPainPoints();

        if (view.getVisibility() == View.VISIBLE) {
            // Editing
            mViewModel.setPainPoint(new PainPoint(mViewModel.getPainPointsMap().get(painLocationEnum)));
            final PainPoint painPoint = mViewModel.getPainPoint();

            openPainStrengthFragment(painPoint.getPainStrength());

   } else {
            // Adding
            mViewModel.setPainPoint(new PainPoint(painLocationEnum));
            openPainStrengthFragment(0);
        }

        view.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
        view.setAnimation(alphaAnimation);
        alphaAnimation.start();
    }

    private void onPainPointViewClicked(final ImageView view, final PainLocationEnum painLocationEnum) {
        if (mSelectedIv != null && mSelectedIv == view) {
            return;
        }

        if (getChildFragmentManager().getBackStackEntryCount() <= 1) {
            if (mSelectedIv != null) {
                mSelectedIv.setAnimation(null);
            }
            mSelectedIv = view;
            onPainPointClicked(view, painLocationEnum);
        } else {
            WarningDialog warningDialog = new WarningDialog(requireContext());
            warningDialog.setPromptText("Are you sure you want to select another point?");
            warningDialog.setOnActionListener(new WarningDialog.WarningDialogActionInterface() {
                @Override
                public void onYesBtnClicked() {
                    if (mSelectedIv != null) {
                        mSelectedIv.setAnimation(null);
                    }
                    mSelectedIv = view;
                    onPainPointClicked(view, painLocationEnum);
                }

                @Override
                public void onNoBtnClicked() {
                }
            });
            warningDialog.show();
        }
    }

    private void openPainStrengthFragment(final int position) {
        getChildFragmentManager().popBackStack(SUB_FRAGS_STACK, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container, PainStrengthSubFragment.newInstance(position,
                        BodyPartEnum.LeftArm), PAIN_STRENGTH_FRAG)
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
                                BodyPartEnum.LeftArm), PAIN_TYPE_FRAG)
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
                                BodyPartEnum.LeftArm), OTHER_FEELING_FRAG)
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
                                BodyPartEnum.LeftArm), PAIN_FREQUENCY_FRAG)
                .addToBackStack(SUB_FRAGS_STACK)
                .commit();
    }

    /**<------ Pain Frequency Sub Fragment ------>*/
    @Override
    public void onContinueToDescriptionBtnClicked(PainFrequencyEnum painFrequency) {
        mViewModel.getPainPoint().setFrequency(painFrequency);

        Log.d(TAG, "asd onContinueToDescriptionBtnClicked: " + mViewModel.getPainPoint().getDescription());
        AddDescriptionFragment.newInstance(mViewModel.getPainPoint().getDescription(),
                BodyPartEnum.LeftArm)
                .show(getChildFragmentManager().beginTransaction(), DESCRIPTION_FRAG);
    }

    /**<------ Add Description Fragment ------>*/
    @Override
    public void onFinishBtnClicked(String description) {
        mViewModel.getPainPoint().setDescription(description);
        mViewModel.setPainPointsInDB();
    }
}
