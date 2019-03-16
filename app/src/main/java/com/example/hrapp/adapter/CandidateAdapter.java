package com.example.hrapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hrapp.R;
import com.example.hrapp.models.Candidate;

import java.util.List;

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.ViewHolder> {

  private List<Candidate> candidates;

  public CandidateAdapter(List<Candidate> candidates) {
    this.candidates = candidates;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
    View view =
      LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_candidate, viewGroup, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    viewHolder.bind(candidates.get(position));
  }

  @Override
  public int getItemCount() {
    return this.candidates.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView nameTextView, bioTextView;
    private Button levelButton;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      nameTextView = itemView.findViewById(R.id.name_text_view);
      bioTextView = itemView.findViewById(R.id.bio_edit_text);
      levelButton = itemView.findViewById(R.id.level_button);
    }

    public void bind(Candidate candidate) {
      nameTextView.setText(candidate.getDisplayName());
      bioTextView.setText(candidate.getBio());
      levelButton.setText(candidate.getLevel());
    }
  }
}
