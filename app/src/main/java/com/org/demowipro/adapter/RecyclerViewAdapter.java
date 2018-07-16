package com.org.demowipro.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.org.demowipro.R;
import com.org.demowipro.request_pojo.RowDescription;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<RowDescription> itemList;

    public RecyclerViewAdapter(List<RowDescription> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list_item, null);
        RecyclerViewHolder recyclerViewHolders;
        recyclerViewHolders = new RecyclerViewHolder(layoutView);
        return recyclerViewHolders;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        holder.rowTitle.setText(itemList.get(position).getTitle());
        holder.rowDescription.setText(itemList.get(position).getDescription());

        Picasso.get()
                .load(itemList.get(position).getImageHref())
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.placeholder)
                .into(holder.rowImage);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
