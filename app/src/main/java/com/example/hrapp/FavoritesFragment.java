package com.example.hrapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import java.util.List;

public class FavoritesFragment extends Fragment {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mFavoritesDatabaseReference;
    private DatabaseReference mQuestionsDatabaseReference;
    private FirebaseUser mUser;

    private RecyclerView mRecyclerView;
    private FavoriteAdapter mFavoriteAdapter;

    private TextView mUserTextLogged;

    private List<Question> mQuestionList;
    private List<Favorite> mFavoriteList;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        mFirebaseAuth = FirebaseAuth.getInstance();

        if (mFirebaseAuth.getCurrentUser() == null) {
            getActivity().finish();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }

        mUserTextLogged = (TextView) view.findViewById(R.id.user_logged_email);
        mUser = mFirebaseAuth.getCurrentUser();
        mUserTextLogged.setText(mUser.getEmail() + "'s favorites:");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.favorites_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFavoritesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Favorites");
        mQuestionsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Questions");
        mQuestionList = new ArrayList<Question>();
        mFavoriteList = new ArrayList<Favorite>();

        mFavoritesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Favorite value = dataSnapshot1.getValue(Favorite.class);
                    if (mUser.getEmail().equals(value.getUserEmail())) {
                        mFavoriteList.add(value);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Hello", "Failed to read value.", databaseError.toException());
            }
        });

        mQuestionsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Question value = dataSnapshot1.getValue(Question.class);
                    for (Favorite f : mFavoriteList) {
                        if (value.getId() == f.getQuestionId()) {
                            mQuestionList.add(value);
                        }
                    }
                }
                mFavoriteAdapter = new FavoriteAdapter(mQuestionList);
                mRecyclerView.setAdapter(mFavoriteAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Hello", "Failed to read value.", databaseError.toException());
            }
        });

        return view;
    }




}
