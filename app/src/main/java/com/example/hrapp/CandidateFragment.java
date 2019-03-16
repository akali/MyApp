package com.example.hrapp;


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

import com.example.hrapp.models.Candidate;
import com.example.hrapp.models.CandidateAdapter;
import com.example.hrapp.models.Position;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CandidateFragment extends Fragment {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private RecyclerView mRecyclerView;
    private List<Candidate> mCandidateList;
    private CandidateAdapter mAdapter;


    public CandidateFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_candidate, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        mRecyclerView = view.findViewById(R.id.candidates_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("Candidates");
        mCandidateList = new ArrayList<Candidate>();

        initCandidateList();

        return view;
    }

    private void initCandidateList() {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Candidate value = dataSnapshot1.getValue(Candidate.class);
                    mCandidateList.add(value);
                }
                mAdapter = new CandidateAdapter(mCandidateList, getContext());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Hello", "Failed to read value.", databaseError.toException());
            }
        });
    }
}
