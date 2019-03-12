package com.example.hrapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hrapp.models.Question;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<Question> mQuestionList;

    public FavoriteAdapter(List<Question> questionList) {
        mQuestionList = questionList;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.question_list_item, viewGroup, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder favoriteViewHolder, int i) {
        favoriteViewHolder.mQuestionText.setText(mQuestionList.get(i).getQuestion());
        favoriteViewHolder.mAnswerText.setText(mQuestionList.get(i).getAnswer());
        favoriteViewHolder.mLevelText.setText(mQuestionList.get(i).getLevel());
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private TextView mQuestionText;

        private TextView mAnswerText;

        private TextView mLevelText;

        private ImageButton mFavoriteButton;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            mQuestionText = (TextView) itemView.findViewById(R.id.question_question);
            mAnswerText = itemView.findViewById(R.id.question_answer);
            mFavoriteButton = itemView.findViewById(R.id.favorite_question);
            mLevelText = itemView.findViewById(R.id.question_level);
            mFavoriteButton.setImageResource(R.drawable.ic_star_yellow_24dp);
        }
    }
}
