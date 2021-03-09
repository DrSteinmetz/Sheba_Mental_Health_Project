package com.example.sheba_mental_health_project.model;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sheba_mental_health_project.R;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {

    private final Context mContext;

    private final List<Question> mQuestions;
    private final List<Answer> mAnswers;

    private final int BINARY_TYPE = 1;
    private final int SLIDER_TYPE = 2;
    private final int NUMBER_TYPE = 3;
    private final int OPEN_TYPE = 4;

    private final String TAG = "QuestionsAdapter";


    public QuestionsAdapter(final Context context, final List<Question> questions,
                            final List<Answer> answers) {
        this.mContext = context;
        this.mQuestions = questions;
        this.mAnswers = answers;
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder {

        private MaterialCheckBox binaryQuestionCb;
        private TextView sliderTv;
        private Slider sliderQuestion;
        private TextView numberTv;
        private TextInputEditText numberQuestionEt;
        private TextView openTv;
        private TextInputEditText openQuestionEt;

        public QuestionsViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            switch (viewType) {
                case BINARY_TYPE:
                    initializeBinaryQuestion(itemView);
                    break;
                case SLIDER_TYPE:
                    initializeSliderQuestion(itemView);
                    break;
                case NUMBER_TYPE:
                    initializeNumberQuestion(itemView);
                    break;
                case OPEN_TYPE:
                    initializeOpenQuestion(itemView);
                    break;
            }
        }

        private void initializeBinaryQuestion(final View itemView) {
            binaryQuestionCb = itemView.findViewById(R.id.question_cb);

            binaryQuestionCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String questionId = mQuestions.get(getAdapterPosition()).getId();
                    if (isChecked) {
                        if (!mAnswers.contains(new Answer(questionId))) {
                            mAnswers.add(new AnswerBinary(questionId));
                            Log.d(TAG, "qwe onCheckedChanged: Added, Size: " + mAnswers.size());
                        }
                    } else {
                        mAnswers.remove(new Answer(questionId));
                        Log.d(TAG, "qwe onCheckedChanged: Removed");
                    }
                }
            });
        }

        private void initializeSliderQuestion(final View itemView) {
            sliderTv = itemView.findViewById(R.id.question_tv);
            sliderQuestion = itemView.findViewById(R.id.question_slider);

            sliderQuestion.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
                @Override
                public void onStartTrackingTouch(@NonNull Slider slider) {}

                @Override
                public void onStopTrackingTouch(@NonNull Slider slider) {
                    final String questionId = mQuestions.get(getAdapterPosition()).getId();

                    setOpenAnswerValue(questionId, slider.getValue() + "");
                }
            });
        }

        private void initializeNumberQuestion(final View itemView) {
            numberTv = itemView.findViewById(R.id.question_tv);
            numberQuestionEt = itemView.findViewById(R.id.answer_et);

            numberQuestionEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    final String questionId = mQuestions.get(getAdapterPosition()).getId();

                    setOpenAnswerValue(questionId, s.toString());
                }
            });
        }

        private void initializeOpenQuestion(final View itemView) {
            openTv = itemView.findViewById(R.id.question_tv);
            openQuestionEt = itemView.findViewById(R.id.answer_et);

            openQuestionEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    final String questionId = mQuestions.get(getAdapterPosition()).getId();

                    setOpenAnswerValue(questionId, s.toString());
                }
            });
        }

        private void setOpenAnswerValue(final String questionId, final String answerValue) {
            final int indexOfAnswer = mAnswers.indexOf(new Answer(questionId));
            if (indexOfAnswer == -1 && !answerValue.isEmpty()) {
                mAnswers.add(new AnswerOpen(questionId, answerValue));
            } else if (indexOfAnswer != -1) {
                final AnswerOpen openAnswer = ((AnswerOpen) mAnswers.get(indexOfAnswer));
                if (!answerValue.isEmpty()) {
                    openAnswer.setAnswer(answerValue);
                } else {
                    mAnswers.remove(openAnswer);
                }
            }
        }
    }

    @NonNull
    @Override
    public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view;

        switch (viewType) {
            case BINARY_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.question_binary_cell_layout, null);
                break;
            case SLIDER_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.question_slider_cell_layout, null);
                break;
            case NUMBER_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.question_number_cell_layout, null);
                break;
            case OPEN_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.question_open_cell_layout, null);
                break;
            default:
                view = null;
                break;
        }

        return new QuestionsViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsViewHolder holder, int position) {
        final Question question = mQuestions.get(position);
        Log.d(TAG, "oron onBindViewHolder: " + mAnswers);
        if (question != null) {
            final String questionText = question.getQuestion();
            final Answer answer;

            final int indexOfAnswer = mAnswers.indexOf(new Answer(question.getId()));
            if (indexOfAnswer != -1) {
                answer = mAnswers.get(indexOfAnswer);
            } else {
                answer = null;
            }

            Log.d(TAG, "oron onBindViewHolder: " + answer);
            Log.d(TAG, "oron onBindViewHolder: " + question.getId());

            switch (getItemViewType(position)) {
                case BINARY_TYPE:
                    final MaterialCheckBox cb = holder.binaryQuestionCb;

                    cb.setText(questionText);
                    cb.setChecked(answer != null);
                    break;
                case SLIDER_TYPE:
                    final TextView sliderTv = holder.sliderTv;
                    final Slider slider = holder.sliderQuestion;

                    sliderTv.setText(questionText);

                    if (answer != null) {
                        final String answerValue = ((AnswerOpen) answer).getAnswer();
                        slider.setValue(Float.parseFloat(answerValue));
                    } else {
                        slider.setValue(0);
                    }
                    break;
                case NUMBER_TYPE:
                    final TextView numberTv = holder.numberTv;
                    final TextInputEditText numberEt = holder.numberQuestionEt;

                    numberTv.setText(questionText);

                    if (answer != null) {
                        final String answerValue = ((AnswerOpen) answer).getAnswer();
                        numberEt.setText(answerValue);
                    } else {
                        numberEt.setText("");
                    }
                    break;
                case OPEN_TYPE:
                    final TextView openTv = holder.openTv;
                    final TextInputEditText openEt = holder.openQuestionEt;

                    openTv.setText(questionText);

                    if (answer != null) {
                        final String answerValue = ((AnswerOpen) answer).getAnswer();
                        openEt.setText(answerValue);
                    } else {
                        openEt.setText("");
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
