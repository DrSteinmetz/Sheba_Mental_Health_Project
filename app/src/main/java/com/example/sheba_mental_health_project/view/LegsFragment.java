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
import com.example.sheba_mental_health_project.viewmodel.LegsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

public class LegsFragment extends Fragment implements PainStrengthSubFragment.PainStrengthSubFragmentInterface,
        PainTypeSubFragment.PainTypeSubFragmentInterface,
        OtherFeelingSubFragment.OtherFeelingSubFragmentInterface,
        PainFrequencySubFragment.PainFrequencySubFragmentInterface,
        DescriptionDialogFragment.AddDescriptionFragmentInterface {

    private LegsViewModel mViewModel;

    private ImageView mRightThighIv;
    private ImageView mLeftThighIv;
    private ImageView mRightKneeIv;
    private ImageView mLeftKneeIv;
    private ImageView mRightShinIv;
    private ImageView mLeftShinIv;
    private ImageView mRightFootIv;
    private ImageView mLeftFootIv;
    private ImageView mRightToesIv;
    private ImageView mLeftToesIv;

    private FloatingActionButton deletePainPointFab;

    private ImageView mSelectedIv;

    private final Animation alphaAnimation = new AlphaAnimation(1, 0);

    private final String SUB_FRAGS_STACK = "Legs_Fragments_Stack";

    private final String PAIN_STRENGTH_FRAG = "Pain_Strength_Fragment";
    private final String PAIN_TYPE_FRAG = "Pain_Type_Fragment";
    private final String OTHER_FEELING_FRAG = "Other_Feeling_Fragment";
    private final String PAIN_FREQUENCY_FRAG = "Pain_Frequency_Fragment";
    private final String DESCRIPTION_FRAG = "Description_Dialog_Fragment";

    private final String TAG = "LegsFragment";


    public static LegsFragment newInstance() {
        return new LegsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.Legs)).get(LegsViewModel.class);

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

        final Observer<PainPoint> onDeletePainPointSucceed = new Observer<PainPoint>() {
            @Override
            public void onChanged(PainPoint painPoint) {
                if (getView() != null) {
                    Snackbar.make(getView(), getString(R.string.pain_point_deleted_prompt),
                            Snackbar.LENGTH_LONG).show();
                }
            }
        };

        final Observer<String> onDeletePainPointFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getSetPainPointsSucceed().observe(this, onSetPainPointsSucceed);
        mViewModel.getSetPainPointsFailed().observe(this, onSetPainPointsFailed);
        mViewModel.getDeletePainPointSucceed().observe(this, onDeletePainPointSucceed);
        mViewModel.getDeletePainPointFailed().observe(this, onDeletePainPointFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.legs_fragment, container, false);

        final View rightThighV = rootView.findViewById(R.id.right_thigh_v);
        final View leftThighV = rootView.findViewById(R.id.left_thigh_v);
        final View rightKneeV = rootView.findViewById(R.id.right_knee_v);
        final View leftKneeV = rootView.findViewById(R.id.left_knee_v);
        final View rightShinV = rootView.findViewById(R.id.right_shin_v);
        final View leftShinV = rootView.findViewById(R.id.left_shin_v);
        final View rightFootV = rootView.findViewById(R.id.right_foot_v);
        final View leftFootV = rootView.findViewById(R.id.left_foot_v);
        final View rightToesV = rootView.findViewById(R.id.right_toes_v);
        final View leftToesV = rootView.findViewById(R.id.left_toes_v);
        mRightThighIv = rootView.findViewById(R.id.right_thigh_iv);
        mLeftThighIv = rootView.findViewById(R.id.left_thigh_iv);
        mRightKneeIv = rootView.findViewById(R.id.right_knee_iv);
        mLeftKneeIv = rootView.findViewById(R.id.left_knee_iv);
        mRightShinIv = rootView.findViewById(R.id.right_shin_iv);
        mLeftShinIv = rootView.findViewById(R.id.left_shin_iv);
        mRightFootIv = rootView.findViewById(R.id.right_foot_iv);
        mLeftFootIv = rootView.findViewById(R.id.left_foot_iv);
        mRightToesIv = rootView.findViewById(R.id.right_toes_iv);
        mLeftToesIv = rootView.findViewById(R.id.left_toes_iv);

        deletePainPointFab = rootView.findViewById(R.id.delete_pain_point_fab);

        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setDuration(700);

        showPainPoints();

        rightThighV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mRightThighIv, PainLocationEnum.RightThigh);
            }
        });

        leftThighV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mLeftThighIv, PainLocationEnum.LeftThigh);
            }
        });

        rightKneeV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mRightKneeIv, PainLocationEnum.RightKnee);
            }
        });

        leftKneeV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mLeftKneeIv, PainLocationEnum.LeftKnee);
            }
        });

        rightShinV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mRightShinIv, PainLocationEnum.RightShin);
            }
        });

        leftShinV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mLeftShinIv, PainLocationEnum.LeftShin);
            }
        });

        rightFootV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mRightFootIv, PainLocationEnum.RightFoot);
            }
        });

        leftFootV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mLeftFootIv, PainLocationEnum.LeftFoot);
            }
        });

        rightToesV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mRightToesIv, PainLocationEnum.RightToes);
            }
        });

        leftToesV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mLeftToesIv, PainLocationEnum.LeftToes);
            }
        });


        deletePainPointFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WarningDialog warningDialog = new WarningDialog(getContext());
                warningDialog.setPromptText(getString(R.string.pain_point_deletion_prompt));
                warningDialog.setOnActionListener(new WarningDialog.WarningDialogActionInterface() {
                    @Override
                    public void onYesBtnClicked() {
                        getChildFragmentManager().popBackStack(SUB_FRAGS_STACK,
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        if (mSelectedIv != null) {
                            mSelectedIv.setAnimation(null);
                            mSelectedIv.setVisibility(View.GONE);
                        }
                        mSelectedIv = null;
                        deletePainPointFab.hide();

                        mViewModel.deletePainPoint();
                    }

                    @Override
                    public void onNoBtnClicked() {}
                });
                warningDialog.show();
            }
        });

        return rootView;
    }

    private void showPainPoints() {
        PainPoint painPoint;

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.RightThigh);
        mRightThighIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mRightThighIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.LeftThigh);
        mLeftThighIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mLeftThighIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.RightKnee);
        mRightKneeIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mRightKneeIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.LeftKnee);
        mLeftKneeIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mLeftKneeIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.RightShin);
        mRightShinIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mRightShinIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.LeftShin);
        mLeftShinIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mLeftShinIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.RightFoot);
        mRightFootIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mRightFootIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.LeftFoot);
        mLeftFootIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mLeftFootIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.RightToes);
        mRightToesIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mRightToesIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.LeftToes);
        mLeftToesIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mLeftToesIv.setColorFilter(painPoint.getColor());
        }
    }

    private void onPainPointClicked(@NotNull final View view, final PainLocationEnum painLocationEnum) {

        showPainPoints();

        if (view.getVisibility() == View.VISIBLE) {
            mViewModel.setPainPoint(new PainPoint(mViewModel.getPainPointsMap().get(painLocationEnum)));
            final PainPoint painPoint = mViewModel.getPainPoint();

            openPainStrengthFragment(painPoint.getPainStrength());
            deletePainPointFab.show();
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
            deletePainPointFab.hide();
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
                    deletePainPointFab.hide();
                    onPainPointClicked(view, painLocationEnum);
                }

                @Override
                public void onNoBtnClicked() {}
            });
            warningDialog.show();
        }
    }

    private void openPainStrengthFragment(final int position) {
        getChildFragmentManager().popBackStack(SUB_FRAGS_STACK, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container, PainStrengthSubFragment.newInstance(position,
                        BodyPartEnum.Legs), PAIN_STRENGTH_FRAG)
                .addToBackStack(SUB_FRAGS_STACK)
                .commit();
    }

    @Override
    public void onFinishBtnClicked(String description) {
        mViewModel.getPainPoint().setDescription(description);
        mViewModel.setPainPointsInDB();
    }

    @Override
    public void onContinueToPainFrequencyBtnClicked(PainOtherFeelingsEnum otherFeeling) {
        mViewModel.getPainPoint().setOtherFeeling(otherFeeling);

        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container,
                        PainFrequencySubFragment.newInstance(mViewModel.getPainPoint().getFrequency(),
                                BodyPartEnum.Legs), PAIN_FREQUENCY_FRAG)
                .addToBackStack(SUB_FRAGS_STACK)
                .commit();
    }

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
                                BodyPartEnum.Legs), PAIN_TYPE_FRAG)
                .addToBackStack(SUB_FRAGS_STACK)
                .commit();
    }

    @Override
    public void onContinueToOtherFeelingsBtnClicked(PainTypeEnum painType) {
        mViewModel.getPainPoint().setPainType(painType);

        final String otherFeeling = mViewModel.getPainPoint()
                .getOtherFeelingLocalString(getContext());
        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container, OtherFeelingSubFragment.newInstance(otherFeeling,
                                BodyPartEnum.Legs), OTHER_FEELING_FRAG)
                .addToBackStack(SUB_FRAGS_STACK)
                .commit();
    }

    @Override
    public void onContinueToDescriptionBtnClicked(PainFrequencyEnum painFrequency) {
        mViewModel.getPainPoint().setFrequency(painFrequency);

        DescriptionDialogFragment.newInstance(mViewModel.getPainPoint().getDescription(),
                BodyPartEnum.Legs)
                .show(getChildFragmentManager().beginTransaction(), DESCRIPTION_FRAG);
    }
}
