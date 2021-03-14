package com.example.sheba_mental_health_project.view.character;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.PainPoint;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.PainLocationEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.view.LoadingDialogFragment;
import com.example.sheba_mental_health_project.viewmodel.CharacterViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CharacterFragment extends Fragment {

    private CharacterViewModel mViewModel;

    private final EnumMap<PainLocationEnum, ImageView> mLocationToIvMap = new EnumMap<>(PainLocationEnum.class);

    private ImageView mCharacterIv;

    private LinearLayout mCharacterLeft;
    private LinearLayout mCharacterCenter;
    private LinearLayout mCharacterRight;

    private PopupWindow mPreviousPopupWindow;

    private boolean mIsClickable;
    private boolean mIsPainPointClickable;

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

    public static CharacterFragment newInstance(final Appointment appointment,
                                                final boolean isClickable,
                                                final boolean isPainPointClickable) {
        CharacterFragment fragment = new CharacterFragment();
        Bundle args = new Bundle();
        args.putSerializable("appointment", appointment);
        args.putBoolean("is_clickable", isClickable);
        args.putBoolean("is_pain_point_clickable", isPainPointClickable);
        fragment.setArguments(args);
        return fragment;
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

        if (getArguments() != null) {
            mIsClickable = getArguments().getBoolean("is_clickable", true);
            mIsPainPointClickable = getArguments().getBoolean("is_pain_point_clickable", false);
            mViewModel.setAppointment((Appointment) getArguments().getSerializable("appointment"));
        }

        final Observer<Map<String, List<PainPoint>>> onGetAllPainPointsSucceed = new Observer<Map<String, List<PainPoint>>>() {
            @Override
            public void onChanged(Map<String, List<PainPoint>> map) {
                showPainPoints();

                if (mIsPainPointClickable) {
                    initializePainPointsPopupWindow();
                }
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
        mCharacterIv = rootView.findViewById(R.id.character_iv);
        mCharacterLeft = rootView.findViewById(R.id.character_right_layout);
        mCharacterCenter = rootView.findViewById(R.id.character_center_layout);
        mCharacterRight = rootView.findViewById(R.id.character_left_layout);

        mViewModel.attachGetAllPainPointsListener();

        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                final int characterHeight = mCharacterIv.getHeight();
                final int characterWidth = ((characterHeight * 450) / 1000);

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout
                        .LayoutParams(characterWidth, RelativeLayout.LayoutParams.MATCH_PARENT);

                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

                mCharacterIv.setLayoutParams(layoutParams);
            }
        });

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                showCharacter();
            }
        });

        initializeViewMap(rootView);

        if (mIsClickable) {
            headView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
        } else if (mIsPainPointClickable) {
            initializePainPointsPopupWindow();
        }

        mViewModel.getAllPainPoints();

        return rootView;
    }

    private void initializeViewMap(@NotNull final View rootView) {
        /** Head */
        mLocationToIvMap.put(PainLocationEnum.Scalp, rootView.findViewById(R.id.scalp_iv));
        mLocationToIvMap.put(PainLocationEnum.Forehead, rootView.findViewById(R.id.forehead_iv));
        mLocationToIvMap.put(PainLocationEnum.RightEar, rootView.findViewById(R.id.left_ear_iv));
        mLocationToIvMap.put(PainLocationEnum.RightEye, rootView.findViewById(R.id.left_eye_iv));
        mLocationToIvMap.put(PainLocationEnum.LeftEar, rootView.findViewById(R.id.right_ear_iv));
        mLocationToIvMap.put(PainLocationEnum.LeftEye, rootView.findViewById(R.id.right_eye_iv));
        mLocationToIvMap.put(PainLocationEnum.Nose, rootView.findViewById(R.id.nose_iv));
        mLocationToIvMap.put(PainLocationEnum.Neck, rootView.findViewById(R.id.neck_iv));
        mLocationToIvMap.put(PainLocationEnum.Mouth, rootView.findViewById(R.id.mouth_iv));
        mLocationToIvMap.put(PainLocationEnum.Pharynx, rootView.findViewById(R.id.mouth_iv));
        mLocationToIvMap.put(PainLocationEnum.Lips, rootView.findViewById(R.id.mouth_iv));
        mLocationToIvMap.put(PainLocationEnum.Palate, rootView.findViewById(R.id.mouth_iv));
        mLocationToIvMap.put(PainLocationEnum.Teeth, rootView.findViewById(R.id.mouth_iv));
        mLocationToIvMap.put(PainLocationEnum.Tongue, rootView.findViewById(R.id.mouth_iv));

        /** Center of Mass */
        mLocationToIvMap.put(PainLocationEnum.Chest, rootView.findViewById(R.id.chest_iv));
        mLocationToIvMap.put(PainLocationEnum.UpperRightAbdomen, rootView.findViewById(R.id.upper_left_abdomen_iv));
        mLocationToIvMap.put(PainLocationEnum.UpperLeftAbdomen, rootView.findViewById(R.id.upper_right_abdomen_iv));
        mLocationToIvMap.put(PainLocationEnum.Navel, rootView.findViewById(R.id.navel_iv));
        mLocationToIvMap.put(PainLocationEnum.LowerRightAbdomen, rootView.findViewById(R.id.lower_left_abdomen_iv));
        mLocationToIvMap.put(PainLocationEnum.LowerLeftAbdomen, rootView.findViewById(R.id.lower_right_abdomen_iv));

        /** Right Arm */
        mLocationToIvMap.put(PainLocationEnum.RightShoulder, rootView.findViewById(R.id.left_shoulder_iv));
        mLocationToIvMap.put(PainLocationEnum.RightArm, rootView.findViewById(R.id.left_arm_iv));
        mLocationToIvMap.put(PainLocationEnum.RightElbow, rootView.findViewById(R.id.left_elbow_iv));
        mLocationToIvMap.put(PainLocationEnum.RightForearm, rootView.findViewById(R.id.left_forearm_iv));
        mLocationToIvMap.put(PainLocationEnum.RightWrist, rootView.findViewById(R.id.left_wrist_iv));
        mLocationToIvMap.put(PainLocationEnum.RightPalm, rootView.findViewById(R.id.left_palm_iv));
        mLocationToIvMap.put(PainLocationEnum.RightFingers, rootView.findViewById(R.id.left_fingers_iv));

        /** Left Arm */
        mLocationToIvMap.put(PainLocationEnum.LeftShoulder, rootView.findViewById(R.id.right_shoulder_iv));
        mLocationToIvMap.put(PainLocationEnum.LeftArm, rootView.findViewById(R.id.right_arm_iv));
        mLocationToIvMap.put(PainLocationEnum.LeftElbow, rootView.findViewById(R.id.right_elbow_iv));
        mLocationToIvMap.put(PainLocationEnum.LeftForearm, rootView.findViewById(R.id.right_forearm_iv));
        mLocationToIvMap.put(PainLocationEnum.LeftWrist, rootView.findViewById(R.id.right_wrist_iv));
        mLocationToIvMap.put(PainLocationEnum.LeftPalm, rootView.findViewById(R.id.right_palm_iv));
        mLocationToIvMap.put(PainLocationEnum.LeftFingers, rootView.findViewById(R.id.right_fingers_iv));

        /** Genitals */
        mLocationToIvMap.put(PainLocationEnum.PrivatePart, rootView.findViewById(R.id.private_part_iv));

        /** Legs */
        mLocationToIvMap.put(PainLocationEnum.RightThigh, rootView.findViewById(R.id.left_thigh_iv));
        mLocationToIvMap.put(PainLocationEnum.LeftThigh, rootView.findViewById(R.id.right_thigh_iv));
        mLocationToIvMap.put(PainLocationEnum.RightKnee, rootView.findViewById(R.id.left_knee_iv));
        mLocationToIvMap.put(PainLocationEnum.LeftKnee, rootView.findViewById(R.id.right_knee_iv));
        mLocationToIvMap.put(PainLocationEnum.RightShin, rootView.findViewById(R.id.left_shin_iv));
        mLocationToIvMap.put(PainLocationEnum.LeftShin, rootView.findViewById(R.id.right_shin_iv));
        mLocationToIvMap.put(PainLocationEnum.RightFoot, rootView.findViewById(R.id.left_foot_iv));
        mLocationToIvMap.put(PainLocationEnum.LeftFoot, rootView.findViewById(R.id.right_foot_iv));
        mLocationToIvMap.put(PainLocationEnum.RightToes, rootView.findViewById(R.id.left_toes_iv));
        mLocationToIvMap.put(PainLocationEnum.LeftToes, rootView.findViewById(R.id.right_toes_iv));
    }

    private void initializePainPointsPopupWindow() {
        for (Map.Entry<PainLocationEnum, ImageView> entry : mLocationToIvMap.entrySet()) {
            if (mViewModel.getPainPointsMap().containsKey(entry.getKey())) {
                if (entry.getKey().equals(PainLocationEnum.Mouth) ||
                        entry.getKey().equals(PainLocationEnum.Pharynx) ||
                        entry.getKey().equals(PainLocationEnum.Lips) ||
                        entry.getKey().equals(PainLocationEnum.Palate) ||
                        entry.getKey().equals(PainLocationEnum.Teeth) ||
                        entry.getKey().equals(PainLocationEnum.Tongue)) {

                    if (entry.getKey().equals(PainLocationEnum.Mouth)) {
                        entry.getValue().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final PopupMenu mouthPopupMenu = new PopupMenu(getContext(), entry.getValue());
                                mouthPopupMenu.inflate(R.menu.mouth_menu);
                                mouthPopupMenu.getMenu().findItem(R.id.pharynx_action)
                                        .setVisible(mViewModel.getPainPointsMap().containsKey(PainLocationEnum.Pharynx));
                                mouthPopupMenu.getMenu().findItem(R.id.lips_action)
                                        .setVisible(mViewModel.getPainPointsMap().containsKey(PainLocationEnum.Lips));
                                mouthPopupMenu.getMenu().findItem(R.id.palate_action)
                                        .setVisible(mViewModel.getPainPointsMap().containsKey(PainLocationEnum.Palate));
                                mouthPopupMenu.getMenu().findItem(R.id.teeth_action)
                                        .setVisible(mViewModel.getPainPointsMap().containsKey(PainLocationEnum.Teeth));
                                mouthPopupMenu.getMenu().findItem(R.id.tongue_action)
                                        .setVisible(mViewModel.getPainPointsMap().containsKey(PainLocationEnum.Tongue));

                                mouthPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @SuppressLint("NonConstantResourceId")
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {

                                        final PainLocationEnum painLocationEnum;

                                        switch (item.getItemId()) {
                                            case R.id.pharynx_action:
                                                painLocationEnum = PainLocationEnum.Pharynx;
                                                break;
                                            case R.id.lips_action:
                                                painLocationEnum = PainLocationEnum.Lips;
                                                break;
                                            case R.id.palate_action:
                                                painLocationEnum = PainLocationEnum.Palate;
                                                break;
                                            case R.id.teeth_action:
                                                painLocationEnum = PainLocationEnum.Teeth;
                                                break;
                                            case R.id.tongue_action:
                                                painLocationEnum = PainLocationEnum.Tongue;
                                                break;
                                            default:
                                                painLocationEnum = null;
                                                break;
                                        }

                                        final PopupWindow painPointWindow = createPainPointPopUpWindow(painLocationEnum);
                                        if (mPreviousPopupWindow != null && mPreviousPopupWindow.isShowing()) {
                                            mPreviousPopupWindow.dismiss();
                                        }
                                        mPreviousPopupWindow = painPointWindow;
                                        painPointWindow.showAsDropDown(v);

                                        return false;
                                    }
                                });

                                entry.getValue().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mouthPopupMenu.show();
                                    }
                                });
                            }
                        });
                    }
                } else {
                    entry.getValue().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final PopupWindow painPointWindow = createPainPointPopUpWindow(entry.getKey());
                            if (mPreviousPopupWindow != null && mPreviousPopupWindow.isShowing()) {
                                mPreviousPopupWindow.dismiss();
                            }
                            mPreviousPopupWindow = painPointWindow;
                            painPointWindow.showAsDropDown(v);
                        }
                    });
                }
            }
        }
    }

    private PopupWindow createPainPointPopUpWindow(final PainLocationEnum painLocationEnum) {
        final PainPoint painPoint = mViewModel.getPainPointsMap().get(painLocationEnum);

        final PopupWindow painPointWindow;
        final LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.pain_point_popup_window, null);

        final TextView painLocationTv = view.findViewById(R.id.pain_location_tv);
        final TextView painStrengthTv = view.findViewById(R.id.pain_strength_tv);
        final TextView painTypeTitleTv = view.findViewById(R.id.pain_type_title_tv);
        final TextView painTypeTv = view.findViewById(R.id.pain_type_tv);
        final TextView painFrequencyTitleTv = view.findViewById(R.id.pain_frequency_title_tv);
        final TextView painFrequencyTv = view.findViewById(R.id.pain_frequency_tv);
        final TextView otherFeelingTitleTv = view.findViewById(R.id.other_feelings_title_tv);
        final TextView otherFeelingTv = view.findViewById(R.id.other_feelings_tv);
        final TextView descriptionTitleTv = view.findViewById(R.id.description_title_tv);
        final TextView descriptionTv = view.findViewById(R.id.description_tv);

        painLocationTv.setText(painPoint.getPainPointLocationLocalString(getContext()));

        painStrengthTv.setText(painPoint.getPainStrength() + "");
        painStrengthTv.setTextColor(painPoint.getColor());

        final String painTypeTxt = painPoint.getPainPointTypeLocalString(getContext());
        painTypeTv.setText(painTypeTxt);
        painTypeTv.setVisibility(painTypeTxt.isEmpty() ? View.GONE : View.VISIBLE);
        painTypeTitleTv.setVisibility(painTypeTv.getVisibility());

        final String painFrequencyTxt = painPoint.getPainPointFrequencyLocalString(getContext());
        painFrequencyTv.setText(painFrequencyTxt);
        painFrequencyTv.setVisibility(painFrequencyTxt.isEmpty() ? View.GONE : View.VISIBLE);
        painFrequencyTitleTv.setVisibility(painFrequencyTv.getVisibility());

        final String otherFeelingTxt = painPoint.getOtherFeelingLocalString(getContext());
        otherFeelingTv.setText(otherFeelingTxt);
        otherFeelingTv.setVisibility(otherFeelingTxt.isEmpty() ? View.GONE : View.VISIBLE);
        otherFeelingTitleTv.setVisibility(otherFeelingTv.getVisibility());

        final String descriptionTxt = painPoint.getDescription().isEmpty() ? ""
                : painPoint.getDescription();
        descriptionTv.setText(descriptionTxt);
        descriptionTv.setVisibility(descriptionTxt.isEmpty() ? View.GONE : View.VISIBLE);
        descriptionTitleTv.setVisibility(descriptionTv.getVisibility());

        painPointWindow = new PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, true);

        painPointWindow.getContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                painPointWindow.dismiss();
            }
        });

        return painPointWindow;
    }

    private void showPainPoints() {
        for (Map.Entry<PainLocationEnum, ImageView> entry : mLocationToIvMap.entrySet()) {
            if (mViewModel.getPainPointsMap().containsKey(entry.getKey())) {
                final PainPoint painPoint = mViewModel.getPainPointsMap().get(entry.getKey());
                entry.getValue().setColorFilter(painPoint.getColor());
                entry.getValue().setVisibility(View.VISIBLE);
            } else {
                entry.getValue().setVisibility(View.INVISIBLE);
            }
        }
    }

    private void showCharacter() {
        if (mCharacterLeft != null) {
            mCharacterLeft.setVisibility(View.VISIBLE);
        }

        if (mCharacterCenter != null) {
            mCharacterCenter.setVisibility(View.VISIBLE);
        }

        if (mCharacterRight != null) {
            mCharacterRight.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mViewModel.removeGetAllPainPointsListener();
    }
}
