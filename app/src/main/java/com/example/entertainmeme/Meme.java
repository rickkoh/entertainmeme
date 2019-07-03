package com.example.entertainmeme;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

public class Meme {
    private Context context;

    private String postLink;
    private String subreddit;
    private String title;
    private String url;
    private Bitmap image;


    public Meme(Context c, String pl, String sr, String t, String u) {
        context = c;
        postLink = pl;
        subreddit = sr;
        title = t;
        url = u;
        loadImage();
    }

    public String getPostLink() {
        return postLink;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public Bitmap getImage() {
        return image;
    }

    public void loadImage() {
        if (url != null) {
            try {
                Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            image = resource;
                        }
                    });
            } catch (Exception e) {

            }
        }
    }


}
