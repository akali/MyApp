package com.example.hrapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrapp.models.Favorite;
import com.example.hrapp.models.Question;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> mQuestionList;

    private List<Favorite> mFavoriteList;

    public List<Favorite> getFavoriteList() {
        return mFavoriteList;
    }

    public void setFavoriteList(List<Favorite> favoriteList) {
        mFavoriteList = favoriteList;
    }

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
        Question question = mQuestionList.get(i);
        questionViewHolder.bind(question, mFavoriteList);
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Question mQuestion;

        private TextView mQuestionText;

        private TextView mAnswerText;

        private ImageButton mFavoriteButton;

        private TextView mLevelText;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            mQuestionText = itemView.findViewById(R.id.question_question);
            mAnswerText = itemView.findViewById(R.id.question_answer);
            mFavoriteButton = itemView.findViewById(R.id.favorite_question);
            mFavoriteButton.setOnClickListener(this);
            mLevelText = itemView.findViewById(R.id.question_level);
        }

        public void bind(Question question, List<Favorite> favorites) {
            mQuestion = question;
            mQuestionText.setText(mQuestion.getQuestion());
            mAnswerText.setText(mQuestion.getAnswer());
            mLevelText.setText(mQuestion.getLevel());

            mFavoriteButton.setImageResource(R.drawable.ic_star_border_grey_24dp);

            for (Favorite f : favorites) {
                if (f.getQuestionId() == mQuestion.getId()) {
                    mFavoriteButton.setImageResource(R.drawable.ic_star_yellow_24dp);
                }
            }
        }

        @Override
        public void onClick(View v) {
            //mFavoriteButton.setImageResource(R.drawable.ic_star_yellow_24dp);
        }
    }
}
