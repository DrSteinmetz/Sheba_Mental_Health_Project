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
import com.example.sheba_mental_health_project.viewmodel.GenitalsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

public class GenitalsFragment extends Fragment
        implements PainStrengthSubFragment.PainStrengthSubFragmentInterface,
        PainTypeSubFragment.PainTypeSubFragmentInterface,
        OtherFeelingSubFragment.OtherFeelingSubFragmentInterface,
        PainFrequencySubFragment.PainFrequencySubFragmentInterface,
        DescriptionDialogFragment.AddDescriptionFragmentInterface,
        GenitalsSubFragment.GenitalsSubFragmentInterface{

    private GenitalsViewModel mViewModel;

    private ImageView mPrivatePartIv;

    private FloatingActionButton deletePainPointFab;

    private ImageView mSelectedIv;

    private final Animation alphaAnimation = new AlphaAnimation(1, 0);

    private final String SUB_FRAGS_STACK = "Genitals_Fragments_Stack";

    private final String PAIN_STRENGTH_FRAG = "Pain_Strength_Fragment";
    private final String PAIN_TYPE_FRAG = "Pain_Type_Fragment";
    private final String OTHER_FEELING_FRAG = "Other_Feeling_Fragment";
    private final String PAIN_FREQUENCY_FRAG = "Pain_Frequency_Fragment";
    private final String DESCRIPTION_FRAG = "Description_Dialog_Fragment";
    private final String GENITALS_SUB_FRAG = "Genitals_Sub_Fragment";

    private final String TAG = "GenitalsFragment";


    public static GenitalsFragment newInstance() {
        return new GenitalsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.Genitals)).get(GenitalsViewModel.class);

        final Observer<PainPoint> onSetPainPointsSucceed = new Observer<PainPoint>() {
            @Override
            public void onChanged(PainPoint painPoint) {
                getChildFragmentManager().popBackStack(SUB_FRAGS_STACK,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getActivity().onBackPressed();
                getActivity().onBackPressed();

                if(painPoint.getPainLocation() == PainLocationEnum.Penis ||
                        painPoint.getPainLocation() ==  PainLocationEnum.Testicles ||
                        painPoint.getPainLocation() == PainLocationEnum.Vagina){
                    mViewModel.getPainPoint().setPainLocation(PainLocationEnum.PrivatePart);
                    mViewModel.setPainPointsInDB();
                }
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

                    if (mSelectedIv != null) {
                        mSelectedIv.setAnimation(null);
                        if (mSelectedIv.getId() == R.id.mouth_iv) {
                            /*if (mViewModel.getPainPointsMouthMap().isEmpty()) {
                                mSelectedIv.setVisibility(View.GONE);
                            }*/
                        } else {
                            mSelectedIv.setVisibility(View.GONE);
                        }
                    }
                    mSelectedIv = null;
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
        final View rootView = inflater.inflate(R.layout.genitals_fragment, container, false);

        final View genitalsV = rootView.findViewById(R.id.genitals_v);
        mPrivatePartIv = rootView.findViewById(R.id.private_part_iv);

        deletePainPointFab = rootView.findViewById(R.id.delete_pain_point_fab);

        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setDuration(700);

        genitalsV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPainPointViewClicked(mPrivatePartIv, PainLocationEnum.PrivatePart);
            }
        });

        showPainPoints();

        return rootView;
    }

    private void showPainPoints() {
        PainPoint painPoint;

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.PrivatePart);
        mPrivatePartIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mPrivatePartIv.setColorFilter(painPoint.getColor());
        }
    }

    private void onPainPointClicked(@NotNull final View view, final PainLocationEnum painLocationEnum) {
        showPainPoints();

        if (view.getVisibility() == View.VISIBLE) {
            // Editing
            mViewModel.setPainPoint(new PainPoint(mViewModel.getPainPointsMap().get(painLocationEnum)));
        } else {
            // Adding
            mViewModel.setPainPoint(new PainPoint(painLocationEnum));
        }
        openGenitalsSubFragment(mViewModel.getPainPointsMap());

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

    private void openGenitalsSubFragment(EnumMap<PainLocationEnum, PainPoint> painPointsGenitalsMap) {
        getChildFragmentManager().popBackStack(SUB_FRAGS_STACK, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container, GenitalsSubFragment.newInstance(painPointsGenitalsMap,
                        BodyPartEnum.Genitals), GENITALS_SUB_FRAG)
                .addToBackStack(SUB_FRAGS_STACK)
                .commit();
    }

    private void openPainStrengthFragment(final int position) {
        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container, PainStrengthSubFragment.newInstance(position,
                        BodyPartEnum.Genitals), PAIN_STRENGTH_FRAG)
                .addToBackStack(SUB_FRAGS_STACK)
                .commit();

    }

    /**<------ Genitals Sub Fragment ------>*/
    @Override
    public void onContinueToStrengthBtnClicked(PainLocationEnum painLocationEnum, int painStrength) {
        mViewModel.getPainPoint().setPainLocation(painLocationEnum);
        openPainStrengthFragment(painStrength);
    }

    @Override
    public void onSelectedPainPointColor(int color) {
        mSelectedIv.setColorFilter(color);
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
                                BodyPartEnum.Genitals), PAIN_TYPE_FRAG)
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
                                BodyPartEnum.Genitals), OTHER_FEELING_FRAG)
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
                                BodyPartEnum.Genitals), PAIN_FREQUENCY_FRAG)
                .addToBackStack(SUB_FRAGS_STACK)
                .commit();
    }

    /**<------ Pain Frequency Sub Fragment ------>*/
    @Override
    public void onContinueToDescriptionBtnClicked(PainFrequencyEnum painFrequency) {
        mViewModel.getPainPoint().setFrequency(painFrequency);

        DescriptionDialogFragment.newInstance(mViewModel.getPainPoint().getDescription(),
                BodyPartEnum.Genitals)
                .show(getChildFragmentManager().beginTransaction(), DESCRIPTION_FRAG);
    }

    /**<------ Description Sub Fragment ------>*/
    @Override
    public void onFinishBtnClicked(String description) {
        mViewModel.getPainPoint().setDescription(description);
        mViewModel.setPainPointsInDB();
    }


}
