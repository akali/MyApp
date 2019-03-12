package com.example.hrapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.hrapp.models.Comment;
import com.example.hrapp.models.Position;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionDetailsActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mCommentsDatabaseReference;
    private RecyclerView mRecyclerView;
    private List<Comment> mCommentList;
    private CommentAdapter mCommentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_details);

        mRecyclerView = (RecyclerView) findViewById(R.id.comments_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance();
        mCommentsDatabaseReference = mDatabase.getReference().child("Comments");
        mCommentList = new ArrayList<Comment>();

        initCommentList();
    }

    private void initCommentList() {
        mCommentsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Comment comment = dataSnapshot1.getValue(Comment.class);
                    mCommentList.add(comment);
                }
                mCommentAdapter = new CommentAdapter(mCommentList);
                mRecyclerView.setAdapter(mCommentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Hello", "Failed to read value.", databaseError.toException());
            }
        });
    }
}
