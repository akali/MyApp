package com.example.hrapp.models;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hrapp.R;

import java.util.List;

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.ViewHolder> {

    private List<Candidate> mCandidateList;

    public CandidateAdapter(List<Candidate> candidates) {
        mCandidateList = candidates;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mNameTextView = itemView.findViewById(R.id.candidate_name);
            mEmailTextView = itemView.findViewById(R.id.candidate_email);
            mCandidateLetter = itemView.findViewById(R.id.candidate_letter);
        }

        public void bind(Candidate candidate) {
            mCandidate = candidate;
            mNameTextView.setText(mCandidate.getName());
            mEmailTextView.setText(mCandidate.getEmail());
            mCandidateLetter.setText(String.valueOf(mCandidate.getName().charAt(0)));
        }
    }
}