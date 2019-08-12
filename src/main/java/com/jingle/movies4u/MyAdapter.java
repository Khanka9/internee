package com.jingle.movies4u;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem>listItems;
    private Context context;

    public MyAdapter(Context context, List<ListItem>listItems) {
        this.listItems = listItems;
        this.context = context;                                                                     //List<ListItem> listItems, Context context
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);

        holder.textViewHead.setText(listItem.getTitle());
        holder.textViewDesc.setText(listItem.getDesc());
        holder.textViewRating.setText(String.valueOf(listItem.getRating()));
        holder.textViewreleaseYear.setText(String.valueOf(listItem.getReleaseYear()));

        Glide.with(this.context)
                .load(listItem.getImageUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;
        public TextView textViewRating;
        public TextView textViewreleaseYear;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewHead =  itemView.findViewById(R.id.textViewHead);
            textViewDesc =  itemView.findViewById(R.id.desc);
            textViewRating = itemView.findViewById(R.id.rating);
            textViewreleaseYear =  itemView.findViewById(R.id.releaseYear);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
