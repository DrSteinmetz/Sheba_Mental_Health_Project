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
import com.example.sheba_mental_health_project.model.enums.FrequencyEnum;
import com.example.sheba_mental_health_project.model.enums.QuestionTypeEnum;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {

    private final Context mContext;

    private final List<Question> mQuestions;
    private final List<Answer> mAnswers;

    private final int BINARY_TYPE = 1;
    private final int SLIDER_TYPE = 2;
    private final int NUMBER_TYPE = 3;
    private final int OPEN_TYPE = 4;
    private final int RADIO_TYPE = 5;

    private final String TAG = "QuestionsAdapter";


    public QuestionsAdapter(final Context context, final List<Question> questions,
                            final List<Answer> answers) {
        this.mContext = context;
        this.mQuestions = questions;
        this.mAnswers = answers;
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder {

        private MaterialCheckBox binaryQuestionCb;
        private TextInputLayout binaryQuestionLayout;
        private TextInputEditText binaryQuestionEt;
        private TextView sliderTv;
        private Slider sliderQuestion;
        private TextView numberTv;
        private TextInputEditText numberQuestionEt;
        private TextView openTv;
        private TextInputEditText openQuestionEt;
        private TextView radioTv;
        private ChipGroup chipGroup;
        private final TextView asteriskTv;

        public QuestionsViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            asteriskTv = itemView.findViewById(R.id.asterisk_tv);

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
                case RADIO_TYPE:
                    initializeRadioQuestion(itemView);
                    break;
            }
        }

        private void initializeBinaryQuestion(final View itemView) {
            binaryQuestionCb = itemView.findViewById(R.id.question_cb);
            binaryQuestionLayout = itemView.findViewById(R.id.answer_layout);
            binaryQuestionEt = itemView.findViewById(R.id.answer_et);

            binaryQuestionCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String questionId = mQuestions.get(getAdapterPosition()).getId();

                    if (isChecked) {
                        if (!mAnswers.contains(new Answer(questionId))) {
                            mAnswers.add(new AnswerBinary(questionId));
                        }
                    } else {
                        mAnswers.remove(new Answer(questionId));
                    }

                    binaryQuestionLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                }
            });

            binaryQuestionEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    final String answerDetails = s.toString().trim();
                    final String questionId = mQuestions.get(getAdapterPosition()).getId();
                    final int answerIndex = mAnswers.indexOf(new Answer(questionId));
                    ((AnswerBinary) mAnswers.get(answerIndex)).setAnswerDetails(answerDetails);
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

        private void initializeRadioQuestion(View itemView) {
            radioTv = itemView.findViewById(R.id.question_tv);
            chipGroup = itemView.findViewById(R.id.chip_group);

            chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(ChipGroup group, int checkedId) {
                    final String questionId = mQuestions.get(getAdapterPosition()).getId();

                    if (chipGroup.getCheckedChipId() != View.NO_ID) {
                        final Chip checkedChip = itemView.findViewById(checkedId);

                        setOpenAnswerValue(questionId, checkedChip.getTag().toString());
                    } else {
                        setOpenAnswerValue(questionId, "");
                    }
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
                        .inflate(R.layout.question_binary_cell_layout, parent,false);
                break;
            case SLIDER_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.question_slider_cell_layout, parent,false);
                break;
            case NUMBER_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.question_number_cell_layout, parent,false);
                break;
            case OPEN_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.question_open_cell_layout, parent,false);
                break;
            case RADIO_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.question_radio_group_cell_layout, parent,false);
                break;
            default:
                view = null;
        }

        return new QuestionsViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsViewHolder holder, int position) {
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

            holder.asteriskTv.setVisibility(question.isMandatory() ? View.VISIBLE : View.GONE);
            Log.d(TAG, "oron onBindViewHolder: isMandatory=" + question.isMandatory());

            switch (getItemViewType(position)) {
                case BINARY_TYPE:
                    final MaterialCheckBox cb = holder.binaryQuestionCb;

                    cb.setText(questionText);
                    cb.setChecked(answer != null);

                    if (answer != null) {
                        final String answerDetails = ((AnswerBinary) answer).getAnswerDetails();
                        holder.binaryQuestionEt.setText(answerDetails);
                        holder.binaryQuestionLayout.setVisibility(View.VISIBLE);
                    } else {
                        holder.binaryQuestionLayout.setVisibility(View.GONE);
                    }
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
                case RADIO_TYPE:
                    final TextView radioTv = holder.radioTv;
                    final ChipGroup chipGroup = holder.chipGroup;

                    radioTv.setText(questionText);

                    if (answer != null) {
                        final String answerValue = ((AnswerOpen) answer).getAnswer();
                        Log.d(TAG, "onBindViewHolder: " +
                                Arrays.asList(FrequencyEnum.values())
                                        .contains(FrequencyEnum.valueOf(answerValue)));
                        ((Chip) chipGroup.findViewWithTag(answerValue)).setChecked(true);
                    } else {
                        chipGroup.clearCheck();
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
            case Radio:
                questionType = RADIO_TYPE;
                break;
            default:
                questionType = 0;
        }

        return questionType;
    }

    @Override
    public int getItemCount() {
        return this.mQuestions.size();
    }


    public boolean isAllMandatoryQuestionsFilled() {

        for (Question question : mQuestions) {
            if ((question.getQuestionType().equals(QuestionTypeEnum.Number) ||
                    question.getQuestionType().equals(QuestionTypeEnum.Open)) &&
                    question.isMandatory()) {
                final int answerIndex = mAnswers.indexOf(new Answer(question.getId()));

                if (answerIndex != -1) {
                    final Answer answer = mAnswers.get(answerIndex);

                    if (answer instanceof AnswerOpen) {
                        if (((AnswerOpen) answer).getAnswer().trim().isEmpty()) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
        }

        return true;
    }
}
