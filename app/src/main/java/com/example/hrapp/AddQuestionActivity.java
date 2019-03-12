package com.example.hrapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrapp.models.Language;
import com.example.hrapp.models.Position;
import com.example.hrapp.models.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionActivity extends AppCompatActivity {

    private DatabaseReference mQuestionsDatabaseReference;
    private DatabaseReference mPositionsDatabaseReference;
    private DatabaseReference mLanguagesDatabaseReference;

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

    private CheckBox mJavascript;
    private CheckBox mCsharp;
    private CheckBox mJava;
    private CheckBox mSql;
    private CheckBox mPython;
    private List<Language> mLanguages;
    private int mLanguagesCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        mJavascript = (CheckBox) findViewById(R.id.check_javascript);
        mCsharp = (CheckBox) findViewById(R.id.check_csharp);
        mJava = (CheckBox) findViewById(R.id.check_java);
        mSql = (CheckBox) findViewById(R.id.check_sql);
        mPython = (CheckBox) findViewById(R.id.check_python);
        mLanguages = new ArrayList<Language>();
        mLanguagesCount = 0;
        mJavascript.setOnCheckedChangeListener(checkedListener);
        mCsharp.setOnCheckedChangeListener(checkedListener);
        mJava.setOnCheckedChangeListener(checkedListener);
        mSql.setOnCheckedChangeListener(checkedListener);
        mPython.setOnCheckedChangeListener(checkedListener);

        mPositionSpinner = (Spinner) findViewById(R.id.position_spinner);
        mLevelSpinner = (Spinner) findViewById(R.id.level_spinner);
        mSaveButton = (Button) findViewById(R.id.save_question);
        mQuestionText = (EditText) findViewById(R.id.question_to_add);
        mAnswerText = (EditText) findViewById(R.id.answer_to_add);
        mPositions = new ArrayList<>();

        mQuestionsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Questions");
        mPositionsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Positions");

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
                String qId = Integer.toString(mCount);
                mQuestionsDatabaseReference.child(qId).setValue(
                        new Question(mCount, mQuestion, mAnswer, mLevel, mPosition));
                mLanguagesDatabaseReference = mQuestionsDatabaseReference.child(qId)
                        .child("languages");
                for (Language language : mLanguages) {
                    String lId = Integer.toString(language.getId());
                    mLanguagesDatabaseReference.child(lId).setValue(
                            new Language(language.getId(), language.getTitle()));
                }
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

    CompoundButton.OnCheckedChangeListener checkedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                mLanguagesCount++;
                Language l = new Language(mLanguagesCount, buttonView.getText().toString());
                mLanguages.add(l);
            }
        }
    };
}
