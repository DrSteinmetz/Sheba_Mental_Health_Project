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
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.Question;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.InquiryViewModel;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class InquiryFragment extends Fragment {

    private InquiryViewModel mViewModel;

    private RelativeLayout mRecommendationsContentRelativeLayout;
    private TextView mRecommendationsTv;
    private MaterialCheckBox mRecommendationsCb;
    private MaterialTextView mRecommendationsAnswerTv;
    private TextView mNoAnswerRecommendationsTv;

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
    private RecyclerView mAnxietyRecycler;
    private TextView mAnxietyTv;
    private RecyclerView mAngerRecycler;
    private TextView mAngerTv;
    private RecyclerView mDepressionRecycler;
    private TextView mDepressionTv;

    private AnswersAdapter mExpectationsAdapter;
    private AnswersAdapter mCovidAdapter;
    private AnswersAdapter mStatementAdapter;
    private AnswersAdapter mSocialAdapter;
    private AnswersAdapter mHabitsAdapter;
    private AnswersAdapter mMentalAdapter;
    private AnswersAdapter mAnxietyAdapter;
    private AnswersAdapter mAngerAdapter;
    private AnswersAdapter mDepressionAdapter;

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
                mViewModel.getLastMeetingFromRepository();
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
                    Appointment lastAppointment = mViewModel.getLastAppointment();
                    Appointment currentAppointment = mViewModel.getCurrentAppointment();

                Log.d(TAG, "onChanged: "+lastAppointment);
                    if(lastAppointment != null) {
                        final String recommendations = lastAppointment.getRecommendations();

                        if ((recommendations != null && !recommendations.isEmpty())) {
                            mRecommendationsContentRelativeLayout.setVisibility(View.VISIBLE);
                            mNoAnswerRecommendationsTv.setVisibility(View.GONE);
                            mRecommendationsTv.setText(lastAppointment.getRecommendations());

                            mRecommendationsCb.setChecked(true);
                            mRecommendationsCb.setClickable(false);
                            switch (currentAppointment.getRecommendationsState()){
                                case Follow:
                                    mRecommendationsCb.setText(R.string.follow_recommendations);
                                    break;
                                case Partially:
                                    mRecommendationsCb.setText(R.string.follow_partially_recommendations);
                                    break;
                                case NotFollow:
                                    mRecommendationsCb.setText(R.string.didnt_follow_recommendations);
                                    break;
                                default:
                                    mRecommendationsContentRelativeLayout.setVisibility(View.GONE);
                                    mNoAnswerRecommendationsTv.setVisibility(View.VISIBLE);
                            }

                            mRecommendationsAnswerTv.setText(currentAppointment.getRecommendationsStateDetails());

                        } else {
                            mRecommendationsContentRelativeLayout.setVisibility(View.GONE);
                            mNoAnswerRecommendationsTv.setVisibility(View.VISIBLE);
                        }
                    } else {
                        mRecommendationsContentRelativeLayout.setVisibility(View.GONE);
                        mNoAnswerRecommendationsTv.setVisibility(View.VISIBLE);
                    }


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

                mAnxietyAdapter = new AnswersAdapter(mViewModel.getAnxietyQuestions(),
                        answers);
                mAnxietyTv.setVisibility(mViewModel.getAnxietyQuestions().isEmpty() ?
                        View.VISIBLE : View.GONE);

                mAngerAdapter = new AnswersAdapter(mViewModel.getAngerQuestions(),
                        answers);
                mAngerTv.setVisibility(mViewModel.getAngerQuestions().isEmpty() ?
                        View.VISIBLE : View.GONE);

                mDepressionAdapter = new AnswersAdapter(mViewModel.getDepressionQuestions(),
                        answers);
                mDepressionTv.setVisibility(mViewModel.getDepressionQuestions().isEmpty() ?
                        View.VISIBLE : View.GONE);


                mExpectationsRecycler.setAdapter(mExpectationsAdapter);
                mCovidRecycler.setAdapter(mCovidAdapter);
                mStatementRecycler.setAdapter(mStatementAdapter);
                mSocialRecycler.setAdapter(mSocialAdapter);
                mHabitsRecycler.setAdapter(mHabitsAdapter);
                mMentalRecycler.setAdapter(mMentalAdapter);
                mAnxietyRecycler.setAdapter(mAnxietyAdapter);
                mAngerRecycler.setAdapter(mAngerAdapter);
                mDepressionRecycler.setAdapter(mDepressionAdapter);


            }
        };

        final Observer<String> onGetLiveAnswersFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.w(TAG, "onChanged: " + error);
            }
        };

        final Observer<Appointment> onGetLastAppointmentSucceed = new Observer<Appointment>() {
            @Override
            public void onChanged(Appointment appointment) {
                mViewModel.getLiveAnswers();
            }
        };

        final Observer<String> onGetLastAppointmentFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                mViewModel.getLiveAnswers();
                Log.e(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getGetLastAppointmentSucceed().observe(this, onGetLastAppointmentSucceed);
        mViewModel.getGetLastAppointmentFailed().observe(this, onGetLastAppointmentFailed);
        mViewModel.getGetAllQuestionsSucceed().observe(this, onGetAllQuestionsSucceed);
        mViewModel.getGetAllQuestionsFailed().observe(this, onGetAllQuestionsFailed);
        mViewModel.getGetLiveAnswersSucceed().observe(this, onGetLiveAnswersSucceed);
        mViewModel.getGetLiveAnswersFailed().observe(this, onGetLiveAnswersFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.inquiry_fragment, container, false);

        final RelativeLayout recommendationsRelativeLayout = rootView.findViewById(R.id.last_meeting_recommendations_relative_layout);
        final RelativeLayout recommendationsTitleRelativeLayout = rootView.findViewById(R.id.recommendations_title_layout);
        final ImageView recommendationsArrowIv = rootView.findViewById(R.id.recommendations_arrow_iv);
        mRecommendationsContentRelativeLayout = rootView.findViewById(R.id.last_meeting_recommendations_content_layout);
        mRecommendationsTv = rootView.findViewById(R.id.last_meeting_recommendations_tv);
        mRecommendationsCb = rootView.findViewById(R.id.last_meeting_recommendations_cb);
        mRecommendationsAnswerTv = rootView.findViewById(R.id.last_meeting_answer_tv);
        mNoAnswerRecommendationsTv = rootView.findViewById(R.id.recommendations_no_answer_relative_tv);


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

        final RelativeLayout anxietyTitleLayout = rootView.findViewById(R.id.anxiety_title_layout);
        final RelativeLayout anxietyRecyclerLayout = rootView.findViewById(R.id.anxiety_recycler_layout);
        mAnxietyTv = rootView.findViewById(R.id.anxiety_recycler_tv);
        final ImageView anxietyArrowIv = rootView.findViewById(R.id.anxiety_arrow_iv);
        mAnxietyRecycler = rootView.findViewById(R.id.anxiety_recycler_view);

        final RelativeLayout angerTitleLayout = rootView.findViewById(R.id.anger_title_layout);
        final RelativeLayout angerRecyclerLayout = rootView.findViewById(R.id.anger_recycler_layout);
        mAngerTv = rootView.findViewById(R.id.anger_recycler_tv);
        final ImageView angerArrowIv = rootView.findViewById(R.id.anger_arrow_iv);
        mAngerRecycler = rootView.findViewById(R.id.anger_recycler_view);

        final RelativeLayout depressionTitleLayout = rootView.findViewById(R.id.depression_title_layout);
        final RelativeLayout depressionRecyclerLayout = rootView.findViewById(R.id.depression_recycler_layout);
        mDepressionTv = rootView.findViewById(R.id.depression_recycler_tv);
        final ImageView depressionArrowIv = rootView.findViewById(R.id.depression_arrow_iv);
        mDepressionRecycler = rootView.findViewById(R.id.depression_recycler_view);

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
        mAnxietyRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAnxietyRecycler.setHasFixedSize(true);
        mAngerRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAngerRecycler.setHasFixedSize(true);
        mDepressionRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mDepressionRecycler.setHasFixedSize(true);

        recommendationsTitleRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean isVisible = recommendationsRelativeLayout.getVisibility() == View.VISIBLE;

                recommendationsRelativeLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
                recommendationsArrowIv.setRotation(isVisible ? 0 : 180);
            }
        });

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

        anxietyTitleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean isVisible = anxietyRecyclerLayout.getVisibility() == View.VISIBLE;

                anxietyRecyclerLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
                anxietyArrowIv.setRotation(isVisible ? 0 : 180);
            }
        });

        angerTitleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean isVisible = angerRecyclerLayout.getVisibility() == View.VISIBLE;

                angerRecyclerLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
                angerArrowIv.setRotation(isVisible ? 0 : 180);
            }
        });

        depressionTitleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isVisible = depressionRecyclerLayout.getVisibility() == View.VISIBLE;

                depressionRecyclerLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
                depressionArrowIv.setRotation(isVisible ? 0 : 180);
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
