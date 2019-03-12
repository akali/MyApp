package com.example.hrapp;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrapp.models.Position;
import com.example.hrapp.models.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddQuestionActivity extends AppCompatActivity {

    private DatabaseReference mQuestionsDatabaseReference;
    private DatabaseReference mPositionsDatabaseReference;

    private Spinner mPositionSpinner;
    private Spinner mLevelSpinner;
    private EditText mQuestionText;
    private EditText mAnswerText;
    private Button mSaveButton;

    private int mCount;
    private String mLevel;
    private String mPosition;
    private String mQuestion;
    private String mAnswer;
    private ArrayList<String> mPositions;
    private ArrayAdapter<CharSequence> mLevelsAdapter;
    private ArrayAdapter<String> mPositionsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        mPositionSpinner = (Spinner) findViewById(R.id.position_spinner);
        mLevelSpinner = (Spinner) findViewById(R.id.level_spinner);
        mSaveButton = (Button) findViewById(R.id.save_question);
        mQuestionText = (EditText) findViewById(R.id.question_to_add);
        mAnswerText = (EditText) findViewById(R.id.answer_to_add);
        mQuestionsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Questions");
        mPositionsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Positions");
        mPositions = new ArrayList<>();


        mPositionsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Position value = dataSnapshot1.getValue(Position.class);
                    mPositions.add(value.getName());
                }

                mPositionsAdapter = new ArrayAdapter<String>(AddQuestionActivity.this,
                        android.R.layout.simple_spinner_item, mPositions);
                mPositionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mPositionSpinner.setAdapter(mPositionsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Hello", "Failed to read value.", databaseError.toException());
            }
        });

        mPositionSpinner.setOnItemSelectedListener(selectListener);

        mQuestionsDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mCount = (int) dataSnapshot.getChildrenCount() + 1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Hello", "Failed to read value.", databaseError.toException());
            }
        });

        mLevelsAdapter = ArrayAdapter.createFromResource(this, R.array.levels,
                android.R.layout.simple_spinner_item);
        mLevelsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLevelSpinner.setAdapter(mLevelsAdapter);
        mLevelSpinner.setOnItemSelectedListener(selectListener);

        mSaveButton.setOnClickListener(saveListener);
    }

    View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mQuestion = mQuestionText.getText().toString();
            mAnswer = mAnswerText.getText().toString();
            if (mQuestion.isEmpty() || mAnswer.isEmpty()) {
                Toast.makeText(v.getContext(), "Please, fill all the credits =)",
                        Toast.LENGTH_SHORT).show();
            } else {
                mQuestionsDatabaseReference.child(Integer.toString(mCount)).setValue(
                        new Question(mCount, mQuestion, mAnswer, mLevel, mPosition));
                finish();
                Toast.makeText(v.getContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        }
    };

    AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int spinnerId = parent.getId();
            switch (spinnerId) {
                case R.id.level_spinner:
                    mLevel = parent.getItemAtPosition(position).toString();
                    break;
                case R.id.position_spinner:
                    mPosition = parent.getItemAtPosition(position).toString();
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
