package com.example.entertainmeme;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

        View itemview = convertView;

        itemview = LayoutInflater.from(context).inflate(
                        R.layout.meme_layout,
                        parent,
                        false
                );

        TextView titleTextView = (TextView)itemview.findViewById(R.id.titleTextView);

        titleTextView.setText(memeList.get(position).getTitle());

        return itemview;
    }
}
