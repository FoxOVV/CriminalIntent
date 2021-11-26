package com.example.criminalintent;

import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;

public class ListRow extends RecyclerView.ViewHolder {
    public ImageView mThumbnail;
    public ListRow(View view) {
        super(view);
        //mThumbnail = (ImageView) view.findViewById(R.id.thumbnail);
    }
}