package com.example.hrapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hrapp.models.Candidate;
import com.example.hrapp.models.Language;
import com.example.hrapp.models.Position;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CreateCandidateActivity extends AppCompatActivity {

  private CheckBox mJavascript;
  private CheckBox mCsharp;
  private CheckBox mJava;
  private CheckBox mSql;
  private CheckBox mPython;
  private List<Language> mLanguages;
  private int mLanguagesCount;

  private Spinner mPositionSpinner;
  private Spinner mLevelSpinner;
  private EditText displayNameEditText;
  private EditText bioEditText;
  private Button mSaveButton;

  private int mCount;
  private String mLevel;
  private Position mPosition;
  private String mQuestion;
  private String mAnswer;
  private ArrayList<String> mPositions;
  private ArrayAdapter<CharSequence> mLevelsAdapter;
  private ArrayAdapter<String> mPositionsAdapter;


  private DatabaseReference mQuestionsDatabaseReference;
  private DatabaseReference mPositionsDatabaseReference;
  private DatabaseReference mLanguagesDatabaseReference;
  private List<Position> fullPositions;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_candidate);

    mJavascript = findViewById(R.id.check_javascript);
    mCsharp = findViewById(R.id.check_csharp);
    mJava = findViewById(R.id.check_java);
    mSql = findViewById(R.id.check_sql);
    mPython = findViewById(R.id.check_python);
    mLanguages = new ArrayList<>();

    mPositionSpinner = findViewById(R.id.position_spinner);
    mLevelSpinner = findViewById(R.id.level_spinner);
    mSaveButton = findViewById(R.id.save_question);
    displayNameEditText = findViewById(R.id.display_name_edit_text);
    bioEditText = findViewById(R.id.bio_edit_text);
    mPositions = new ArrayList<>();
    fullPositions = new ArrayList<>();

    mPositionsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Positions");

    mPositionsDatabaseReference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
          Position value = dataSnapshot1.getValue(Position.class);
          fullPositions.add(value);
          mPositions.add(value.getName());
        }

        mPositionsAdapter = new ArrayAdapter<>(CreateCandidateActivity.this,
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

    mLevelsAdapter = ArrayAdapter.createFromResource(this, R.array.levels,
      android.R.layout.simple_spinner_item);
    mLevelsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mLevelSpinner.setAdapter(mLevelsAdapter);
    mLevelSpinner.setOnItemSelectedListener(selectListener);

    mJavascript.setOnCheckedChangeListener(checkedListener);
    mCsharp.setOnCheckedChangeListener(checkedListener);
    mJava.setOnCheckedChangeListener(checkedListener);
    mSql.setOnCheckedChangeListener(checkedListener);
    mPython.setOnCheckedChangeListener(checkedListener);

    mSaveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onSave();
      }
    });
  }

  private void onSave() {
    String displayName = displayNameEditText.getText().toString();
    String bio = bioEditText.getText().toString();

    if (TextUtils.isEmpty(displayName) || TextUtils.isEmpty(bio)) {
      Toast.makeText(this, "Please fill name and bio", Toast.LENGTH_SHORT).show();
      return;
    }

    Candidate candidate = new Candidate(
      0,
      displayName,
      mLanguages,
      mPosition,
      mLevel,
      bio
    );

    // TODO: to add into firebase

  }

  AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      int spinnerId = parent.getId();
      switch (spinnerId) {
        case R.id.level_spinner:
          mLevel = parent.getItemAtPosition(position).toString();
          break;
        case R.id.position_spinner:
          mPosition = fullPositions.get(position);
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
      Language l = new Language(mLanguages.size(), buttonView.getText().toString());
      if (isChecked) {
        mLanguages.add(l);
      } else {
        List<Language> nLanguages = new ArrayList<>();

        for (Language mLanguage : mLanguages) {
          if (!mLanguage.getTitle().equalsIgnoreCase(l.getTitle()))
            nLanguages.add(new Language(nLanguages.size(), mLanguage.getTitle()));
        }

        mLanguages = nLanguages;
      }
    }
  };
}
