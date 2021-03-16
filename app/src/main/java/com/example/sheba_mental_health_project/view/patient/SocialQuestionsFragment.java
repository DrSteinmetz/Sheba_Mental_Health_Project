package com.example.sheba_mental_health_project.view.patient;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
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

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.Question;
import com.example.sheba_mental_health_project.model.QuestionsAdapter;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.SocialQuestionsViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class SocialQuestionsFragment extends Fragment {

    private SocialQuestionsViewModel mViewModel;

    private RecyclerView mRecyclerView;

    private QuestionsAdapter mQuestionsAdapter;

    private final String TAG = "SocialQuestionsFragment";


    public interface SocialQuestionsFragmentInterface {
        void onContinueFromSocialQuestions();
    }

    private SocialQuestionsFragmentInterface listener;

    public static SocialQuestionsFragment newInstance() {
        return new SocialQuestionsFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (SocialQuestionsFragmentInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements SocialQuestionsFragmentInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.SocialQuestions)).get(SocialQuestionsViewModel.class);

        final Observer<List<Question>> onGetQuestionsOfPageSucceed = new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                mQuestionsAdapter = new QuestionsAdapter(getContext(), questions,
                        mViewModel.getCurrentAppointment().getAnswers());
                mRecyclerView.setAdapter(mQuestionsAdapter);
            }
        };

        final Observer<String> onGetQuestionsOfPageFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.w(TAG, "onChanged: " + error);
            }
        };

        final Observer<Appointment> onUpdateAnswersOfAppointmentSucceed = new Observer<Appointment>() {
            @Override
            public void onChanged(Appointment appointment) {
                if (listener != null) {
                    listener.onContinueFromSocialQuestions();
                }
            }
        };

        final Observer<String> onUpdateAnswersOfAppointmentFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.e(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getGetQuestionsOfPageSucceed().observe(this, onGetQuestionsOfPageSucceed);
        mViewModel.getGetQuestionsOfPageFailed().observe(this, onGetQuestionsOfPageFailed);
        mViewModel.getUpdateAnswersOfAppointmentSucceed().observe(this, onUpdateAnswersOfAppointmentSucceed);
        mViewModel.getUpdateAnswersOfAppointmentFailed().observe(this, onUpdateAnswersOfAppointmentFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.social_questions_fragment, container, false);

        mViewModel.attachSetGetQuestionsOfPageListener();
        mViewModel.attachSetUpdateAnswersOfAppointmentListener();

        mRecyclerView = rootView.findViewById(R.id.questions_recycler);
        final MaterialButton backBtn = rootView.findViewById(R.id.back_btn);
        final MaterialButton continueBtn = rootView.findViewById(R.id.continue_btn);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add loading dialog
                mViewModel.updateAnswersOfAppointment();
            }
        });

        mViewModel.getQuestions(ViewModelEnum.SocialQuestions);

        return rootView;
    }
}
