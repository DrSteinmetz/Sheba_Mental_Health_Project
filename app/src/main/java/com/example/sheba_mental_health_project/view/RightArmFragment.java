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
import com.example.sheba_mental_health_project.viewmodel.RightArmViewModel;

import org.jetbrains.annotations.NotNull;

public class RightArmFragment extends Fragment
        implements PainStrengthSubFragment.PainStrengthSubFragmentInterface,
        PainTypeSubFragment.PainTypeSubFragmentInterface,
        OtherFeelingSubFragment.OtherFeelingSubFragmentInterface,
        PainFrequencySubFragment.PainFrequencySubFragmentInterface,
        DescriptionDialogFragment.AddDescriptionFragmentInterface {

    private RightArmViewModel mViewModel;

    private ImageView mShoulderIv;
    private ImageView mArmIv;
    private ImageView mElbowIv;
    private ImageView mForearmIv;
    private ImageView mWristIv;
    private ImageView mPalmIv;
    private ImageView mFingersIv;

    private ImageView mSelectedIv;

    private final Animation alphaAnimation = new AlphaAnimation(1, 0);

    private final String SUB_FRAGS_STACK = "Right_Arm_Fragments_Stack";

    private final String PAIN_STRENGTH_FRAG = "Pain_Strength_Fragment";
    private final String PAIN_TYPE_FRAG = "Pain_Type_Fragment";
    private final String OTHER_FEELING_FRAG = "Other_Feeling_Fragment";
    private final String PAIN_FREQUENCY_FRAG = "Pain_Frequency_Fragment";
    private final String DESCRIPTION_FRAG = "Description_Dialog_Fragment";
//    private ColorSeekBar mPainStrengthSb;
//    private RadioGroup mPainTypeRg;
//    private RadioGroup mOtherFeelingsRg;
//    private RadioGroup mPainFrequencyRg;

//    private MaterialButton mAddBtn;

    private final String TAG = "RightArmFragment";


    public static RightArmFragment newInstance() {
        return new RightArmFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.RightArm)).get(RightArmViewModel.class);

        final Observer<PainPoint> onSetPainPointsSucceed = new Observer<PainPoint>() {
            @Override
            public void onChanged(PainPoint painPoint) {
                getChildFragmentManager().popBackStack(SUB_FRAGS_STACK,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .remove(LeftArmFragment.this).commit();
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
        final View rootView = inflater.inflate(R.layout.right_arm_fragment, container, false);

        final View shoulderV = rootView.findViewById(R.id.shoulder_v);
        final View armV = rootView.findViewById(R.id.arm_v);
        final View elbowV = rootView.findViewById(R.id.elbow_v);
        final View forearmV = rootView.findViewById(R.id.forearm_v);
        final View wristV = rootView.findViewById(R.id.wrist_v);
        final View palmV = rootView.findViewById(R.id.palm_v);
        final View fingersV = rootView.findViewById(R.id.fingers_v);
        mShoulderIv = rootView.findViewById(R.id.shoulder_iv);
        mArmIv = rootView.findViewById(R.id.arm_iv);
        mElbowIv = rootView.findViewById(R.id.elbow_iv);
        mForearmIv = rootView.findViewById(R.id.forearm_iv);
        mWristIv = rootView.findViewById(R.id.wrist_iv);
        mPalmIv = rootView.findViewById(R.id.palm_iv);
        mFingersIv = rootView.findViewById(R.id.fingers_iv);

        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setDuration(700);

//        mPainStrengthSb = rootView.findViewById(R.id.pain_strength_sb);
//        mPainTypeRg = rootView.findViewById(R.id.pain_type_rg);
//        mOtherFeelingsRg = rootView.findViewById(R.id.other_feeling_rg);
//        mPainFrequencyRg = rootView.findViewById(R.id.pain_frequency_rg);
//        mAddBtn = rootView.findViewById(R.id.add_btn);

        showPainPoints();

//        mPainStrengthSb.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
//            @Override
//            public void onColorChangeListener(int colorBarPosition, int alphaBarPosition, int color) {
//                if (mSelectedIv != null) {
//                    mSelectedIv.setColorFilter(color);
//                }
//            }
//        });

        shoulderV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mShoulderIv, PainLocationEnum.RightShoulder);
            }
        });

        armV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mArmIv, PainLocationEnum.RightArm);
            }
        });

        elbowV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mElbowIv, PainLocationEnum.RightElbow);
            }
        });

        forearmV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mForearmIv, PainLocationEnum.RightForearm);
            }
        });

        wristV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mWristIv, PainLocationEnum.RightWrist);
            }
        });

        palmV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mPalmIv, PainLocationEnum.RightPalm);
            }
        });

        fingersV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mFingersIv, PainLocationEnum.RightFingers);
            }
        });

//        mPainTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                final PainPoint painPoint = mViewModel.getPainPoint();
//                switch (checkedId) {
//                    case R.id.sharp_pain_rb:
//                        painPoint.setPainType(PainTypeEnum.Sharp);
//                        break;
//                    case R.id.dim_pain_rb:
//                        painPoint.setPainType(PainTypeEnum.Dim);
//                        break;
//                    case R.id.pressing_pain_rb:
//                        painPoint.setPainType(PainTypeEnum.Pressing);
//                        break;
//                    case R.id.burn_pain_rb:
//                        painPoint.setPainType(PainTypeEnum.Burning);
//                        break;
//                    case R.id.no_pain_rb:
//                        painPoint.setPainType(PainTypeEnum.NoPain);
//                        break;
//                }
//            }
//        });

//        mOtherFeelingsRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                final PainPoint painPoint = mViewModel.getPainPoint();
//                switch (mOtherFeelingsRg.getCheckedRadioButtonId()) {
//                    case R.id.bleeding_rb:
//                        painPoint.setOtherFeeling(PainOtherFeelingsEnum.Bleeding);
//                        break;
//                    case R.id.itch_rb:
//                        painPoint.setOtherFeeling(PainOtherFeelingsEnum.Itching);
//                        break;
//                    case R.id.sting_rb:
//                        painPoint.setOtherFeeling(PainOtherFeelingsEnum.Sting);
//                        break;
//                    case R.id.strange_rb:
//                        painPoint.setOtherFeeling(PainOtherFeelingsEnum.Strange);
//                        break;
//                    case R.id.numb_rb:
//                        painPoint.setOtherFeeling(PainOtherFeelingsEnum.Numb);
//                        break;
//                    case R.id.other_rb:
//                        painPoint.setOtherFeeling(PainOtherFeelingsEnum.Other);
//                        break;
//                }
//            }
//        });

//        mPainFrequencyRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                final PainPoint painPoint = mViewModel.getPainPoint();
//                switch (mPainFrequencyRg.getCheckedRadioButtonId()) {
//                    case R.id.wavy_rb:
//                        painPoint.setFrequency(PainFrequencyEnum.Wavy);
//                        break;
//                    case R.id.static_rb:
//                        painPoint.setFrequency(PainFrequencyEnum.Static);
//                        break;
//                    case R.id.worse_rb:
//                        painPoint.setFrequency(PainFrequencyEnum.GettingWorse);
//                        break;
//                    case R.id.better_rb:
//                        painPoint.setFrequency(PainFrequencyEnum.GettingBetter);
//                        break;
//                }
//            }
//        });

//        mAddBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mViewModel.getPainPoint().setPainStrength(mPainStrengthSb.getColorBarPosition());
//                mViewModel.getPainPoint().setColor(mPainStrengthSb.getColor());
//                mViewModel.setPainPointsInDB();
//            }
//        });

        return rootView;
    }

    private void showPainPoints() {
        PainPoint painPoint;

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.RightShoulder);
        mShoulderIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mShoulderIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.RightArm);
        mArmIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mArmIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.RightElbow);
        mElbowIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mElbowIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.RightForearm);
        mForearmIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mForearmIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.RightWrist);
        mWristIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mWristIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.RightPalm);
        mPalmIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mPalmIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.RightFingers);
        mFingersIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mFingersIv.setColorFilter(painPoint.getColor());
        }
    }

    private void onPainPointClicked(@NotNull final View view, final PainLocationEnum painLocationEnum) {
//        mViewModel.getPainPoint().setPainLocation(painLocationEnum);
        showPainPoints();

        if (view.getVisibility() == View.VISIBLE) {
            // Editing:
//            mAddBtn.setText("Edit");
            mViewModel.setPainPoint(new PainPoint(mViewModel.getPainPointsMap().get(painLocationEnum)));
            final PainPoint painPoint = mViewModel.getPainPoint();

            openPainStrengthFragment(painPoint.getPainStrength());

//            mPainStrengthSb.setColorBarPosition(painPoint.getPainStrength());
//
//            if (painPoint.getPainType() != null) {
//                switch (painPoint.getPainType()) {
//                    case Sharp:
//                        mPainTypeRg.check(R.id.sharp_pain_rb);
//                        break;
//                    case Dim:
//                        mPainTypeRg.check(R.id.dim_pain_rb);
//                        break;
//                    case Pressing:
//                        mPainTypeRg.check(R.id.pressing_pain_rb);
//                        break;
//                    case Burning:
//                        mPainTypeRg.check(R.id.burn_pain_rb);
//                        break;
//                    case NoPain:
//                        mPainTypeRg.check(R.id.no_pain_rb);
//                        break;
//                    default:
//                        mPainTypeRg.clearCheck();
//                }
//            } else {
//                mPainTypeRg.clearCheck();
//            }
//
//            if (painPoint.getOtherFeeling() != null) {
//                switch (painPoint.getOtherFeeling()) {
//                    case Bleeding:
//                        mOtherFeelingsRg.check(R.id.bleeding_rb);
//                        break;
//                    case Itching:
//                        mOtherFeelingsRg.check(R.id.itch_rb);
//                        break;
//                    case Sting:
//                        mOtherFeelingsRg.check(R.id.sting_rb);
//                        break;
//                    case Strange:
//                        mOtherFeelingsRg.check(R.id.strange_rb);
//                        break;
//                    case Numb:
//                        mOtherFeelingsRg.check(R.id.numb_rb);
//                        break;
//                    case Other:
//                        mOtherFeelingsRg.check(R.id.other_rb);
//                        break;
//                    default:
//                        mOtherFeelingsRg.clearCheck();
//                }
//            } else {
//                mOtherFeelingsRg.clearCheck();
//            }
//
//            if (painPoint.getFrequency() != null) {
//                switch (painPoint.getFrequency()) {
//                    case Wavy:
//                        mPainFrequencyRg.check(R.id.wavy_rb);
//                        break;
//                    case Static:
//                        mPainFrequencyRg.check(R.id.static_rb);
//                        break;
//                    case GettingWorse:
//                        mPainFrequencyRg.check(R.id.worse_rb);
//                        break;
//                    case GettingBetter:
//                        mPainFrequencyRg.check(R.id.better_rb);
//                        break;
//                    default:
//                        mPainFrequencyRg.clearCheck();
//                }
//            } else {
//                mPainFrequencyRg.clearCheck();
//            }
        } else {
            // Adding:
//            mAddBtn.setText("Add");
//            mSelectedIv.setColorFilter(getContext().getColor(R.color.yellow));
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

        /*final Fragment fragment = getChildFragmentManager().findFragmentByTag(PAIN_STRENGTH_FRAG);*/
        if (/*(fragment != null && fragment.isVisible()) ||*/
                getChildFragmentManager().getBackStackEntryCount() <= 1) {
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
                        BodyPartEnum.RightArm), PAIN_STRENGTH_FRAG)
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
                                BodyPartEnum.RightArm), PAIN_TYPE_FRAG)
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
                                BodyPartEnum.RightArm), OTHER_FEELING_FRAG)
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
                                BodyPartEnum.RightArm), PAIN_FREQUENCY_FRAG)
                .addToBackStack(SUB_FRAGS_STACK)
                .commit();
    }

    /**<------ Pain Frequency Sub Fragment ------>*/
    @Override
    public void onContinueToDescriptionBtnClicked(PainFrequencyEnum painFrequency) {
        mViewModel.getPainPoint().setFrequency(painFrequency);

        DescriptionDialogFragment.newInstance(mViewModel.getPainPoint().getDescription(),
                BodyPartEnum.RightArm)
                .show(getChildFragmentManager().beginTransaction(), DESCRIPTION_FRAG);
    }

    /**<------ Description Sub Fragment ------>*/
    @Override
    public void onFinishBtnClicked(String description) {
        mViewModel.getPainPoint().setDescription(description);
        mViewModel.setPainPointsInDB();
    }
}
