package com.example.hrapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.hrapp.models.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionListActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private List<Question> mQuestionList;
    private RecyclerView mRecyclerView;
    private QuestionAdapter mQuestionAdapter;
    private FloatingActionButton mAddQuestionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        final String positionName = getIntent().getStringExtra("positionName");

        mAddQuestionButton = (FloatingActionButton) findViewById(R.id.add_question);
        mRecyclerView = (RecyclerView) findViewById(R.id.questions_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Questions");
        mQuestionList = new ArrayList<Question>();

        mAddQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddQuestionActivity.class);
                startActivity(intent);
                mQuestionList.clear();
            }
        });

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Question value = dataSnapshot1.getValue(Question.class);
                    if (value.getPosition().equals(positionName)) {
                        mQuestionList.add(value);
                    }
                }
                mQuestionAdapter = new QuestionAdapter(mQuestionList);
                mRecyclerView.setAdapter(mQuestionAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Hello", "Failed to read value.", databaseError.toException());
            }
        });
    }
}
