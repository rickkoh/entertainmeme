package com.example.entertainmeme.model;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

public class Meme {
    private Context context;

    // Attributes
    private int id;
    private String postLink;
    private String subreddit;
    private String title;
    private String url;
    private Bitmap image;
    private String datetimeadded;

    // Database table and column names
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS 'meme' ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `postLink` TEXT NOT NULL UNIQUE, `subreddit` TEXT NOT NULL, `title` TEXT NOT NULL, `url` TEXT NOT NULL, `datetimeadded` TEXT NOT NULL )";
    public static final String TABLE_NAME = "meme";
    public static final String C_ID = "id";
    public static final String C_POSTLINK = "postLink";
    public static final String C_SUBREDDIT = "subreddit";
    public static final String C_TITLE = "title";
    public static final String C_URL = "url";
    public static final String C_DATETIMEADDED = "datetimeadded";

    // Constructor
    public Meme(Context c, String pl, String sr, String t, String u) {
        context = c;
        postLink = pl;
        subreddit = sr;
        title = t;
        url = u;
        loadImage();
    }

    // Constructor
    public Meme(int i, String pl, String sr, String t, String u, String d) {
        id = i;
        postLink = pl;
        subreddit = sr;
        title = t;
        url = u;
        datetimeadded = d;
    }

    // Get PostLink
    public String getPostLink() {
        return postLink;
    }

    // Get Subreddit
    public String getSubreddit() {
        return subreddit;
    }

    // Get Title
    public String getTitle() {
        return title;
    }

    // Get URL
    public String getUrl() {
        return url;
    }

    // Get Image
    public Bitmap getImage() {
        return image;
    }

    // Load image
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
