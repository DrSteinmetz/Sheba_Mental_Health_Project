package com.example.sheba_mental_health_project.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sheba_mental_health_project.R;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.slider.Slider;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.AnswersViewHolder> {

    private final List<Question> mQuestions;
    private final List<Answer> mAnswers;

    private final int BINARY_TYPE = 1;
    private final int SLIDER_TYPE = 2;
    private final int NUMBER_TYPE = 3;
    private final int OPEN_TYPE = 4;

    private final String TAG = "AnswersAdapter";


    public AnswersAdapter(final List<Question> questions, final List<Answer> answers) {
        this.mQuestions = questions;
        this.mAnswers = answers;
    }

    public class AnswersViewHolder extends RecyclerView.ViewHolder {

        private MaterialCheckBox binaryQuestionCb;
        private TextView sliderTv;
        private TextView sliderValueTv;
        private Slider sliderAnswer;
        private TextView numberTv;
        private MaterialTextView numberAnswerTv;
        private TextView openTv;
        private MaterialTextView openAnswerTv;

        public AnswersViewHolder(@NonNull View itemView, final int viewType) {
            super(itemView);

            switch (viewType) {
                case BINARY_TYPE:
                    binaryQuestionCb = itemView.findViewById(R.id.question_cb);
                    binaryQuestionCb.setClickable(false);
                    break;
                case SLIDER_TYPE:
                    sliderTv = itemView.findViewById(R.id.question_tv);
                    sliderValueTv = itemView.findViewById(R.id.slider_value_tv);
                    sliderAnswer = itemView.findViewById(R.id.answer_slider);
                    break;
                case NUMBER_TYPE:
                    numberTv = itemView.findViewById(R.id.question_tv);
                    numberAnswerTv = itemView.findViewById(R.id.answer_tv);
                    break;
                case OPEN_TYPE:
                    openTv = itemView.findViewById(R.id.question_tv);
                    openAnswerTv = itemView.findViewById(R.id.answer_tv);
                    break;
            }
        }
    }


    @NonNull
    @Override
    public AnswersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view;

        switch (viewType) {
            case BINARY_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.question_binary_cell_layout, null);
                break;
            case SLIDER_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.answer_slider_cell_layout, null);
                break;
            case NUMBER_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.answer_number_cell_layout, null);
                break;
            case OPEN_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.answer_open_cell_layout, null);
                break;
            default:
                view = null;
                break;
        }

        return new AnswersViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersViewHolder holder, int position) {
        final Question question = mQuestions.get(position);

        if (question != null) {
            final String questionText = question.getQuestion();
            final Answer answer;

            final int indexOfAnswer = mAnswers.indexOf(new Answer(question.getId()));
            if (indexOfAnswer != -1) {
                answer = mAnswers.get(indexOfAnswer);
            } else {
                answer = null;
            }

            switch (getItemViewType(position)) {
                case BINARY_TYPE:
                    final MaterialCheckBox cb = holder.binaryQuestionCb;

                    cb.setText(questionText);
                    cb.setChecked(answer != null);
                    break;
                case SLIDER_TYPE:
                    final TextView sliderTv = holder.sliderTv;
                    final TextView sliderValueTv = holder.sliderValueTv;
                    final Slider slider = holder.sliderAnswer;

                    sliderTv.setText(questionText);

                    if (answer != null) {
                        final String answerValue = ((AnswerOpen) answer).getAnswer();

                        sliderValueTv.setText(answerValue);
                        slider.setValue(Float.parseFloat(answerValue));
                    } else {
                        sliderValueTv.setText("0");
                        slider.setValue(0);
                    }
                    break;
                case NUMBER_TYPE:
                    final TextView numberTv = holder.numberTv;
                    final MaterialTextView numberAnswerTv = holder.numberAnswerTv;

                    numberTv.setText(questionText);

                    if (answer != null) {
                        final String answerValue = ((AnswerOpen) answer).getAnswer();

                        numberAnswerTv.setText(answerValue);
                    } else {
                        numberAnswerTv.setText("");
                    }
                    break;
                case OPEN_TYPE:
                    final TextView openTv = holder.openTv;
                    final MaterialTextView openAnswerTv = holder.openAnswerTv;

                    openTv.setText(questionText);

                    if (answer != null) {
                        final String answerValue = ((AnswerOpen) answer).getAnswer();

                        openAnswerTv.setText(answerValue);
                    } else {
                        openAnswerTv.setText("");
                    }
                    break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        final Question question = mQuestions.get(position);
        int questionType;

        switch (question.getQuestionType()) {
            case Binary:
                questionType = BINARY_TYPE;
                break;
            case Slider:
                questionType = SLIDER_TYPE;
                break;
            case Number:
                questionType = NUMBER_TYPE;
                break;
            case Open:
                questionType = OPEN_TYPE;
                break;
            default:
                questionType = 0;
                break;
        }

        return questionType;
    }

    @Override
    public int getItemCount() {
        return this.mQuestions.size();
    }
}
