package com.example.sheba_mental_health_project.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
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

    private final String TAG = "MouthSubFragment";
    private PainLocationEnum selectedPainLocation = PainLocationEnum.Pharynx;

    private RadioGroup mouthRg1;
    private RadioGroup mouthRg2;
    private Context context1;

    private EnumMap<PainLocationEnum, PainPoint> mPainPointsMouthMap = new EnumMap<>(PainLocationEnum.class);

    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                mouthRg2.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception(like Vladimir Volodin pointed out)
                mouthRg2.clearCheck(); // clear the second RadioGroup!
                mouthRg2.setOnCheckedChangeListener(listener2); //reset the listener

            }

            switch (checkedId){
                case R.id.pharynx:
                    selectedPainLocation = PainLocationEnum.Pharynx;
                    if (mPainPointsMouthMap.containsKey(PainLocationEnum.Pharynx)){
                        int color = mPainPointsMouthMap.get(PainLocationEnum.Pharynx).getColor();
                        listener.onSelectedPainPointColor(color);
                    }

                    break;

                case R.id.lips:
                    selectedPainLocation = PainLocationEnum.Lips;
                    if (mPainPointsMouthMap.containsKey(PainLocationEnum.Lips)){
                        int color = mPainPointsMouthMap.get(PainLocationEnum.Lips).getColor();
                        listener.onSelectedPainPointColor(color);
                    }

                    break;

                case R.id.palate:
                    selectedPainLocation = PainLocationEnum.Palate;
                    if (mPainPointsMouthMap.containsKey(PainLocationEnum.Palate)){
                        int color = mPainPointsMouthMap.get(PainLocationEnum.Palate).getColor();
                        listener.onSelectedPainPointColor(color);
                    }

                    break;
            }


        }
    };

    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                mouthRg1.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception(like Vladimir Volodin pointed out)
                mouthRg1.clearCheck(); // clear the second RadioGroup!
                mouthRg1.setOnCheckedChangeListener(listener1); //reset the listener

            }

            switch (checkedId){
                case R.id.teeth:
                    selectedPainLocation = PainLocationEnum.Teeth;
                    if (mPainPointsMouthMap.containsKey(PainLocationEnum.Teeth)){
                        int color = mPainPointsMouthMap.get(PainLocationEnum.Teeth).getColor();
                        listener.onSelectedPainPointColor(color);
                    }

                    break;

                case R.id.tongue:
                    selectedPainLocation = PainLocationEnum.Tongue;
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
        listener = (HeadFragment) getParentFragment();
        context1 = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.mouth_sub_fragment, container, false);


        mouthRg1 = rootView.findViewById(R.id.mouth_Rg1);
        mouthRg2 = rootView.findViewById(R.id.mouth_Rg2);
        mouthRg1.clearCheck();
        mouthRg2.clearCheck();
        mouthRg1.setOnCheckedChangeListener(listener1);
        mouthRg2.setOnCheckedChangeListener(listener2);


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
                int color = mPainPointsMouthMap.get(PainLocationEnum.Pharynx).getColor();
                listener.onSelectedPainPointColor(color);
            }
            if (mPainPointsMouthMap.containsKey(PainLocationEnum.Pharynx)){
                PainPoint painPoint = mPainPointsMouthMap.get(PainLocationEnum.Pharynx);
                int color = painPoint.getColor();
                Drawable wrappedDrawable = setMouthPainPointsColor(color,R.drawable.ic_pharynx);
                pharynxBtn.setCompoundDrawablesWithIntrinsicBounds(null,null,null,wrappedDrawable);
            }
            if (mPainPointsMouthMap.containsKey(PainLocationEnum.Teeth)){
                PainPoint painPoint = mPainPointsMouthMap.get(PainLocationEnum.Teeth);
                int color = painPoint.getColor();
                Drawable wrappedDrawable = setMouthPainPointsColor(color,R.drawable.ic_teeth);
                teethBtn.setCompoundDrawablesWithIntrinsicBounds(null,null,null,wrappedDrawable);
            }
            if (mPainPointsMouthMap.containsKey(PainLocationEnum.Lips)){
                PainPoint painPoint = mPainPointsMouthMap.get(PainLocationEnum.Lips);
                int color = painPoint.getColor();
                Drawable wrappedDrawable = setMouthPainPointsColor(color,R.drawable.ic_lips);
                lipsBtn.setCompoundDrawablesWithIntrinsicBounds(null,null,null,wrappedDrawable);
            }
            if (mPainPointsMouthMap.containsKey(PainLocationEnum.Tongue)){
                PainPoint painPoint = mPainPointsMouthMap.get(PainLocationEnum.Tongue);
                int color = painPoint.getColor();
                Drawable wrappedDrawable = setMouthPainPointsColor(color,R.drawable.ic_tongue);
                tongueBtn.setCompoundDrawablesWithIntrinsicBounds(null,null,null,wrappedDrawable);
            }
            if (mPainPointsMouthMap.containsKey(PainLocationEnum.Palate)){
                PainPoint painPoint = mPainPointsMouthMap.get(PainLocationEnum.Palate);
                int color = painPoint.getColor();
                Drawable wrappedDrawable = setMouthPainPointsColor(color,R.drawable.ic_palate);
                palateBtn.setCompoundDrawablesWithIntrinsicBounds(null,null,null,wrappedDrawable);
            }
        }

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onContinueToStrengthBtnClicked(selectedPainLocation,mPainPointsMouthMap);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

    public Drawable setMouthPainPointsColor(int color, int drawable){
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(context1, drawable);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable,color);
        return  wrappedDrawable;
    }

}