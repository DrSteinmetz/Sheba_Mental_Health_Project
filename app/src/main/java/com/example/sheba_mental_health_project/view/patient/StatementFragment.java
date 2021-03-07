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
import com.example.sheba_mental_health_project.viewmodel.StatementViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class StatementFragment extends Fragment {

    private StatementViewModel mViewModel;

    private RecyclerView mRecyclerView;

    private QuestionsAdapter mQuestionsAdapter;

    private final String TAG = "StatementFragment";


    public interface StatementFragmentInterface {
        void onContinueToCategoryQuestions();
    }

    private StatementFragmentInterface listener;

    public static StatementFragment newInstance() {
        return new StatementFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (StatementFragmentInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements StatementFragmentInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.Statement)).get(StatementViewModel.class);

        final Observer<List<Question>> onGetQuestionsOfPageSucceed = new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                if (mQuestionsAdapter == null) {
                    Log.d(TAG, "qwe onChanged: " + questions.size());
                    mQuestionsAdapter = new QuestionsAdapter(getContext(), questions,
                            mViewModel.getCurrentAppointment().getAnswers());
                    mRecyclerView.setAdapter(mQuestionsAdapter);
                } else {
                    mQuestionsAdapter.notifyDataSetChanged();
                }
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
                    listener.onContinueToCategoryQuestions();
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
        final View rootView = inflater.inflate(R.layout.statement_fragment, container, false);

        mRecyclerView = rootView.findViewById(R.id.questions_recycler);
        final MaterialButton continueBtn = rootView.findViewById(R.id.continue_btn);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        mViewModel.attachSetUpdateAnswersOfAppointmentListener();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add loading dialog
                mViewModel.updateAnswersOfAppointment();
            }
        });

        mViewModel.getQuestions(ViewModelEnum.Statement);

        return rootView;
    }
}
