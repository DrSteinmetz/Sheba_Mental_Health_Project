package com.example.sheba_mental_health_project.view;

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

    private Context mContext;

    private RadioGroup genderRg;
    private RadioGroup maleRg;
    private RadioGroup femaleRg;
    private TextView chooseTitleTv;
    private LinearLayout maleLayout;
    private LinearLayout femaleLayout;
    private MaterialButton continueBtn;
    private RadioButton penisRb;
    private RadioButton testiclesRb;
    private RadioButton vaginaRb;
    private PainLocationEnum selectedLocation;

    private EnumMap<PainLocationEnum, PainPoint> mPainPointsGenitalsMap = new EnumMap<>(PainLocationEnum.class);

    private final String TAG = "GenitalsSubFragment";


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (GenitalsFragment) getParentFragment();
        mContext = context;
    }

    public static GenitalsSubFragment newInstance(EnumMap<PainLocationEnum, PainPoint> mPainPointsGenitalsMap,
                                                  final BodyPartEnum fragmentName) {
        GenitalsSubFragment fragment = new GenitalsSubFragment();
        Bundle args = new Bundle();
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

        genderRg = rootView.findViewById(R.id.gender_Rg);
        maleRg = rootView.findViewById(R.id.male_Rg);
        femaleRg = rootView.findViewById(R.id.female_Rg);
        chooseTitleTv = rootView.findViewById(R.id.choose_title_tv);
        maleLayout = rootView.findViewById(R.id.male_linear);
        femaleLayout = rootView.findViewById(R.id.female_linear);
        continueBtn = rootView.findViewById(R.id.continue_btn);
        penisRb = rootView.findViewById(R.id.penis);
        testiclesRb = rootView.findViewById(R.id.testicles);
        vaginaRb = rootView.findViewById(R.id.vagina);

        if (getArguments() != null) {
            mPainPointsGenitalsMap  = (EnumMap<PainLocationEnum, PainPoint> ) getArguments()
                    .getSerializable("genitals_pain_points");

            if (mPainPointsGenitalsMap.containsKey(PainLocationEnum.Penis)) {
                int color = mPainPointsGenitalsMap.get(PainLocationEnum.Penis).getColor();
                listener.onSelectedPainPointColor(color);
            }

            if (mPainPointsGenitalsMap.containsKey(PainLocationEnum.Vagina)) {
                int color = mPainPointsGenitalsMap.get(PainLocationEnum.Vagina).getColor();
                listener.onSelectedPainPointColor(color);
            }

            if (mPainPointsGenitalsMap.containsKey(PainLocationEnum.Penis)) {
                PainPoint painPoint = mPainPointsGenitalsMap.get(PainLocationEnum.Penis);
                int color = painPoint.getColor();
                Drawable wrappedDrawable = setGenitalsPainPointsColor(color, R.drawable.ic_penis);
                penisRb.setCompoundDrawablesWithIntrinsicBounds(null, null, null, wrappedDrawable);
            }

            if (mPainPointsGenitalsMap.containsKey(PainLocationEnum.Testicles)) {
                PainPoint painPoint = mPainPointsGenitalsMap.get(PainLocationEnum.Testicles);
                int color = painPoint.getColor();
                Drawable wrappedDrawable = setGenitalsPainPointsColor(color, R.drawable.ic_testicles);
                testiclesRb.setCompoundDrawablesWithIntrinsicBounds(null, null, null, wrappedDrawable);
            }

            if (mPainPointsGenitalsMap.containsKey(PainLocationEnum.Vagina)) {
                PainPoint painPoint = mPainPointsGenitalsMap.get(PainLocationEnum.Vagina);
                int color = painPoint.getColor();
                Drawable wrappedDrawable = setGenitalsPainPointsColor(color, R.drawable.ic_vagina);
                vaginaRb.setCompoundDrawablesWithIntrinsicBounds(null, null, null, wrappedDrawable);
            }
        }

        maleRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.penis:
                       selectedLocation = PainLocationEnum.Penis;
                       break;
                    case R.id.testicles:
                        selectedLocation = PainLocationEnum.Testicles;
                        break;
                }
            }
        });

        genderRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                chooseTitleTv.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.VISIBLE);
                switch (checkedId) {
                    case R.id.male_Rb:
                      maleLayout.setVisibility(View.VISIBLE);
                      femaleLayout.setVisibility(View.INVISIBLE);
                      penisRb.setChecked(true);
                      selectedLocation = PainLocationEnum.Penis;
                      break;

                    case R.id.female_Rb:
                        femaleLayout.setVisibility(View.VISIBLE);
                        maleLayout.setVisibility(View.INVISIBLE);
                        vaginaRb.setChecked(true);
                        selectedLocation = PainLocationEnum.Vagina;
                        break;
                }
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPainPointsGenitalsMap.containsKey(selectedLocation)) {
                    int painStrength = mPainPointsGenitalsMap.get(selectedLocation).getPainStrength();
                    listener.onContinueToStrengthBtnClicked(selectedLocation, painStrength);
                } else {
                    listener.onContinueToStrengthBtnClicked(selectedLocation, 0);
                }
            }
        });

        return rootView;
    }

    public Drawable setGenitalsPainPointsColor(int color, int drawable) {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(mContext, drawable);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        return  wrappedDrawable;
    }
}
