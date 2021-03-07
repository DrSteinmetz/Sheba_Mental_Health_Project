package com.example.sheba_mental_health_project.view.character;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.PainPoint;
import com.example.sheba_mental_health_project.model.enums.BodyPartEnum;
import com.example.sheba_mental_health_project.model.enums.PainLocationEnum;
import com.google.android.material.button.MaterialButton;

import java.util.EnumMap;

public class MouthSubFragment extends Fragment {

    private PainLocationEnum mSelectedPainLocation = PainLocationEnum.Pharynx;

    private RadioGroup mMouthRg1;
    private RadioGroup mMouthRg2;

    private EnumMap<PainLocationEnum, PainPoint> mPainPointsMouthMap = new EnumMap<>(PainLocationEnum.class);

    private final String TAG = "MouthSubFragment";


    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                mMouthRg2.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception(like Vladimir Volodin pointed out)
                mMouthRg2.clearCheck(); // clear the second RadioGroup!
                mMouthRg2.setOnCheckedChangeListener(listener2); //reset the listener

            }

            switch (checkedId){
                case R.id.pharynx:
                    mSelectedPainLocation = PainLocationEnum.Pharynx;
                    if (mPainPointsMouthMap.containsKey(PainLocationEnum.Pharynx)){
                        int color = mPainPointsMouthMap.get(PainLocationEnum.Pharynx).getColor();
                        listener.onSelectedPainPointColor(color);
                    }

                    break;

                case R.id.lips:
                    mSelectedPainLocation = PainLocationEnum.Lips;
                    if (mPainPointsMouthMap.containsKey(PainLocationEnum.Lips)){
                        int color = mPainPointsMouthMap.get(PainLocationEnum.Lips).getColor();
                        listener.onSelectedPainPointColor(color);
                    }

                    break;

                case R.id.palate:
                    mSelectedPainLocation = PainLocationEnum.Palate;
                    if (mPainPointsMouthMap.containsKey(PainLocationEnum.Palate)){
                        int color = mPainPointsMouthMap.get(PainLocationEnum.Palate).getColor();
                        listener.onSelectedPainPointColor(color);
                    }

                    break;
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                mMouthRg1.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception(like Vladimir Volodin pointed out)
                mMouthRg1.clearCheck(); // clear the second RadioGroup!
                mMouthRg1.setOnCheckedChangeListener(listener1); //reset the listener

            }

            switch (checkedId){
                case R.id.teeth:
                    mSelectedPainLocation = PainLocationEnum.Teeth;
                    if (mPainPointsMouthMap.containsKey(PainLocationEnum.Teeth)){
                        int color = mPainPointsMouthMap.get(PainLocationEnum.Teeth).getColor();
                        listener.onSelectedPainPointColor(color);
                    }
                    break;
                case R.id.tongue:
                    mSelectedPainLocation = PainLocationEnum.Tongue;
                    if (mPainPointsMouthMap.containsKey(PainLocationEnum.Tongue)){
                        int color = mPainPointsMouthMap.get(PainLocationEnum.Tongue).getColor();
                        listener.onSelectedPainPointColor(color);
                    }

                    break;
            }
        }
    };


    public interface MouthSubFragmentInterface {
        void onContinueToStrengthBtnClicked(PainLocationEnum painLocationEnum, EnumMap<PainLocationEnum, PainPoint> mPainPointsMouthMap);
        void onSelectedPainPointColor(int color);
    }

    private MouthSubFragmentInterface listener;

    public static MouthSubFragment newInstance(EnumMap<PainLocationEnum, PainPoint> mPainPointsMouthMap , final BodyPartEnum fragmentName) {
        MouthSubFragment fragment = new MouthSubFragment();
        Bundle args = new Bundle();
        args.putSerializable("mouth_pain_points", mPainPointsMouthMap);
        args.putSerializable("fragment_name", fragmentName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (HeadFragment) getParentFragment();
        } catch (Exception ex) {
            throw new ClassCastException("The fragment must implement MouthSubFragmentInterface Listener!");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.mouth_sub_fragment, container, false);

        mMouthRg1 = rootView.findViewById(R.id.mouth_Rg1);
        mMouthRg2 = rootView.findViewById(R.id.mouth_Rg2);
        mMouthRg1.clearCheck();
        mMouthRg2.clearCheck();
        mMouthRg1.setOnCheckedChangeListener(listener1);
        mMouthRg2.setOnCheckedChangeListener(listener2);


        final RadioButton pharynxBtn = rootView.findViewById(R.id.pharynx);
        final RadioButton lipsBtn = rootView.findViewById(R.id.lips);
        final RadioButton teethBtn = rootView.findViewById(R.id.teeth);
        final RadioButton palateBtn = rootView.findViewById(R.id.palate);
        final RadioButton tongueBtn = rootView.findViewById(R.id.tongue);
        final MaterialButton continueBtn = rootView.findViewById(R.id.continue_btn);

        pharynxBtn.setChecked(true);


        if (getArguments() != null) {
            mPainPointsMouthMap  = (EnumMap<PainLocationEnum, PainPoint> ) getArguments()
                    .getSerializable("mouth_pain_points");

            if (mPainPointsMouthMap.containsKey(PainLocationEnum.Pharynx)){
                final int color = mPainPointsMouthMap.get(PainLocationEnum.Pharynx).getColor();
                listener.onSelectedPainPointColor(color);
            }

            if (mPainPointsMouthMap.containsKey(PainLocationEnum.Pharynx)){
                final PainPoint painPoint = mPainPointsMouthMap.get(PainLocationEnum.Pharynx);
                final int color = painPoint.getColor();
                final Drawable wrappedDrawable = setMouthPainPointsColor(color, R.drawable.ic_pharynx);
                pharynxBtn.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        null, wrappedDrawable);
            }

            if (mPainPointsMouthMap.containsKey(PainLocationEnum.Teeth)){
                final PainPoint painPoint = mPainPointsMouthMap.get(PainLocationEnum.Teeth);
                final int color = painPoint.getColor();
                final Drawable wrappedDrawable = setMouthPainPointsColor(color, R.drawable.ic_teeth);
                teethBtn.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        null, wrappedDrawable);
            }

            if (mPainPointsMouthMap.containsKey(PainLocationEnum.Lips)){
                final PainPoint painPoint = mPainPointsMouthMap.get(PainLocationEnum.Lips);
                final int color = painPoint.getColor();
                final Drawable wrappedDrawable = setMouthPainPointsColor(color, R.drawable.ic_lips);
                lipsBtn.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        null, wrappedDrawable);
            }

            if (mPainPointsMouthMap.containsKey(PainLocationEnum.Tongue)) {
                final PainPoint painPoint = mPainPointsMouthMap.get(PainLocationEnum.Tongue);
                final int color = painPoint.getColor();
                final Drawable wrappedDrawable = setMouthPainPointsColor(color, R.drawable.ic_tongue);
                tongueBtn.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        null, wrappedDrawable);
            }

            if (mPainPointsMouthMap.containsKey(PainLocationEnum.Palate)) {
                final PainPoint painPoint = mPainPointsMouthMap.get(PainLocationEnum.Palate);
                final int color = painPoint.getColor();
                final Drawable wrappedDrawable = setMouthPainPointsColor(color, R.drawable.ic_palate);
                palateBtn.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        null, wrappedDrawable);
            }
        }

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onContinueToStrengthBtnClicked(mSelectedPainLocation, mPainPointsMouthMap);
            }
        });

        return rootView;
    }

    public Drawable setMouthPainPointsColor(int color, int drawable){
        final Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(), drawable);
        final Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        return  wrappedDrawable;
    }
}
