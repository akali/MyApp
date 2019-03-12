package com.example.hrapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hrapp.models.Position;

import java.util.List;

public class PositionAdapter extends RecyclerView.Adapter<PositionAdapter.PositionViewHolder> {

    private List<Position> mPositionList;

    private Context mContext;

    public PositionAdapter(Context context, List<Position> positions) {
        mPositionList = positions;
        mContext = context;
    }

    @NonNull
    @Override
    public PositionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.position_list_item, viewGroup, false);
        return new PositionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PositionViewHolder positionViewHolder, int i) {
        Position position = mPositionList.get(i);
        positionViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mPositionList.size();
    }

    class PositionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mPositionTextName;
        private Position mPosition;

        public PositionViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            mPositionTextName = itemView.findViewById(R.id.posiotion_name);
        }

        public void bind(Position position) {
            mPosition = position;
            mPositionTextName.setText(mPosition.getName());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, QuestionListActivity.class);
            intent.putExtra("positionName", mPosition.getName());
            mContext.startActivity(intent);
        }
    }
}
