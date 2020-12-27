package com.example.sheba_mental_health_project.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.PainPoint;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.PainFrequencyEnum;
import com.example.sheba_mental_health_project.model.enums.PainLocationEnum;
import com.example.sheba_mental_health_project.model.enums.PainOtherFeelingsEnum;
import com.example.sheba_mental_health_project.model.enums.PainTypeEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.LeftArmViewModel;
import com.google.android.material.button.MaterialButton;
import com.rtugeek.android.colorseekbar.ColorSeekBar;

public class LeftArmFragment extends Fragment {

    private LeftArmViewModel mViewModel;

    private ImageView mShoulderIv;
    private ImageView mElbowIv;
    private ImageView mHandIv;
    private ImageView mSelectedIv;

    private ColorSeekBar mPainStrengthSb;
    private RadioGroup mPainTypeRg;
    private RadioGroup mOtherFeelingsRg;
    private RadioGroup mPainFrequencyRg;

    private MaterialButton mAddBtn;

    private final String TAG = "LeftArmFragment";


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
                requireActivity().onBackPressed();
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
        final View handV = rootView.findViewById(R.id.hand_v);
        mShoulderIv = rootView.findViewById(R.id.shoulder_iv);
        mElbowIv = rootView.findViewById(R.id.elbow_iv);
        mHandIv = rootView.findViewById(R.id.hand_iv);

        mPainStrengthSb = rootView.findViewById(R.id.pain_strength_sb);
        mPainTypeRg = rootView.findViewById(R.id.pain_type_rg);
        mOtherFeelingsRg = rootView.findViewById(R.id.other_feeling_rg);
        mPainFrequencyRg = rootView.findViewById(R.id.pain_frequency_rg);
        mAddBtn = rootView.findViewById(R.id.add_btn);

        showPainPoints();

        mPainStrengthSb.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int colorBarPosition, int alphaBarPosition, int color) {
                if (mSelectedIv != null) {
                    mSelectedIv.setColorFilter(color);
                }
            }
        });

        shoulderV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedIv = mShoulderIv;
                onPainPointClicked(mShoulderIv, PainLocationEnum.Shoulder);
            }
        });

        elbowV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedIv = mElbowIv;
                onPainPointClicked(mElbowIv, PainLocationEnum.Elbow);
            }
        });

        handV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedIv = mHandIv;
                onPainPointClicked(mHandIv, PainLocationEnum.Hand);
            }
        });

        mPainTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final PainPoint painPoint = mViewModel.getPainPoint();
                switch (checkedId) {
                    case R.id.sharp_pain_rb:
                        painPoint.setPainType(PainTypeEnum.Sharp);
                        break;
                    case R.id.dim_pain_rb:
                        painPoint.setPainType(PainTypeEnum.Dim);
                        break;
                    case R.id.pressing_pain_rb:
                        painPoint.setPainType(PainTypeEnum.Pressing);
                        break;
                    case R.id.burn_pain_rb:
                        painPoint.setPainType(PainTypeEnum.Burning);
                        break;
                    case R.id.no_pain_rb:
                        painPoint.setPainType(PainTypeEnum.NoPain);
                        break;
                }
            }
        });

        mOtherFeelingsRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final PainPoint painPoint = mViewModel.getPainPoint();
                switch (mOtherFeelingsRg.getCheckedRadioButtonId()) {
                    case R.id.bleeding_rb:
                        painPoint.setOtherFeeling(PainOtherFeelingsEnum.Bleeding);
                        break;
                    case R.id.itch_rb:
                        painPoint.setOtherFeeling(PainOtherFeelingsEnum.Itching);
                        break;
                    case R.id.sting_rb:
                        painPoint.setOtherFeeling(PainOtherFeelingsEnum.Sting);
                        break;
                    case R.id.strange_rb:
                        painPoint.setOtherFeeling(PainOtherFeelingsEnum.Strange);
                        break;
                    case R.id.numb_rb:
                        painPoint.setOtherFeeling(PainOtherFeelingsEnum.Numb);
                        break;
                    case R.id.other_rb:
                        painPoint.setOtherFeeling(PainOtherFeelingsEnum.Other);
                        break;
                }
            }
        });

        mPainFrequencyRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final PainPoint painPoint = mViewModel.getPainPoint();
                switch (mPainFrequencyRg.getCheckedRadioButtonId()) {
                    case R.id.wavy_rb:
                        painPoint.setFrequency(PainFrequencyEnum.Wavy);
                        break;
                    case R.id.static_rb:
                        painPoint.setFrequency(PainFrequencyEnum.Static);
                        break;
                    case R.id.worse_rb:
                        painPoint.setFrequency(PainFrequencyEnum.GettingWorse);
                        break;
                    case R.id.better_rb:
                        painPoint.setFrequency(PainFrequencyEnum.GettingBetter);
                        break;
                }
            }
        });

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getPainPoint().setPainStrength(mPainStrengthSb.getColorBarPosition());
                mViewModel.getPainPoint().setColor(mPainStrengthSb.getColor());
                mViewModel.setPainPointsInDB();
            }
        });

        return rootView;
    }

    private void showPainPoints() {
        PainPoint painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.Shoulder);
        mShoulderIv.setVisibility(painPoint != null ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mShoulderIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.Elbow);
        mElbowIv.setVisibility(mViewModel.getPainPointsMap()
                .containsKey(PainLocationEnum.Elbow) ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mElbowIv.setColorFilter(painPoint.getColor());
        }

        painPoint = mViewModel.getPainPointsMap().get(PainLocationEnum.Hand);
        mHandIv.setVisibility(mViewModel.getPainPointsMap()
                .containsKey(PainLocationEnum.Hand) ? View.VISIBLE : View.GONE);
        if (painPoint != null) {
            mHandIv.setColorFilter(painPoint.getColor());
        }
    }

    private void onPainPointClicked(final View view, final PainLocationEnum painLocationEnum) {
        mViewModel.getPainPoint().setPainLocation(painLocationEnum);
        showPainPoints();
        if (view.getVisibility() == View.VISIBLE) {
            // Editing:
            mAddBtn.setText("Edit");
            mViewModel.setPainPoint(new PainPoint(mViewModel.getPainPointsMap().get(painLocationEnum)));
            final PainPoint painPoint = mViewModel.getPainPoint();

            mPainStrengthSb.setColorBarPosition(painPoint.getPainStrength());

            if (painPoint.getPainType() != null) {
                switch (painPoint.getPainType()) {
                    case Sharp:
                        mPainTypeRg.check(R.id.sharp_pain_rb);
                        break;
                    case Dim:
                        mPainTypeRg.check(R.id.dim_pain_rb);
                        break;
                    case Pressing:
                        mPainTypeRg.check(R.id.pressing_pain_rb);
                        break;
                    case Burning:
                        mPainTypeRg.check(R.id.burn_pain_rb);
                        break;
                    case NoPain:
                        mPainTypeRg.check(R.id.no_pain_rb);
                        break;
                    default:
                        mPainTypeRg.clearCheck();
                }
            } else {
                mPainTypeRg.clearCheck();
            }

            if (painPoint.getOtherFeeling() != null) {
                switch (painPoint.getOtherFeeling()) {
                    case Bleeding:
                        mOtherFeelingsRg.check(R.id.bleeding_rb);
                        break;
                    case Itching:
                        mOtherFeelingsRg.check(R.id.itch_rb);
                        break;
                    case Sting:
                        mOtherFeelingsRg.check(R.id.sting_rb);
                        break;
                    case Strange:
                        mOtherFeelingsRg.check(R.id.strange_rb);
                        break;
                    case Numb:
                        mOtherFeelingsRg.check(R.id.numb_rb);
                        break;
                    case Other:
                        mOtherFeelingsRg.check(R.id.other_rb);
                        break;
                    default:
                        mOtherFeelingsRg.clearCheck();
                }
            } else {
                mOtherFeelingsRg.clearCheck();
            }

            if (painPoint.getFrequency() != null) {
                switch (painPoint.getFrequency()) {
                    case Wavy:
                        mPainFrequencyRg.check(R.id.wavy_rb);
                        break;
                    case Static:
                        mPainFrequencyRg.check(R.id.static_rb);
                        break;
                    case GettingWorse:
                        mPainFrequencyRg.check(R.id.worse_rb);
                        break;
                    case GettingBetter:
                        mPainFrequencyRg.check(R.id.better_rb);
                        break;
                    default:
                        mPainFrequencyRg.clearCheck();
                }
            } else {
                mPainFrequencyRg.clearCheck();
            }
        } else {
            // Adding:
            mAddBtn.setText("Add");
            mSelectedIv.setColorFilter(mPainStrengthSb.getColor());
        }
        view.setVisibility(View.VISIBLE);
    }
}
