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
import com.example.sheba_mental_health_project.viewmodel.CenterOfMassViewModel;

import org.jetbrains.annotations.NotNull;

public class CenterOfMassFragment extends Fragment
        implements PainStrengthSubFragment.PainStrengthSubFragmentInterface,
        PainTypeSubFragment.PainTypeSubFragmentInterface,
        OtherFeelingSubFragment.OtherFeelingSubFragmentInterface,
        PainFrequencySubFragment.PainFrequencySubFragmentInterface,
        DescriptionDialogFragment.AddDescriptionFragmentInterface {

    private CenterOfMassViewModel mViewModel;

    private ImageView mChestIv;
    private ImageView mUpperLeftIv;
    private ImageView mUpperRightIv;
    private ImageView mNavelIv;
    private ImageView mLowerLeftIv;
    private ImageView mLowerRightIv;

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

        final Observer<PainPoint> onSetPainPointsSucceed = new Observer<PainPoint>() {
            @Override
            public void onChanged(PainPoint painPoint) {
                getChildFragmentManager().popBackStack(SUB_FRAGS_STACK,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
        final View rootView = inflater.inflate(R.layout.center_of_mass_fragment, container, false);

        final View chestV = rootView.findViewById(R.id.chest_v);
        final View upperLeftV = rootView.findViewById(R.id.upper_left_v);
        final View upperRightV = rootView.findViewById(R.id.upper_right_v);
        final View navelV = rootView.findViewById(R.id.navel_v);
        final View lowerLeftV = rootView.findViewById(R.id.lower_left_v);
        final View lowerRightV = rootView.findViewById(R.id.lower_right_v);
        mChestIv = rootView.findViewById(R.id.chest_iv);
        mUpperLeftIv = rootView.findViewById(R.id.upper_left_iv);
        mUpperRightIv = rootView.findViewById(R.id.upper_right_iv);
        mNavelIv = rootView.findViewById(R.id.navel_iv);
        mLowerLeftIv = rootView.findViewById(R.id.lower_left_iv);
        mLowerRightIv = rootView.findViewById(R.id.lower_right_iv);

        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setDuration(700);

        showPainPoints();

        chestV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mChestIv, PainLocationEnum.Chest);
            }
        });

        upperLeftV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mUpperLeftIv, PainLocationEnum.UpperLeftAbdomen);
            }
        });

        upperRightV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mUpperRightIv, PainLocationEnum.UpperRightAbdomen);
            }
        });

        navelV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mNavelIv, PainLocationEnum.Navel);
            }
        });

        lowerLeftV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mLowerLeftIv, PainLocationEnum.LowerLeftAbdomen);
            }
        });

        lowerRightV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mLowerRightIv, PainLocationEnum.LowerRightAbdomen);
            }
        });

        return rootView;
    }

    private void showPainPoints() {
        PainPoint painPoint;

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.Chest);
        mChestIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mChestIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.UpperLeftAbdomen);
        mUpperLeftIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mUpperLeftIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.UpperRightAbdomen);
        mUpperRightIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mUpperRightIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.Navel);
        mNavelIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mNavelIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.LowerLeftAbdomen);
        mLowerLeftIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mLowerLeftIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.LowerRightAbdomen);
        mLowerRightIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mLowerRightIv.setColorFilter(painPoint.getColor());
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

        final PainPoint painPoint = mViewModel.getPainPoint();
        final String otherFeeling = painPoint.getOtherFeelingLocalString(getContext());
        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container, OtherFeelingSubFragment.newInstance(otherFeeling,
                                painPoint.getPainLocation(),
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
