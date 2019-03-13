package com.example.hrapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import com.example.hrapp.models.Favorite;
import com.example.hrapp.models.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionListActivity extends AppCompatActivity {

    private DatabaseReference mQuestionsDatabaseReference;
    private DatabaseReference mFavoritesDatabaseReference;
    private FirebaseDatabase mDatabase;
    private List<Question> mQuestionList;
    private List<Favorite> mFavoriteList;
    private RecyclerView mRecyclerView;
    private QuestionAdapter mQuestionAdapter;
    private FloatingActionButton mAddQuestionButton;

    private String mPositionName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        mPositionName = getIntent().getStringExtra("positionName");

        mAddQuestionButton = (FloatingActionButton) findViewById(R.id.add_question);
        mRecyclerView = (RecyclerView) findViewById(R.id.questions_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance();
        mQuestionsDatabaseReference = mDatabase.getReference().child("Questions");
        mFavoritesDatabaseReference = mDatabase.getReference().child("Favorites");
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

        initFavoriteList();
        initQuestionList();

    }


    private void initQuestionList() {
        mQuestionsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Question value = dataSnapshot1.getValue(Question.class);
                    if (value.getPosition().equals(mPositionName)) {
                        mQuestionList.add(value);
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
                    mFavoriteList.add(favorite);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
            Intent intent = new Intent(view.getContext(), QuestionDetailsActivity.class);
            Question question = mQuestionList.get(position);
            intent.putExtra("questionDetails", question);
            startActivity(intent);
        }
    };
}
