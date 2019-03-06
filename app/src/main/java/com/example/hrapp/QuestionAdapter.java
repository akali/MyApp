package com.example.hrapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hrapp.models.Question;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> mQuestionList;

    public QuestionAdapter(List<Question> questionList) {
        mQuestionList = questionList;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.question_list_item, viewGroup, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder questionViewHolder, int i) {
        questionViewHolder.mQuestionText.setText(mQuestionList.get(i).getQuestion());
        questionViewHolder.mAnswerText.setText(mQuestionList.get(i).getAnswer());
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {

        private TextView mQuestionText;

        private TextView mAnswerText;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            mQuestionText = itemView.findViewById(R.id.question_question);
            mAnswerText = itemView.findViewById(R.id.question_answer);
        }
    }
}
