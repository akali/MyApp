package com.example.hrapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hrapp.adapter.CandidateAdapter;
import com.example.hrapp.models.Candidate;
import com.example.hrapp.models.Language;
import com.example.hrapp.models.Position;

import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class CandidateFragment extends Fragment {

    private static final int CREATE_CANDIDATE_REQUEST_CODE = 101;

    public CandidateFragment() {
        // Required empty public constructor
    }

    public static CandidateFragment newInstance() {
        CandidateFragment fragment = new CandidateFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_candidate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = view.findViewById(R.id.add_candidate_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClick();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.candidates_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Candidate> candidates = Arrays.asList(new Candidate
          (1,
            "Pavel Durov",
            Arrays.asList(
              new Language(1, "C#"),
              new Language(2, "Java")
            ),
            new Position(1, "Database Specialist"),
            "Junior",
            "25 y.o. designer from Australia"
          )
        );

        recyclerView.setAdapter(new CandidateAdapter(candidates));
    }

    private void onAddClick() {
        Intent intent = new Intent(getContext(), CreateCandidateActivity.class);
        startActivityForResult(intent, CREATE_CANDIDATE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_CANDIDATE_REQUEST_CODE && resultCode == RESULT_OK) {

        }
    }
}
