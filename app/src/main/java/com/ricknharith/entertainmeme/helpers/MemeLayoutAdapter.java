package com.ricknharith.entertainmeme.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ricknharith.entertainmeme.R;
import com.ricknharith.entertainmeme.models.Meme;

import java.util.List;

public class MemeLayoutAdapter extends RecyclerView.Adapter<MemeLayoutAdapter.MyViewHolder> {

    private Context context;
    private List<Meme> memes;
    private OnClickListener onClickListener;

    // Create a viewholder class that stores the components of the view
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View view;
        TextView titleTextView;
        ImageView memeImageView;
        ImageButton elipsisBtn;

        OnClickListener onClickListener;

        // Constructor
        public MyViewHolder(View view, OnClickListener onClickListener) {
            super(view);
            // Instantiate components
            this.view = view;
            this.titleTextView = (TextView)view.findViewById(R.id.titleTextView);
            this.memeImageView = (ImageView)view.findViewById(R.id.memeImageView);
            this.elipsisBtn =(ImageButton)view.findViewById(R.id.elipsisBtn);

            // Set OnClickListener
            this.onClickListener = onClickListener;

            itemView.setOnClickListener(this);
            elipsisBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Detect which button was clicked
            if (v == elipsisBtn) {
                // If the elipsisbtn was clicked
                // Call onElipsisButtonClickListener
                onClickListener.onElipsisButtonClickListener(elipsisBtn, getAdapterPosition());
            }
            else {
                // If the card was clicked
                // Call onCardClickListener
                onClickListener.onCardClickListener(getAdapterPosition());
            }
        }
    }

    // Constructor
    public MemeLayoutAdapter(List<Meme> memes, Context context, OnClickListener onClickListener) {
        this.context = context;
        this.memes = memes;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Link components
        View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.meme_row_card,
                    parent,
                    false
            );

        MyViewHolder myViewHolder = new MyViewHolder(view, onClickListener);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // Set components properties

        // Set titleTextView text
        holder.titleTextView.setText(memes.get(position).getTitle());

        // Set image
        Glide.with(context)
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
        void onElipsisButtonClickListener(View view, int position);
    }
}
