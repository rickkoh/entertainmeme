package com.ricknharith.entertainmeme.models;

import android.content.Context;

public class Meme {
    private Context context;

    // Attributes
    private int id;
    private String postLink;
    private String subreddit;
    private String title;
    private String url;
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

    // Constructor
    public Meme(String pl, String sr, String t, String u) {
        postLink = pl;
        subreddit = sr;
        title = t;
        url = u;
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

}
