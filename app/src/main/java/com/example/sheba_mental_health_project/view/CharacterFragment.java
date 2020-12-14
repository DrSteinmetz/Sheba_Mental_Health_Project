package com.example.sheba_mental_health_project.view;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.viewmodel.CharacterViewModel;

public class CharacterFragment extends Fragment {

    private CharacterViewModel mViewModel;


    /*public interface CharacterFragmentInterface {
        void onHeadClicked();
        void onCenterOfMassClicked();
        void onRightArmClicked();
        void onLeftArmClicked();
        void onGenitalsClicked();
        void onLegsClicked();
    }

    private CharacterFragmentInterface listener;*/

    public static CharacterFragment newInstance() {
        return new CharacterFragment();
    }

    /*@Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (CharacterFragmentInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements CharacterFragmentInterface listener!");
        }
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.Character)).get(CharacterViewModel.class);
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

        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(android.R.id.content, HeadFragment.newInstance(), "Head_Fragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        centerOfMassView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(android.R.id.content, CenterOfMassFragment.newInstance(), "Center_Of_Mass_Fragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        leftArmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(android.R.id.content, LeftArmFragment.newInstance(), "Left_Arm_Fragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        rightArmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(android.R.id.content, RightArmFragment.newInstance(), "Right_Arm_Fragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        genitalsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(android.R.id.content, GenitalsFragment.newInstance(), "Genitals_Fragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        legsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(android.R.id.content, LegsFragment.newInstance(), "Legs_Fragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }
}
