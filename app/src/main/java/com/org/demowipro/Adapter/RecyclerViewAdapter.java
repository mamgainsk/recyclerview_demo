package com.org.demowipro.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.org.demowipro.R;
import com.org.demowipro.RequestPOJO.RowDescription;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<RowDescription> itemList;
    private Context context;

    public RecyclerViewAdapter(List<RowDescription> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
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

        if (itemList.get(position).getImageHref() != null) {
            Picasso.get()
                    .load(itemList.get(position).getImageHref().toString())
                    .into(holder.rowImage);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
