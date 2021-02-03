package com.example.sheba_mental_health_project.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sheba_mental_health_project.R;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {

    private final Context mContext;
    private final List<Question> mQuestions;
    private final List<String> mAnswers;

    private final String TAG = "QuestionsAdapter";

    public QuestionsAdapter(final Context context, final List<Question> questions,
                            final List<String> answers) {
        this.mContext = context;
        this.mQuestions = questions;
        this.mAnswers = answers;
    }

    /*public interface QuestionsAdapterInterface {
        void onQuestionCheckedChange(View view, boolean isChecked);
    }

    private QuestionsAdapterInterface listener;

    public void setQuestionListener(final QuestionsAdapterInterface listener) {
        this.listener = listener;
    }*/

    public class QuestionsViewHolder extends RecyclerView.ViewHolder {

        private final MaterialCheckBox questionCb;

        public QuestionsViewHolder(@NonNull View itemView) {
            super(itemView);

            questionCb = itemView.findViewById(R.id.question_cb);

            questionCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    /*if (listener != null) {
                        listener.onQuestionCheckedChange(buttonView, isChecked);
                    }*/

                    if (isChecked) {
                        if (!mAnswers.contains(mQuestions.get(getAdapterPosition()).getId())) {
                            mAnswers.add(mQuestions.get(getAdapterPosition()).getId());
                            Log.d(TAG, "qwe onCheckedChanged: Added, Size: " + mAnswers.size());
                        }
                    } else {
                        mAnswers.remove(mQuestions.get(getAdapterPosition()).getId());
                        Log.d(TAG, "qwe onCheckedChanged: Removed");
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_cell_layout, null);
        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsViewHolder holder, int position) {
        final Question question = mQuestions.get(position);

        final MaterialCheckBox cb = holder.questionCb;

        cb.setText(question.getQuestion());
        cb.setChecked(mAnswers.contains(question.getId()));
    }

    @Override
    public int getItemCount() {
        return this.mQuestions.size();
    }
}
