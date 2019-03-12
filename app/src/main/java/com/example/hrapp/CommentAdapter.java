package com.example.hrapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hrapp.models.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> mCommentList;

    public CommentAdapter(List<Comment> commentList) {
        mCommentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.comment_list_item, viewGroup, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
        commentViewHolder.mUserEmailText.setText(mCommentList.get(i).getUserEmail());
        commentViewHolder.mCommentText.setText(mCommentList.get(i).getComment());
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        private TextView mUserEmailText;
        private TextView mCommentText;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            mUserEmailText = (TextView) itemView.findViewById(R.id.comment_user_email);
            mCommentText = (TextView) itemView.findViewById(R.id.comment_text);
        }
    }
}
