package com.ricknharith.entertainmeme.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ricknharith.entertainmeme.R;
import com.ricknharith.entertainmeme.models.Meme;

import java.util.List;

public class MemeAdapter extends RecyclerView.Adapter<MemeAdapter.MyViewHolder> {

    private List<Meme> memes;
    private OnClickListener onClickListener;

    // Create a viewholder class that stores the components of the view
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View view;
        TextView titleTextView;
        ImageView memeImageView;
        Context context;
        OnClickListener onClickListener;

        // Constructor
        public MyViewHolder(View view, OnClickListener onClickListener, Context context) {
            super(view);
            // Instantiate components
            this.view = view;
            this.titleTextView = (TextView)view.findViewById(R.id.titleTextView);
            this.memeImageView = (ImageView) view.findViewById(R.id.memeImageView);
            this.context = context;
            this.onClickListener = onClickListener;

            // Set OnClickListener
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // If the card was clicked
            // Call onCardClickListener
            onClickListener.onCardClickListener(getAdapterPosition());
        }
    }

    // Constructor
    public MemeAdapter(List<Meme> memes, OnClickListener onClickListener) {
        this.memes = memes;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Link components
        View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.meme_swipe_card,
                    parent,
                    false
            );

        MyViewHolder myViewHolder = new MyViewHolder(view, onClickListener, parent.getContext());

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // Set components properties

        // Set titleTextView text
        holder.titleTextView.setText(memes.get(position).getTitle());

        // Set image
        Glide.with(holder.context)
            .load(memes.get(position).getUrl())
            .into(holder.memeImageView);
    }

    @Override
    public int getItemCount() {
        return memes.size();
    }

    // Interface OnClickListener
    public interface OnClickListener{
        void onCardClickListener(int position);
    }
}
