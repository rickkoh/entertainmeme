package com.example.entertainmeme.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.entertainmeme.R;
import com.example.entertainmeme.models.Meme;

import java.util.List;

public class MemeAdapter extends RecyclerView.Adapter<MemeAdapter.MyViewHolder> {

    private List<Meme> memes;
    private OnClickListener onClickListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View view;
        TextView titleTextView;
        ImageView memeImageView;
        Context context;
        OnClickListener onClickListener;

        public MyViewHolder(View view, OnClickListener onClickListener, Context context) {
            super(view);
            this.view = view;
            this.titleTextView = (TextView)view.findViewById(R.id.titleTextView);
            this.memeImageView = (ImageView) view.findViewById(R.id.memeImageView);
            this.context = context;
            this.onClickListener = onClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onItemClickListener(getAdapterPosition());
        }
    }

    public MemeAdapter(List<Meme> memes, OnClickListener onClickListener) {
        this.memes = memes;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

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
        holder.titleTextView.setText(memes.get(position).getTitle());

        Glide.with(holder.context)
            .load(memes.get(position).getUrl())
            .into(holder.memeImageView);
    }

    @Override
    public int getItemCount() {
        return memes.size();
    }

    public interface OnClickListener{
        void onItemClickListener(int position);
    }
}
