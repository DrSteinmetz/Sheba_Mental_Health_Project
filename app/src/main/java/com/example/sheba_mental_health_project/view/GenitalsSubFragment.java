package com.example.sheba_mental_health_project.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.PainPoint;
import com.example.sheba_mental_health_project.model.enums.BodyPartEnum;
import com.example.sheba_mental_health_project.model.enums.PainLocationEnum;
import com.google.android.material.button.MaterialButton;

import java.util.EnumMap;

public class GenitalsSubFragment extends Fragment {

    private RadioGroup mGenderRg;
    private RadioGroup mMaleRg;
    private RadioGroup mFemaleRg;
    private TextView mChooseTitleTv;
    private LinearLayout mMaleLayout;
    private LinearLayout mFemaleLayout;
    private RadioButton mPenisRb;
    private RadioButton mTesticlesRb;
    private RadioButton mVaginaRb;
    private MaterialButton mContinueBtn;

    private PainLocationEnum mSelectedLocation;

    private EnumMap<PainLocationEnum, PainPoint> mPainPointsGenitalsMap = new EnumMap<>(PainLocationEnum.class);

    private final String TAG = "GenitalsSubFragment";


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (GenitalsFragment) getParentFragment();
        } catch (Exception ex) {
            throw new ClassCastException("The fragment must implement GenitalsSubFragmentInterface Listener!");
        }
    }

    public static GenitalsSubFragment newInstance(EnumMap<PainLocationEnum, PainPoint> mPainPointsGenitalsMap,
                                                  final BodyPartEnum fragmentName) {
        GenitalsSubFragment fragment = new GenitalsSubFragment();
        final Bundle args = new Bundle();
        args.putSerializable("genitals_pain_points", mPainPointsGenitalsMap);
        args.putSerializable("fragment_name", fragmentName);
        fragment.setArguments(args);
        return fragment;
    }

    public interface GenitalsSubFragmentInterface {
        void onContinueToStrengthBtnClicked(PainLocationEnum painLocationEnum ,int painStrength);
        void onSelectedPainPointColor(int color);
    }

    private GenitalsSubFragment.GenitalsSubFragmentInterface listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_genitals_sub, container, false);

        mGenderRg = rootView.findViewById(R.id.gender_Rg);
        mMaleRg = rootView.findViewById(R.id.male_Rg);
        mFemaleRg = rootView.findViewById(R.id.female_Rg);
        mChooseTitleTv = rootView.findViewById(R.id.choose_title_tv);
        mMaleLayout = rootView.findViewById(R.id.male_linear);
        mFemaleLayout = rootView.findViewById(R.id.female_linear);
        mContinueBtn = rootView.findViewById(R.id.continue_btn);
        mPenisRb = rootView.findViewById(R.id.penis);
        mTesticlesRb = rootView.findViewById(R.id.testicles);
        mVaginaRb = rootView.findViewById(R.id.vagina);

        if (getArguments() != null) {
            mPainPointsGenitalsMap  = (EnumMap<PainLocationEnum, PainPoint> ) getArguments()
                    .getSerializable("genitals_pain_points");

            if (mPainPointsGenitalsMap.containsKey(PainLocationEnum.Penis)) {
                final int color = mPainPointsGenitalsMap.get(PainLocationEnum.Penis).getColor();
                listener.onSelectedPainPointColor(color);
            }

            if (mPainPointsGenitalsMap.containsKey(PainLocationEnum.Vagina)) {
                final int color = mPainPointsGenitalsMap.get(PainLocationEnum.Vagina).getColor();
                listener.onSelectedPainPointColor(color);
            }

            if (mPainPointsGenitalsMap.containsKey(PainLocationEnum.Penis)) {
                final PainPoint painPoint = mPainPointsGenitalsMap.get(PainLocationEnum.Penis);
                final int color = painPoint.getColor();
                final Drawable wrappedDrawable = setGenitalsPainPointsColor(color, R.drawable.ic_penis);
                mPenisRb.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        null, wrappedDrawable);
            }

            if (mPainPointsGenitalsMap.containsKey(PainLocationEnum.Testicles)) {
                final PainPoint painPoint = mPainPointsGenitalsMap.get(PainLocationEnum.Testicles);
                final int color = painPoint.getColor();
                final Drawable wrappedDrawable = setGenitalsPainPointsColor(color, R.drawable.ic_testicles);
                mTesticlesRb.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        null, wrappedDrawable);
            }

            if (mPainPointsGenitalsMap.containsKey(PainLocationEnum.Vagina)) {
                final PainPoint painPoint = mPainPointsGenitalsMap.get(PainLocationEnum.Vagina);
                final int color = painPoint.getColor();
                final Drawable wrappedDrawable = setGenitalsPainPointsColor(color, R.drawable.ic_vagina);
                mVaginaRb.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        null, wrappedDrawable);
            }
        }

        mMaleRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.penis:
                       mSelectedLocation = PainLocationEnum.Penis;
                       break;
                    case R.id.testicles:
                        mSelectedLocation = PainLocationEnum.Testicles;
                        break;
                }
            }
        });

        mGenderRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mChooseTitleTv.setVisibility(View.VISIBLE);
                mContinueBtn.setVisibility(View.VISIBLE);
                switch (checkedId) {
                    case R.id.male_Rb:
                      mMaleLayout.setVisibility(View.VISIBLE);
                      mFemaleLayout.setVisibility(View.INVISIBLE);
                      mPenisRb.setChecked(true);
                      mSelectedLocation = PainLocationEnum.Penis;
                      break;
                    case R.id.female_Rb:
                        mFemaleLayout.setVisibility(View.VISIBLE);
                        mMaleLayout.setVisibility(View.INVISIBLE);
                        mVaginaRb.setChecked(true);
                        mSelectedLocation = PainLocationEnum.Vagina;
                        break;
                }
            }
        });

        mContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPainPointsGenitalsMap.containsKey(mSelectedLocation)) {
                    final int painStrength = mPainPointsGenitalsMap.get(mSelectedLocation).getPainStrength();
                    listener.onContinueToStrengthBtnClicked(mSelectedLocation, painStrength);
                } else {
                    listener.onContinueToStrengthBtnClicked(mSelectedLocation, 0);
                }
            }
        });

        return rootView;
    }

    public Drawable setGenitalsPainPointsColor(int color, int drawable) {
        final Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(), drawable);
        final Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        return  wrappedDrawable;
    }
}
