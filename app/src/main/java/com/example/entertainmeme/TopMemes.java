package com.example.entertainmeme;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

public class TopMemes {
    private Context context;

    private int id;
    private String name;
    private String url;
    private Bitmap image;

    public TopMemes(Context c,String n,String u){
        context=c;
        name=n;
        url=u;
        loadImage();
    }

    public TopMemes(int i, String n, String u){
        id=i;
        name=n;
        url=u;

    }

    public String getName(){
        return name;
    }

    public String getUrl(){
        return url;
    }

    public  Bitmap getImage(){
        return image;
    }

    public void loadImage(){
        if(url!= null){
            try{
                Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        image=resource;
                    }
                });
            }catch (Exception e){
        }
    }

}}

