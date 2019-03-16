package com.example.hrapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.hrapp.models.Candidate;
import com.example.hrapp.models.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CandidateListActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mQuestionsDatabaseReference;
    private Candidate mCandidateDetails;
    private List<Question> mQuestionListForCandidate;
    private RecyclerView mRecyclerView;
    private CandidateQAdapter mAdapter;
    private Button mResultScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);

        mResultScore = (Button) findViewById(R.id.result_score_button);
        mRecyclerView = (RecyclerView) findViewById(R.id.for_candidates_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        mCandidateDetails = intent.getParcelableExtra("candidateDetails");
        mDatabase = FirebaseDatabase.getInstance();
        mQuestionsDatabaseReference = mDatabase.getReference().child("Questions");
        mQuestionListForCandidate = new ArrayList<Question>();

        initQuestionListForCandidate();
        mResultScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void initQuestionListForCandidate() {
        mQuestionsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Question question = dataSnapshot1.getValue(Question.class);
                    if (question.getLevel().equals(mCandidateDetails.getLevel()) &&
                    question.getPosition().equals(mCandidateDetails.getPosition())) {
                        mQuestionListForCandidate.add(question);
                    }
                }
                mAdapter = new CandidateQAdapter(mQuestionListForCandidate, getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Hello", "Failed to read value.", databaseError.toException());
            }
        });

    }

}
