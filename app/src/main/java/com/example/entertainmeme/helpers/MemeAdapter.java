package com.example.entertainmeme.helpers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.entertainmeme.R;
import com.example.entertainmeme.activities.MainActivity;
import com.example.entertainmeme.activities.MemeActivity;
import com.example.entertainmeme.models.Meme;

import java.util.List;

public class MemeAdapter extends RecyclerView.Adapter<MemeAdapter.MyViewHolder> {

    Context context;
    private List<Meme> memes;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView titleTextView;
        ImageView memeImageView;

        public MyViewHolder(View view) {
            super(view);
            this.view = view;
            this.titleTextView = (TextView)view.findViewById(R.id.titleTextView);
            this.memeImageView = (ImageView) view.findViewById(R.id.memeImageView);
        }
    }

    public MemeAdapter(Context context, List<Meme> memes) {
        this.context = context;
        this.memes = memes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.meme_layout,
                parent,
                false
        );

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titleTextView.setText(memes.get(position).getTitle());

        Glide.with(context)
                .load(memes.get(position).getUrl())
                .into(holder.memeImageView);


    }

    @Override
    public int getItemCount() {
        return memes.size();
    }
}
