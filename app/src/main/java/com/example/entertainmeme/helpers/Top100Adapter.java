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
import com.example.entertainmeme.models.TopMemes;

import java.util.List;

public class Top100Adapter extends BaseAdapter
{
    Context context;
    List<TopMemes> TopMemeList;

    public Top100Adapter(Context context,List<TopMemes> TopMeme){
        this.context= context;
        TopMemeList= TopMeme;
    }

    @Override
    public int getCount(){
        return TopMemeList.size();
    }

    @Override
    public Object getItem(int position){
        return null;
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View itemview=convertView;

        itemview= LayoutInflater.from(context).inflate(
                R.layout.meme_layout,
                parent,
                false
        );

        TextView titleTextView= (TextView)itemview.findViewById(R.id.titleTextView);
        ImageView memeImageView =(ImageView)itemview.findViewById(R.id.memeImageView);

        titleTextView.setText(TopMemeList.get(position).getName());

        Glide.with(context).load(TopMemeList.get(position).getUrl())
                .into(memeImageView);
        return itemview;
    }
}
