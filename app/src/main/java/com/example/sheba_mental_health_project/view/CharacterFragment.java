package com.example.sheba_mental_health_project.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.PainPoint;
import com.example.sheba_mental_health_project.model.enums.PainLocationEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.viewmodel.CharacterViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CharacterFragment extends Fragment {

    private CharacterViewModel mViewModel;

    private final EnumMap<PainLocationEnum, ImageView> mLocationToIvMap = new EnumMap<>(PainLocationEnum.class);

    private final String TAG = "CharacterFragment";


    public interface CharacterFragmentInterface {
        void onHeadClicked();
        void onCenterOfMassClicked();
        void onRightArmClicked();
        void onLeftArmClicked();
        void onGenitalsClicked();
        void onLegsClicked();
    }

    private CharacterFragmentInterface listener;

    public static CharacterFragment newInstance() {
        return new CharacterFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (CharacterFragmentInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements CharacterFragmentInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.Character)).get(CharacterViewModel.class);

        final Observer<Map<String, List<PainPoint>>> onGetAllPainPointsSucceed = new Observer<Map<String, List<PainPoint>>>() {
            @Override
            public void onChanged(Map<String, List<PainPoint>> map) {
                showPainPoints();
            }
        };

        final Observer<String> onGetAllPainPointsFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.w(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getGetAllPaintPointsSucceed().observe(this, onGetAllPainPointsSucceed);
        mViewModel.getGetAllPaintPointsFailed().observe(this, onGetAllPainPointsFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.character_fragment, container, false);

        final View headView = rootView.findViewById(R.id.head_v);
        final View leftArmView = rootView.findViewById(R.id.arm_left_v);
        final View rightArmView = rootView.findViewById(R.id.arm_right_v);
        final View centerOfMassView = rootView.findViewById(R.id.center_of_mass_v);
        final View genitalsView = rootView.findViewById(R.id.genitals_v);
        final View legsView = rootView.findViewById(R.id.legs_v);

        initializeViewMap(rootView);

        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*getParentFragmentManager().beginTransaction()
                        .replace(android.R.id.content, HeadFragment.newInstance(), "Head_Fragment")
                        .addToBackStack(null)
                        .commit();*/
                if (listener != null) {
                    listener.onHeadClicked();
                }
            }
        });

        centerOfMassView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCenterOfMassClicked();
                }
            }
        });

        leftArmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLeftArmClicked();
                }
            }
        });

        rightArmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRightArmClicked();
                }
            }
        });

        genitalsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onGenitalsClicked();
                }
            }
        });

        legsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLegsClicked();
                }
            }
        });

        mViewModel.getAllPainPoints();

        return rootView;
    }

    private void initializeViewMap(final View rootView) {
        mLocationToIvMap.put(PainLocationEnum.Hand, rootView.findViewById(R.id.left_arm_1_iv));
        mLocationToIvMap.put(PainLocationEnum.Elbow, rootView.findViewById(R.id.left_arm_2_iv));
        mLocationToIvMap.put(PainLocationEnum.Shoulder, rootView.findViewById(R.id.left_arm_3_iv));
    }

    private void showPainPoints() {
        for (Map.Entry<PainLocationEnum, ImageView> entry : mLocationToIvMap.entrySet()) {
            if (mViewModel.getPainPointsMap().containsKey(entry.getKey())) {
                final PainPoint painPoint = mViewModel.getPainPointsMap().get(entry.getKey());
                entry.getValue().setColorFilter(painPoint.getColor());
                entry.getValue().setVisibility(View.VISIBLE);
            } else {
                entry.getValue().setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mViewModel.removeGetAllPainPointsListener();
    }
}
