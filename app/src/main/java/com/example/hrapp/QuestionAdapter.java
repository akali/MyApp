package com.example.hrapp;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.hrapp.models.Favorite;
import com.example.hrapp.models.Question;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>
        implements Filterable {

    private List<Question> mQuestionList;
    private List<Question> mQuestionListFull;

    private List<Favorite> mFavoriteList;

    private RecyclerViewClickItemListener<Question> mListener;

    public List<Favorite> getFavoriteList() {
        return mFavoriteList;
    }

    public void setFavoriteList(List<Favorite> favoriteList) {
        mFavoriteList = favoriteList;
    }

    public QuestionAdapter(List<Question> questionList, RecyclerViewClickItemListener<Question> listener) {
        mQuestionList = questionList;
        mQuestionListFull = new ArrayList<>(questionList);
        mListener = listener;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.question_list_item, viewGroup, false);
        return new QuestionViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder questionViewHolder, int i) {
        Question question = mQuestionList.get(i);
        questionViewHolder.bind(question, mFavoriteList, i);
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Question> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mQuestionListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Question question : mQuestionListFull) {
                    if (question.getQuestion().toLowerCase().contains(filterPattern)) {
                        filteredList.add(question);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mQuestionList.clear();
            mQuestionList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Question mQuestion;

        private TextView mQuestionText;
        private TextView mAnswerText;
        private ToggleButton mFavoriteButton;
        private Button mLevelButton;

        private RecyclerViewClickItemListener<Question> mListener;
        private int mPosition;

        public QuestionViewHolder(@NonNull View itemView, RecyclerViewClickItemListener<Question> listener) {
            super(itemView);

            mQuestionText = itemView.findViewById(R.id.question_question);
            mAnswerText = itemView.findViewById(R.id.question_answer);
            mFavoriteButton = itemView.findViewById(R.id.favorite_question);
            mFavoriteButton.setOnClickListener(this);
            mLevelButton = itemView.findViewById(R.id.question_level_button);
            mListener = listener;
            mQuestionText.setOnClickListener(this);
            mFavoriteButton.setOnClickListener(this);
        }

        public void bind(Question question, List<Favorite> favorites, int position) {
            mPosition = position;
            mQuestion = question;
            mQuestionText.setText(mQuestion.getQuestion());
            mAnswerText.setText(mQuestion.getAnswer());
            mLevelButton.setText(mQuestion.getLevel());
            if (mLevelButton.getText().equals("Junior")) {
                mLevelButton.setBackgroundResource(R.drawable.button_red_rounded);
                mLevelButton.setTextColor(Color.parseColor("#FF515E"));
            } else if (mLevelButton.getText().equals("Middle")) {
                mLevelButton.setBackgroundResource(R.drawable.button_yellow_rounded);
                mLevelButton.setTextColor(Color.parseColor("#FF8E09"));
            } else if (mLevelButton.getText().equals("Senior")) {
                mLevelButton.setBackgroundResource(R.drawable.button_green_rounded);
                mLevelButton.setTextColor(Color.parseColor("#00D053"));
            }

            mFavoriteButton.setSelected(false);
            for (Favorite f : favorites) {
                if (f.getQuestionId() == mQuestion.getId()) {
                    mFavoriteButton.setSelected(true);
                }
            }
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, mQuestion, mPosition);
        }
    }
}
