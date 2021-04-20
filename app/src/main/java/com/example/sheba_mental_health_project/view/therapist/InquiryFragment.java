package com.example.sheba_mental_health_project.view.therapist;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Answer;
import com.example.sheba_mental_health_project.model.AnswersAdapter;
import com.example.sheba_mental_health_project.model.Question;
import com.example.sheba_mental_health_project.model.QuestionsAdapter;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.InquiryViewModel;

import java.util.List;

public class InquiryFragment extends Fragment {

    private InquiryViewModel mViewModel;

    private RecyclerView mExpectationsRecycler;
    private TextView mExpectationsTv;
    private RecyclerView mCovidRecycler;
    private TextView mCovidTv;
    private RecyclerView mStatementRecycler;
    private TextView mStatementTv;
    private RecyclerView mSocialRecycler;
    private TextView mSocialTv;
    private RecyclerView mHabitsRecycler;
    private TextView mHabitsTv;
    private RecyclerView mMentalRecycler;
    private TextView mMentalTv;

    private AnswersAdapter mExpectationsAdapter;
    private AnswersAdapter mCovidAdapter;
    private AnswersAdapter mStatementAdapter;
    private AnswersAdapter mSocialAdapter;
    private AnswersAdapter mHabitsAdapter;
    private AnswersAdapter mMentalAdapter;

    private final String TAG = "InquiryFragment";


    public static InquiryFragment newInstance() {
        return new InquiryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.Inquiry)).get(InquiryViewModel.class);

        final Observer<List<Question>> onGetAllQuestionsSucceed = new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                mViewModel.getLiveAnswers();
            }
        };

        final Observer<String> onGetAllQuestionsFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.w(TAG, "onChanged: " + error);
            }
        };

        final Observer<List<Answer>> onGetLiveAnswersSucceed = new Observer<List<Answer>>() {
            @Override
            public void onChanged(List<Answer> answers) {

                mExpectationsAdapter = new AnswersAdapter(mViewModel.getExpectationsQuestions(),
                        answers);
                mExpectationsTv.setVisibility(mViewModel.getExpectationsQuestions().isEmpty() ?
                        View.VISIBLE : View.GONE);

                mCovidAdapter = new AnswersAdapter(mViewModel.getCovidQuestions(),
                        answers);
                mCovidTv.setVisibility(mViewModel.getCovidQuestions().isEmpty() ?
                        View.VISIBLE : View.GONE);

                mStatementAdapter = new AnswersAdapter(mViewModel.getStatementQuestions(),
                        answers);
                mStatementTv.setVisibility(mViewModel.getStatementQuestions().isEmpty() ?
                        View.VISIBLE : View.GONE);

                mSocialAdapter = new AnswersAdapter(mViewModel.getSocialQuestions(),
                        answers);
                mSocialTv.setVisibility(mViewModel.getSocialQuestions().isEmpty() ?
                        View.VISIBLE : View.GONE);

                mHabitsAdapter = new AnswersAdapter(mViewModel.getHabitsQuestions(),
                        answers);
                mHabitsTv.setVisibility(mViewModel.getHabitsQuestions().isEmpty() ?
                        View.VISIBLE : View.GONE);

                mMentalAdapter = new AnswersAdapter(mViewModel.getMentalQuestions(),
                        answers);
                mMentalTv.setVisibility(mViewModel.getMentalQuestions().isEmpty() ?
                        View.VISIBLE : View.GONE);


                mExpectationsRecycler.setAdapter(mExpectationsAdapter);
                mCovidRecycler.setAdapter(mCovidAdapter);
                mStatementRecycler.setAdapter(mStatementAdapter);
                mSocialRecycler.setAdapter(mSocialAdapter);
                mHabitsRecycler.setAdapter(mHabitsAdapter);
                mMentalRecycler.setAdapter(mMentalAdapter);
            }
        };

        final Observer<String> onGetLiveAnswersFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.w(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getGetAllQuestionsSucceed().observe(this, onGetAllQuestionsSucceed);
        mViewModel.getGetAllQuestionsFailed().observe(this, onGetAllQuestionsFailed);
        mViewModel.getGetLiveAnswersSucceed().observe(this, onGetLiveAnswersSucceed);
        mViewModel.getGetLiveAnswersFailed().observe(this, onGetLiveAnswersFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.inquiry_fragment, container, false);

        final RelativeLayout expectationsTitleLayout = rootView.findViewById(R.id.expectations_title_layout);
        final RelativeLayout expectationsRecyclerLayout = rootView.findViewById(R.id.expectations_recycler_layout);
        mExpectationsTv = rootView.findViewById(R.id.expectations_recycler_tv);
        final ImageView expectationsArrowIv = rootView.findViewById(R.id.expectations_arrow_iv);
        mExpectationsRecycler = rootView.findViewById(R.id.expectations_recycler_view);
        final RelativeLayout covidTitleLayout = rootView.findViewById(R.id.covid_title_layout);
        final RelativeLayout covidRecyclerLayout = rootView.findViewById(R.id.covid_recycler_layout);
        mCovidTv = rootView.findViewById(R.id.covid_recycler_tv);
        final ImageView covidArrowIv = rootView.findViewById(R.id.covid_arrow_iv);
        mCovidRecycler = rootView.findViewById(R.id.covid_recycler_view);
        final RelativeLayout statementTitleLayout = rootView.findViewById(R.id.statement_title_layout);
        final RelativeLayout statementRecyclerLayout = rootView.findViewById(R.id.statement_recycler_layout);
        mStatementTv = rootView.findViewById(R.id.statement_recycler_tv);
        final ImageView statementArrowIv = rootView.findViewById(R.id.statement_arrow_iv);
        mStatementRecycler = rootView.findViewById(R.id.statement_recycler_view);
        final RelativeLayout socialTitleLayout = rootView.findViewById(R.id.social_title_layout);
        final RelativeLayout socialRecyclerLayout = rootView.findViewById(R.id.social_recycler_layout);
        mSocialTv = rootView.findViewById(R.id.social_recycler_tv);
        final ImageView socialArrowIv = rootView.findViewById(R.id.social_arrow_iv);
        mSocialRecycler = rootView.findViewById(R.id.social_recycler_view);
        final RelativeLayout habitsTitleLayout = rootView.findViewById(R.id.habits_title_layout);
        final RelativeLayout habitsRecyclerLayout = rootView.findViewById(R.id.habits_recycler_layout);
        mHabitsTv = rootView.findViewById(R.id.habits_recycler_tv);
        final ImageView habitsArrowIv = rootView.findViewById(R.id.habits_arrow_iv);
        mHabitsRecycler = rootView.findViewById(R.id.habits_recycler_view);
        final RelativeLayout mentalTitleLayout = rootView.findViewById(R.id.mental_title_layout);
        final RelativeLayout mentalRecyclerLayout = rootView.findViewById(R.id.mental_recycler_layout);
        mMentalTv = rootView.findViewById(R.id.mental_recycler_tv);
        final ImageView mentalArrowIv = rootView.findViewById(R.id.mental_arrow_iv);
        mMentalRecycler = rootView.findViewById(R.id.mental_recycler_view);

        mViewModel.attachGetLiveAnswersListener();

        mExpectationsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mExpectationsRecycler.setHasFixedSize(true);
        mCovidRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mCovidRecycler.setHasFixedSize(true);
        mStatementRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mStatementRecycler.setHasFixedSize(true);
        mSocialRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mSocialRecycler.setHasFixedSize(true);
        mHabitsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mHabitsRecycler.setHasFixedSize(true);
        mMentalRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mMentalRecycler.setHasFixedSize(true);


        expectationsTitleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean isVisible = expectationsRecyclerLayout.getVisibility() == View.VISIBLE;

                expectationsRecyclerLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
                expectationsArrowIv.setRotation(isVisible ? 0 : 180);
            }
        });

        covidTitleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean isVisible = covidRecyclerLayout.getVisibility() == View.VISIBLE;

                covidRecyclerLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
                covidArrowIv.setRotation(isVisible ? 0 : 180);
            }
        });

        statementTitleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean isVisible = statementRecyclerLayout.getVisibility() == View.VISIBLE;

                statementRecyclerLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
                statementArrowIv.setRotation(isVisible ? 0 : 180);
            }
        });

        socialTitleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean isVisible = socialRecyclerLayout.getVisibility() == View.VISIBLE;

                socialRecyclerLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
                socialArrowIv.setRotation(isVisible ? 0 : 180);
            }
        });

        habitsTitleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean isVisible = habitsRecyclerLayout.getVisibility() == View.VISIBLE;

                habitsRecyclerLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
                habitsArrowIv.setRotation(isVisible ? 0 : 180);
            }
        });

        mentalTitleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean isVisible = mentalRecyclerLayout.getVisibility() == View.VISIBLE;

                mentalRecyclerLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
                mentalArrowIv.setRotation(isVisible ? 0 : 180);
            }
        });

        mViewModel.getAllQuestions();

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mViewModel != null) {
            mViewModel.removeLiveAnswersListener();
        }
    }
}
