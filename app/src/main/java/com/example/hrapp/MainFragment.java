package com.example.hrapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.hrapp.models.Position;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private RecyclerView mRecyclerView;
    private List<Position> mPositionList;
    private PositionAdapter mPositionAdapter;

    private ViewFlipper mViewFlipper;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        int images[] = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};
        mViewFlipper = view.findViewById(R.id.image_flipper);

        for (int image : images) {
            flipperImages(image);
        }

        mRecyclerView = view.findViewById(R.id.positions_recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("Positions");
        mPositionList = new ArrayList<>();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Position value = dataSnapshot1.getValue(Position.class);
                    mPositionList.add(value);
                }
                mPositionAdapter = new PositionAdapter(getActivity(), mPositionList);
                mRecyclerView.setAdapter(mPositionAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Hello", "Failed to read value.", databaseError.toException());
            }
        });

        return view;
    }

    private void flipperImages(int image) {
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(image);

        mViewFlipper.addView(imageView);
        mViewFlipper.setFlipInterval(3000);
        mViewFlipper.setAutoStart(true);

        mViewFlipper.setInAnimation(getActivity(), android.R.anim.slide_in_left);
        mViewFlipper.setOutAnimation(getActivity(), android.R.anim.slide_out_right);
    }

}
