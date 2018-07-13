package com.org.demowipro.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.org.demowipro.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    AppCompatTextView rowTitle;
    AppCompatTextView rowDescription;
    AppCompatImageView rowImage;

    RecyclerViewHolder(View itemView) {
        super(itemView);

        rowTitle = itemView.findViewById(R.id.row_title);
        rowDescription = itemView.findViewById(R.id.row_description);
        rowImage = itemView.findViewById(R.id.row_image);
    }

}
