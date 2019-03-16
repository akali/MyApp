package com.example.hrapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hrapp.models.Favorite;
import com.example.hrapp.models.Question;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionListActivity extends AppCompatActivity {

    private DatabaseReference mQuestionsDatabaseReference;
    private DatabaseReference mFavoritesDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private List<Question> mQuestionList;
    private List<Favorite> mFavoriteList;
    private RecyclerView mRecyclerView;
    private QuestionAdapter mQuestionAdapter;
    private FloatingActionButton mAddQuestionButton;
    private Button mFilterJunior;
    private Button mFilterMiddle;
    private Button mFilterSenior;
    private ImageView mFilterImage;

    private static final Map<Integer, String> buttonTextColorMap = new HashMap<Integer, String>(){{
        put(R.id.filter_junior, "#FF515E");
        put(R.id.filter_middle, "#FF8E09");
        put(R.id.filter_senior, "#00D053");
    }};

    private static final Map<Integer, Integer> buttonBackgrounMap = new HashMap<Integer, Integer>(){{
        put(R.id.filter_junior, R.drawable.button_red_rounded);
        put(R.id.filter_middle, R.drawable.button_yellow_rounded);
        put(R.id.filter_senior, R.drawable.button_green_rounded);
    }};

    private String mPositionName;

    private List<Question> mLevelFilteredQuestionList;
    private List<Question> mLevelNotFilteredQuestionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        mFirebaseAuth = FirebaseAuth.getInstance();

        if (mFirebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        mUser = mFirebaseAuth.getCurrentUser();

        mPositionName = getIntent().getStringExtra("positionName");

        mAddQuestionButton = (FloatingActionButton) findViewById(R.id.add_question);
        mRecyclerView = (RecyclerView) findViewById(R.id.questions_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance();
        mQuestionsDatabaseReference = mDatabase.getReference().child("Questions");
        mFavoritesDatabaseReference = mDatabase.getReference().child("Favorites");
        mLevelFilteredQuestionList = new ArrayList<Question>();
        mLevelNotFilteredQuestionList = new ArrayList<Question>();
        mQuestionList = new ArrayList<Question>();
        mFavoriteList = new ArrayList<Favorite>();

        mAddQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddQuestionActivity.class);
                startActivity(intent);
                mQuestionList.clear();
            }
        });

        mFilterJunior = findViewById(R.id.filter_junior);
        mFilterMiddle = findViewById(R.id.filter_middle);
        mFilterSenior = findViewById(R.id.filter_senior);
        mFilterImage = findViewById(R.id.filter_image);

        mFilterImage.setOnClickListener(levelsFilterListener);
        mFilterJunior.setOnClickListener(levelsFilterListener);
        mFilterMiddle.setOnClickListener(levelsFilterListener);
        mFilterSenior.setOnClickListener(levelsFilterListener);

        initFavoriteList();
        initQuestionList();
    }

    void setEnabled(Button b, boolean enabled) {
        if (enabled) {
            b.setTextColor(Color.parseColor(buttonTextColorMap.get(b.getId())));
            b.setBackgroundResource(buttonBackgrounMap.get(b.getId()));
        } else {
            b.setBackgroundResource(R.drawable.button_default_rounded);
            b.setTextColor(Color.parseColor("#000000"));
        }
    }

    View.OnClickListener levelsFilterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button junButton = findViewById(R.id.filter_junior);
            Button midButton = findViewById(R.id.filter_middle);
            Button senButton = findViewById(R.id.filter_senior);
            String levelName = "";

            switch (v.getId()) {
                case R.id.filter_junior:
                    Button button = (Button) v;
                    levelName = button.getText().toString();

                    setEnabled(button, true);
                    setEnabled(midButton, false);
                    setEnabled(senButton, false);

                    filterQuestionsByLevel(levelName);
                    break;
                case R.id.filter_middle:
                    levelName = ((Button)v).getText().toString();

                    setEnabled((Button) v, true);
                    setEnabled(junButton, false);
                    setEnabled(senButton, false);

                    filterQuestionsByLevel(levelName);
                    break;
                case R.id.filter_senior:
                    levelName = ((Button)v).getText().toString();

                    setEnabled((Button) v, true);
                    setEnabled(junButton, false);
                    setEnabled(midButton, false);

                    filterQuestionsByLevel(levelName);
                    break;
                case R.id.filter_image:
                    setEnabled(junButton, false);
                    setEnabled(midButton, false);
                    setEnabled(senButton, false);

                    resetFilter();
                    break;
            }
        }
    };

    private void resetFilter() {
        mLevelFilteredQuestionList.clear();
        mQuestionList.clear();
        mQuestionList.addAll(mLevelNotFilteredQuestionList);
        mQuestionAdapter.notifyDataSetChanged();
    }

    private void filterQuestionsByLevel(String level) {
        mLevelFilteredQuestionList.clear();

        for (Question question : mLevelNotFilteredQuestionList) {
            if (question.getLevel().equals(level)) {
                mLevelFilteredQuestionList.add(question);
            }
        }
        mQuestionList.clear();
        mQuestionList.addAll(mLevelFilteredQuestionList);
        mQuestionAdapter.notifyDataSetChanged();
    }

    private void initQuestionList() {
        mQuestionsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Question value = dataSnapshot1.getValue(Question.class);
                    if (value.getPosition().equals(mPositionName)) {
                        mQuestionList.add(value);
                        mLevelNotFilteredQuestionList.add(value);
                    }
                }
                mQuestionAdapter = new QuestionAdapter(mQuestionList, listener);
                mQuestionAdapter.setFavoriteList(mFavoriteList);
                mRecyclerView.setAdapter(mQuestionAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Hello", "Failed to read value.", databaseError.toException());
            }
        });
    }

    private void initFavoriteList() {
        mFavoritesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Favorite favorite = dataSnapshot1.getValue(Favorite.class);
                    if (mUser.getEmail().equals(favorite.getUserEmail())) {
                        mFavoriteList.add(favorite);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Hello", "Failed to read value.", databaseError.toException());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mQuestionAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mQuestionList.clear();
        mFavoriteList.clear();
        initFavoriteList();
        initQuestionList();
    }

    RecyclerViewClickListener listener = new RecyclerViewClickListener() {
        @Override
        public void onClick(View view, int position) {
            switch (view.getId()) {
                case R.id.question_question:
                    Intent intent = new Intent(view.getContext(), QuestionDetailsActivity.class);
                    Question question = mQuestionList.get(position);
                    intent.putExtra("questionDetails", question);
                    startActivity(intent);
                    break;
                case R.id.favorite_question:
                    if (view.isSelected()) {
                        initFavoriteList();
                        removeFromFavorites(position);
                    } else {
                        addToFavorites(position);
                    }
                    break;
            }

        }
    };

    public void removeFromFavorites(int position) {
        int questionId = mQuestionList.get(position).getId();
        int index = 0;
        for (Favorite favorite : mFavoriteList) {
            if (favorite.getQuestionId() == questionId &&
                    favorite.getUserEmail().equals(mUser.getEmail())) {
                index = mFavoriteList.indexOf(favorite);
                String childFavoriteId = Integer.toString(favorite.getId());
                mFavoritesDatabaseReference.child(childFavoriteId).removeValue();
            }
        }
        mFavoriteList.remove(index);
        mQuestionAdapter.notifyItemRemoved(index);
    }

    public void addToFavorites(int position) {
        int questionId = mQuestionList.get(position).getId();
        int maxi = 0;
        for (Favorite favorite : mFavoriteList) {
            if (favorite.getId() > maxi) {
                maxi = favorite.getId();
            }
        }
        int favoriteId = maxi + 1;
        String childFavoriteId = Integer.toString(favoriteId);
        mFavoritesDatabaseReference.child(childFavoriteId).setValue(
                new Favorite(favoriteId, questionId, mUser.getEmail()));
        mFavoriteList.add(new Favorite(favoriteId, questionId, mUser.getEmail()));
        mQuestionAdapter.notifyDataSetChanged();
    }
}
