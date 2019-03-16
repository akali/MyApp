package com.example.hrapp.models;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hrapp.CandidateListActivity;
import com.example.hrapp.R;

import java.util.List;

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.ViewHolder> {

    private Context mContext;

    private List<Candidate> mCandidateList;

    public CandidateAdapter(List<Candidate> candidates, Context context) {
        mCandidateList = candidates;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.candidate_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Candidate candidate = mCandidateList.get(position);
        viewHolder.bind(candidate);
    }

    @Override
    public int getItemCount() {
        return mCandidateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mNameTextView;
        private TextView mEmailTextView;
        private TextView mCandidateLetter;
        private Candidate mCandidate;
        private TextView mDetailsText;
        private TextView mCandidatePosition;
        private TextView mCandidateLevel;
        private Button mCandidateGenerateList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mNameTextView = itemView.findViewById(R.id.candidate_name);
            mEmailTextView = itemView.findViewById(R.id.candidate_email);
            mCandidateLetter = itemView.findViewById(R.id.candidate_letter);
            mDetailsText = itemView.findViewById(R.id.candidate_details);
            mCandidatePosition = itemView.findViewById(R.id.candidate_position);
            mCandidateLevel = itemView.findViewById(R.id.candidate_level);
            mCandidateGenerateList = itemView.findViewById(R.id.candidate_generate_button);
        }

        public void bind(Candidate candidate) {
            mCandidate = candidate;
            mNameTextView.setText(mCandidate.getName());
            mEmailTextView.setText(mCandidate.getEmail());
            mCandidateLetter.setText(String.valueOf(mCandidate.getName().charAt(0)));
            mDetailsText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCandidatePosition.setText(mCandidate.getPosition());
                    mCandidateLevel.setText(mCandidate.getLevel());
                    mCandidatePosition.setVisibility(View.VISIBLE);
                    mCandidateLevel.setVisibility(View.VISIBLE);
                    mCandidateGenerateList.setVisibility(View.VISIBLE);
                }
            });
            mCandidateGenerateList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CandidateListActivity.class);
                    intent.putExtra("candidateDetails", mCandidate);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}