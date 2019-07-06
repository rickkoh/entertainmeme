package com.example.entertainmeme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Top100Adapter extends BaseAdapter
{
    Context context;
    List<TopMemes> TopMemeList;

    public Top100Adapter(Context context,List<TopMemes> TopMemes){
        this.context= context;
        TopMemeList= TopMemes;
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
                R.layout.top_layout,
                parent,
                false
        );

        TextView memeTitle= (TextView)itemview.findViewById(R.id.memeTitle);
        ImageView TopMemeView =(ImageView)itemview.findViewById(R.id.TopMemeView);

        Glide.with(context).load(TopMemeList.get(position).getUrl())
                .into(TopMemeView);
        return itemview;
    }
}
