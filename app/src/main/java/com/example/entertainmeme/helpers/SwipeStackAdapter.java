package com.example.entertainmeme.helpers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.entertainmeme.R;
import com.example.entertainmeme.models.Meme;

import java.util.ArrayList;
import java.util.List;

public class SwipeStackAdapter extends BaseAdapter {

    private int start = 0;
    private int pointer = 0;
    private Boolean forward = false;
    private List<Meme> memes;
    private Context context;

    public SwipeStackAdapter(List<Meme> memes, Context context) {
        this.memes = memes;
        this.context = context;
    }

    public List<Meme> updateMemes() {
        List<Meme> stackMemes = new ArrayList<Meme>();
        if (memes != null && !memes.isEmpty()) {
            if (forward) stackMemes = memes.subList(start, memes.size());
            else stackMemes = memes.subList(pointer, memes.size());
        }
        return stackMemes;
    }

    public void back() {
        if (pointer <= 0) return;
        pointer-=1;
        start = pointer;
        forward = false;
    }

    public void next() {
        pointer+=1;
        forward = true;
    }

    @Override
    public int getCount() {
        if (updateMemes() == null) return 0;
        return updateMemes().size();
    }

    @Override
    public Meme getItem(int position) {
        if (updateMemes() == null || position >= memes.size()) return null;
        return updateMemes().get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.d(null, "id getting");
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.meme_card, parent, false);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
        ImageView memeImageView = (ImageView) convertView.findViewById(R.id.memeImageView);

        titleTextView.setText(updateMemes().get(position).getTitle());

        try {
            Glide.with(context)
                    .asBitmap()
                    .load(updateMemes().get(position).getUrl())
                    .into(memeImageView);
        } catch (Exception e) {
        }

        return convertView;
    }
}
