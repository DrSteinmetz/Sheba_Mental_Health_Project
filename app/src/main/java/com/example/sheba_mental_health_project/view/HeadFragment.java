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
import com.example.sheba_mental_health_project.viewmodel.HeadViewModel;

import org.jetbrains.annotations.NotNull;

public class HeadFragment extends Fragment
        implements PainStrengthSubFragment.PainStrengthSubFragmentInterface,
        PainTypeSubFragment.PainTypeSubFragmentInterface,
        OtherFeelingSubFragment.OtherFeelingSubFragmentInterface,
        PainFrequencySubFragment.PainFrequencySubFragmentInterface,
        DescriptionDialogFragment.AddDescriptionFragmentInterface,
        LeftOrRightDialogFragment.LeftOrRightDialogFragmentInterface {

    private HeadViewModel mViewModel;

    private ImageView mScalpIv;
    private ImageView mForeheadIv;
    private ImageView mRightEyeIv;
    private ImageView mLeftEyeIv;
    private ImageView mNoseIv;
    private ImageView mMouthIv;
    private ImageView mNeckIv;

    private ImageView mSelectedIv;

    private boolean mIsBoth = false;

    private final Animation alphaAnimation = new AlphaAnimation(1, 0);

    private final String SUB_FRAGS_STACK = "Head_Fragments_Stack";

    private final String PAIN_STRENGTH_FRAG = "Pain_Strength_Fragment";
    private final String PAIN_TYPE_FRAG = "Pain_Type_Fragment";
    private final String OTHER_FEELING_FRAG = "Other_Feeling_Fragment";
    private final String PAIN_FREQUENCY_FRAG = "Pain_Frequency_Fragment";
    private final String DESCRIPTION_FRAG = "Description_Dialog_Fragment";

    private final String TAG = "HeadFragment";


    public static HeadFragment newInstance() {
        return new HeadFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.Head)).get(HeadViewModel.class);

        final Observer<PainPoint> onSetPainPointsSucceed = new Observer<PainPoint>() {
            @Override
            public void onChanged(PainPoint painPoint) {
                getChildFragmentManager().popBackStack(SUB_FRAGS_STACK,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getActivity().onBackPressed();
                getActivity().onBackPressed();

                if (mIsBoth) {
                    switch (mViewModel.getPainPoint().getPainLocation()) {
                        case LeftEye:
                            mIsBoth = false;
                            mViewModel.getPainPoint().setPainLocation(PainLocationEnum.RightEye);
                            mViewModel.setPainPointsInDB();
                            break;
                        case RightEye:
                            mIsBoth = false;
                            mViewModel.getPainPoint().setPainLocation(PainLocationEnum.LeftEye);
                            mViewModel.setPainPointsInDB();
                            break;
                        case LeftEar:
                            mIsBoth = false;
                            mViewModel.getPainPoint().setPainLocation(PainLocationEnum.RightEar);
                            mViewModel.setPainPointsInDB();
                            break;
                        case RightEar:
                            mIsBoth = false;
                            mViewModel.getPainPoint().setPainLocation(PainLocationEnum.LeftEar);
                            mViewModel.setPainPointsInDB();
                            break;
                        default:
                            mIsBoth = false;
                            break;
                    }
                }
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
        final View rootView = inflater.inflate(R.layout.head_fragment, container, false);

        final View scalpV = rootView.findViewById(R.id.scalp_v);
        final View foreheadV = rootView.findViewById(R.id.forehead_v);
        final View rightEyeV = rootView.findViewById(R.id.right_eye_v);
        final View leftEyeV = rootView.findViewById(R.id.left_eye_v);
        final View noseV = rootView.findViewById(R.id.nose_v);
        final View mouthV = rootView.findViewById(R.id.mouth_v);
        final View neckV = rootView.findViewById(R.id.neck_v);
        mScalpIv = rootView.findViewById(R.id.scalp_iv);
        mForeheadIv = rootView.findViewById(R.id.forehead_iv);
        mRightEyeIv = rootView.findViewById(R.id.right_eye_iv);
        mLeftEyeIv = rootView.findViewById(R.id.left_eye_iv);
        mNoseIv = rootView.findViewById(R.id.nose_iv);
        mMouthIv = rootView.findViewById(R.id.mouth_iv);
        mNeckIv = rootView.findViewById(R.id.neck_iv);

        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setDuration(700);

        showPainPoints();

        scalpV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mScalpIv, PainLocationEnum.Scalp);
            }
        });

        foreheadV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mForeheadIv, PainLocationEnum.Forehead);
            }
        });

        rightEyeV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mRightEyeIv, PainLocationEnum.RightEye);
                LeftOrRightDialogFragment.newInstance(BodyPartEnum.Head)
                        .show(getChildFragmentManager().beginTransaction(), DESCRIPTION_FRAG);
            }
        });

        leftEyeV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mLeftEyeIv, PainLocationEnum.LeftEye);
                LeftOrRightDialogFragment.newInstance(BodyPartEnum.Head)
                        .show(getChildFragmentManager().beginTransaction(), DESCRIPTION_FRAG);
            }
        });

        noseV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mNoseIv, PainLocationEnum.Nose);
            }
        });

        mouthV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mMouthIv, PainLocationEnum.Mouth);
            }
        });

        neckV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mNeckIv, PainLocationEnum.Neck);
            }
        });

        return rootView;
    }

    private void showPainPoints() {
        PainPoint painPoint;

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.Scalp);
        mScalpIv.setVisibility(painPoint != null ? View.VISIBLE : View.INVISIBLE);
        if (painPoint != null) {
            mScalpIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.Forehead);
        mForeheadIv.setVisibility(painPoint != null ? View.VISIBLE : View.INVISIBLE);
        if (painPoint != null) {
            mForeheadIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.RightEye);
        mRightEyeIv.setVisibility(painPoint != null ? View.VISIBLE : View.INVISIBLE);
        if (painPoint != null) {
            mRightEyeIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.LeftEye);
        mLeftEyeIv.setVisibility(painPoint != null ? View.VISIBLE : View.INVISIBLE);
        if (painPoint != null) {
            mLeftEyeIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.Nose);
        mNoseIv.setVisibility(painPoint != null ? View.VISIBLE : View.INVISIBLE);
        if (painPoint != null) {
            mNoseIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.Mouth);
        mMouthIv.setVisibility(painPoint != null ? View.VISIBLE : View.INVISIBLE);
        if (painPoint != null) {
            mMouthIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.Neck);
        mNeckIv.setVisibility(painPoint != null ? View.VISIBLE : View.INVISIBLE);
        if (painPoint != null) {
            mNeckIv.setColorFilter(painPoint.getColor());
        }
    }

    private void onPainPointClicked(@NotNull final View view, final PainLocationEnum painLocationEnum) {
        showPainPoints();

        if (view.getVisibility() == View.VISIBLE) {
            mViewModel.setPainPoint(new PainPoint(mViewModel.getPainPointsMap().get(painLocationEnum)));
            final PainPoint painPoint = mViewModel.getPainPoint();

            openPainStrengthFragment(painPoint.getPainStrength());
        } else {
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
                        BodyPartEnum.Head), PAIN_STRENGTH_FRAG)
                .addToBackStack(SUB_FRAGS_STACK)
                .commit();
    }

    /**<------ Left Or Right Dialog Fragment ------>*/
    @Override
    public void onOkBtnClicked(boolean isBoth) {
        mIsBoth = isBoth;
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
                                BodyPartEnum.Head), PAIN_TYPE_FRAG)
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
                                mViewModel.getPainPoint().getPainLocation(),
                                BodyPartEnum.Head), OTHER_FEELING_FRAG)
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
                                BodyPartEnum.Head), PAIN_FREQUENCY_FRAG)
                .addToBackStack(SUB_FRAGS_STACK)
                .commit();
    }

    /**<------ Pain Frequency Sub Fragment ------>*/
    @Override
    public void onContinueToDescriptionBtnClicked(PainFrequencyEnum painFrequency) {
        mViewModel.getPainPoint().setFrequency(painFrequency);

        DescriptionDialogFragment.newInstance(mViewModel.getPainPoint().getDescription(),
                BodyPartEnum.Head)
                .show(getChildFragmentManager().beginTransaction(), DESCRIPTION_FRAG);
    }

    /**<------ Description Sub Fragment ------>*/
    @Override
    public void onFinishBtnClicked(String description) {
        mViewModel.getPainPoint().setDescription(description);
        mViewModel.setPainPointsInDB();
    }
}
