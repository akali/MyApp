package com.example.hrapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hrapp.models.Question;

import java.util.ArrayList;
import java.util.List;

public class CandidateQAdapter extends RecyclerView.Adapter<CandidateQAdapter.CandidateQViewHolder> {

  private List<Question> mQuestionList;

  private Context mContext;

  private List<Integer> scores;

  public CandidateQAdapter(List<Question> questionList, Context context) {
    mQuestionList = questionList;
    mContext = context;
    this.scores = new ArrayList<>();
    for (int i = 0; i < questionList.size(); ++i) {
      this.scores.add(0);
    }
  }


  @NonNull
  @Override
  public CandidateQViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
    View view = layoutInflater.inflate(R.layout.question_candidate_item, viewGroup, false);
    return new CandidateQViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull CandidateQViewHolder candidateQViewHolder, int i) {
    Question question = mQuestionList.get(i);
    candidateQViewHolder.bind(question, i);
  }

  @Override
  public int getItemCount() {
    return mQuestionList.size();
  }

  public int calculateScore() {
    int result = 0;

    for (int i = 0; i < this.scores.size(); ++i)
      result += this.scores.get(i);

    return result;
  }


  class CandidateQViewHolder extends RecyclerView.ViewHolder {

    private Question mQuestion;

    private TextView mQuestionText;
    private TextView mAnswerText;
    private Button mLevelButton;

    private Spinner mScoreSpinner;
    private ArrayAdapter<CharSequence> mScoresAdapter;
    private int mPosition;

    public CandidateQViewHolder(@NonNull View itemView) {
      super(itemView);

      mQuestionText = itemView.findViewById(R.id.question_question);
      mAnswerText = itemView.findViewById(R.id.question_answer);
      mLevelButton = itemView.findViewById(R.id.question_level_button);
      mScoreSpinner = itemView.findViewById(R.id.score_spinner);

      mScoresAdapter = ArrayAdapter.createFromResource(mContext, R.array.scores,
        android.R.layout.simple_spinner_item);
      mScoresAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      mScoreSpinner.setAdapter(mScoresAdapter);
      mScoreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          scores.set(mPosition, position + 1);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
          scores.set(mPosition, 0);
        }
      });
    }

    public void bind(Question question, int i) {
      mPosition = i;
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
    }
  }
}
