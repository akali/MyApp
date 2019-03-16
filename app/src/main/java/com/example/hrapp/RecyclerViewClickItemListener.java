package com.example.hrapp;

import android.view.View;

public interface RecyclerViewClickItemListener <T> {
  void onClick(View v, T item, int position);
}
