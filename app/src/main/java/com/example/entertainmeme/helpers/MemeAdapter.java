package com.example.entertainmeme.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.entertainmeme.R;
import com.example.entertainmeme.models.Meme;

import java.util.List;

public class MemeAdapter extends BaseAdapter {

    Context context;
    List<Meme> memeList;

    public MemeAdapter(Context context, List<Meme> mL) {
        this.context = context;
        memeList = mL;
    }

    @Override
    public int getCount() {
        return memeList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(
                        R.layout.meme_layout,
                        parent,
                        false
                );

        TextView titleTextView = (TextView)convertView.findViewById(R.id.titleTextView);

        ImageView memeImageView = (ImageView)convertView.findViewById(R.id.memeImageView);

        titleTextView.setText(memeList.get(position).getTitle());

        Glide.with(context)
                .load(memeList.get(position).getUrl())
                .into(memeImageView);

        return convertView;
    }
}
