package com.example.sheba_mental_health_project.view.patient;

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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.RecommendationsStateEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.MentalPatientViewModel;
import com.example.sheba_mental_health_project.viewmodel.RecommendationQuestionsViewModel;
import com.google.android.material.button.MaterialButton;

import static android.content.ContentValues.TAG;

public class RecommendationQuestionsFragment extends Fragment {

    private EditText mDetailsEt;

    private RecommendationQuestionsViewModel mViewModel;

    public interface RecommendationsQuestionsInterface {
        void onContinueFromRecommendationsQuestions();
    }

    RecommendationsQuestionsInterface listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (RecommendationsQuestionsInterface) context;
        }catch (Exception ex){
            throw new ClassCastException("The Activity Must Implements RecommendationsQuestionsInterface listener!");
        }
    }

    public static RecommendationQuestionsFragment newInstance(String recommendations) {
        RecommendationQuestionsFragment fragment = new RecommendationQuestionsFragment();
        Bundle args = new Bundle();
        args.putSerializable("recommendations", recommendations);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.RecommendationsQuestions)).get(RecommendationQuestionsViewModel.class);

        mViewModel.setRecommendationsState(mViewModel.getCurrentAppointment().getRecommendationsState());

        final Observer<RecommendationsStateEnum> onUpdateRecommendationsStateSucceed = new Observer<RecommendationsStateEnum>() {
            @Override
            public void onChanged(RecommendationsStateEnum recommendationsStateEnum) {
                if(listener != null){
                    if(mViewModel.isRecommendationsStateChanged() &&
                            mViewModel.getRecommendationsStateEnum().equals(RecommendationsStateEnum.Follow)){
                        //TODO add animation
                    }
                    listener.onContinueFromRecommendationsQuestions();
                }
            }
        };

        final Observer<String> onUpdateRecommendationsStateFailed = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "onChanged: " + s);
            }
        };

        mViewModel.getUpdateRecommendationsSucceed().observe(this, onUpdateRecommendationsStateSucceed);
        mViewModel.getUpdateRecommendationsFailed().observe(this, onUpdateRecommendationsStateFailed);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recommendation_questions_fragment, container, false);
        TextView recommendationsTv = rootView.findViewById(R.id.recommendations_tv);
        final RadioGroup radioGroup = rootView.findViewById(R.id.recommendations_rg);
        mDetailsEt = rootView.findViewById(R.id.details_et);
        MaterialButton continueBtn = rootView.findViewById(R.id.continue_btn);

        if(getArguments() != null){
            recommendationsTv.setText(getArguments().getString("recommendations"));
        }

        RecommendationsStateEnum recommendationsStateEnum = mViewModel.getRecommendationsStateEnum();
        if(recommendationsStateEnum != null){
            RadioButton rb = (RadioButton) radioGroup.getChildAt(recommendationsStateEnum.ordinal());
            rb.setChecked(true);

            final String details = mViewModel.getCurrentAppointment().getRecommendationsStateDetails();
            if( details != null){
                mDetailsEt.setText(details);
            }

            mDetailsEt.setVisibility(View.VISIBLE);
        }




        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mDetailsEt.setVisibility(View.VISIBLE);
                mViewModel.setRecommendationsStateChanged(true);

            }
        });


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.follow_rb:
                        mViewModel.setRecommendationsState(RecommendationsStateEnum.Follow);
                        break;
                    case R.id.partially_rb:
                        mViewModel.setRecommendationsState(RecommendationsStateEnum.Partially);
                        break;
                    case R.id.didnt_rb:
                        mViewModel.setRecommendationsState(RecommendationsStateEnum.NotFollow);
                        break;
                    default:
                        break;

                }
                mViewModel.updateRecommendationsState(mViewModel.getRecommendationsStateEnum(),mDetailsEt.getText().toString());
            }
        });

        return rootView;
    }






}