package com.example.hrapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

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
        favoriteViewHolder.mLevelButton.setText(mQuestionList.get(i).getLevel());

        if (favoriteViewHolder.mLevelButton.getText().equals("Junior")) {
            favoriteViewHolder.mLevelButton.setBackgroundResource(R.drawable.button_red_rounded);
            favoriteViewHolder.mLevelButton.setTextColor(Color.parseColor("#FF515E"));
        } else if (favoriteViewHolder.mLevelButton.getText().equals("Middle")) {
            favoriteViewHolder.mLevelButton.setBackgroundResource(R.drawable.button_yellow_rounded);
            favoriteViewHolder.mLevelButton.setTextColor(Color.parseColor("#FF8E09"));
        } else if (favoriteViewHolder.mLevelButton.getText().equals("Senior")) {
            favoriteViewHolder.mLevelButton.setBackgroundResource(R.drawable.button_green_rounded);
            favoriteViewHolder.mLevelButton.setTextColor(Color.parseColor("#00D053"));
        }
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private TextView mQuestionText;

        private TextView mAnswerText;

        private Button mLevelButton;

        private ToggleButton mFavoriteButton;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            mQuestionText = (TextView) itemView.findViewById(R.id.question_question);
            mAnswerText = itemView.findViewById(R.id.question_answer);
            mFavoriteButton = itemView.findViewById(R.id.favorite_question);
            mLevelButton = itemView.findViewById(R.id.question_level_button);
            mFavoriteButton.setSelected(true);
        }
    }
}
