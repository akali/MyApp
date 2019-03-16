package com.example.hrapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hrapp.models.Comment;
import com.example.hrapp.models.Favorite;
import com.example.hrapp.models.Language;
import com.example.hrapp.models.Position;
import com.example.hrapp.models.Question;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionDetailsActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private DatabaseReference mCommentsDatabaseReference;
    private DatabaseReference mQuestionsDatabaseReference;
    private DatabaseReference mLanguagesDatabaseReference;
    private RecyclerView mRecyclerView;
    private List<Comment> mCommentList;
    private List<Comment> mCommentListFull;
    private CommentAdapter mCommentAdapter;

    private Question mQuestionDetails;
    private Button mQuestionPositionText;
    private Button mQuestionLevelText;
    private TextView mQuestionQuestionText;
    private TextView mQuestionAnswerText;
    private TextView mQuestionLanguagesText;

    private EditText mCommentText;
    private ImageButton mCommentSend;
    private String mLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_details);

        mFirebaseAuth = FirebaseAuth.getInstance();

        if (mFirebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        mUser = mFirebaseAuth.getCurrentUser();

        Intent intent = getIntent();
        mQuestionDetails = intent.getParcelableExtra("questionDetails");
        mQuestionPositionText = (Button) findViewById(R.id.details_position);
        mQuestionLevelText = (Button) findViewById(R.id.details_level);
        mQuestionQuestionText = (TextView) findViewById(R.id.details_question);
        mQuestionAnswerText = (TextView) findViewById(R.id.details_answer);
        mQuestionLanguagesText = (TextView) findViewById(R.id.details_languages);
        mCommentText = (EditText) findViewById(R.id.comment_message);
        mCommentSend = (ImageButton) findViewById(R.id.send_button);

        mRecyclerView = (RecyclerView) findViewById(R.id.comments_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance();
        mCommentsDatabaseReference = mDatabase.getReference().child("Comments");
        mQuestionsDatabaseReference = mDatabase.getReference().child("Questions");
        mLanguagesDatabaseReference = mQuestionsDatabaseReference.child(Integer.toString(
                mQuestionDetails.getId())).child("languages");
        mCommentList = new ArrayList<Comment>();
        mCommentListFull = new ArrayList<Comment>();
        mLanguages = "";


        mCommentSend.setOnClickListener(sendListener);
        initQuestionDetails();
        initCommentList();
    }

    private void initQuestionDetails() {
        mQuestionPositionText.setText(mQuestionDetails.getPosition());
        mQuestionLevelText.setText(mQuestionDetails.getLevel());
        mQuestionQuestionText.setText(mQuestionDetails.getQuestion());
        mQuestionAnswerText.setText(mQuestionDetails.getAnswer());

        mLanguagesDatabaseReference.addValueEventListener(
                new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Language language = dataSnapshot1.getValue(Language.class);
                    mLanguages = mLanguages + language.getTitle() + " ";
                }
                mQuestionLanguagesText.setText(mLanguages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Hello", "Failed to read value.", databaseError.toException());
            }
        });

        switch (mQuestionDetails.getLevel()) {
            case "Junior":
                mQuestionLevelText.setBackgroundResource(R.drawable.button_red_rounded);
                mQuestionLevelText.setTextColor(Color.parseColor("#FF515E"));
                break;
            case "Middle":
                mQuestionLevelText.setBackgroundResource(R.drawable.button_yellow_rounded);
                mQuestionLevelText.setTextColor(Color.parseColor("#FF8E09"));
                break;
            case "Senior":
                mQuestionLevelText.setBackgroundResource(R.drawable.button_green_rounded);
                mQuestionLevelText.setTextColor(Color.parseColor("#00D053"));
                break;
        }

    }

    private void initCommentList() {
        mCommentsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Comment comment = dataSnapshot1.getValue(Comment.class);
                    mCommentListFull.add(comment);
                    if (comment.getQuestionId() == mQuestionDetails.getId()) {
                        mCommentList.add(comment);
                    }
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

    View.OnClickListener sendListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String message = mCommentText.getText().toString();
            int cId = mCommentListFull.size() + 1;
            mCommentsDatabaseReference.child(Integer.toString(cId)).setValue(
                    new Comment(cId, mQuestionDetails.getId(), mUser.getEmail(), message));
            mCommentList.clear();
            mCommentListFull.clear();
            mCommentText.setText("");
        }
    };
}
