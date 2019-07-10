package com.example.entertainmeme.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.entertainmeme.R;
import com.example.entertainmeme.models.Meme;

import java.util.List;

public class SwipeStackAdapter extends BaseAdapter {

    private List<Meme> memes;
    private Context context;

    public SwipeStackAdapter(List<Meme> memes, Context context) {
        this.memes = memes;
        this.context = context;
    }

    public void updateMemes(List<Meme> memes) {
        this.memes = memes;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (memes == null) return 0;
        return memes.size();
    }

    @Override
    public Meme getItem(int position) {
        if (memes == null || position >= memes.size()) return null;
        return memes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.meme_card, parent, false);
        ImageView memeImageView = (ImageView) convertView.findViewById(R.id.memeImageView);

        try {
            Glide.with(context)
                    .asBitmap()
                    .load(memes.get(position).getUrl())
                    .into(memeImageView);
        } catch (Exception e) {
        }

        return convertView;
    }
}
