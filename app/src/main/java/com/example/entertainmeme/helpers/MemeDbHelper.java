package com.example.entertainmeme.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.entertainmeme.models.Meme;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MemeDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "MemeDbHelper";
    private static final String dbName = "MemeDb";
    private static final int version = 1;

    public MemeDbHelper(Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Meme.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Meme.TABLE_NAME);
        onCreate(db);
    }

    // Saves the meme
    public void insertMeme(Meme meme) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Meme.C_POSTLINK, meme.getPostLink());
        values.put(Meme.C_SUBREDDIT, meme.getSubreddit());
        values.put(Meme.C_TITLE, meme.getTitle());
        values.put(Meme.C_URL, meme.getUrl());
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        values.put(Meme.C_DATETIMEADDED, dateFormat.format(Calendar.getInstance().getTime()));

        try {
            db.insert(Meme.TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.d(null, e.toString());
            values = new ContentValues();
            values.put(Meme.C_DATETIMEADDED, Calendar.getInstance().getTime().toString());
        }

        try {
            db.update(Meme.TABLE_NAME, values, Meme.C_POSTLINK + "=?", new String[]{meme.getPostLink()});
        } catch (Exception ee) {

        }

        db.close();
    }

    public List<Meme> getAllMemes() {
        List<Meme> memes = new ArrayList<Meme>();

        String selectQuery = "SELECT * FROM " + Meme.TABLE_NAME + " ORDER BY " + Meme.C_DATETIMEADDED + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Meme meme = new Meme(
                        cursor.getInt(cursor.getColumnIndex(Meme.C_ID)),
                        cursor.getString(cursor.getColumnIndex(Meme.C_POSTLINK)),
                        cursor.getString(cursor.getColumnIndex(Meme.C_SUBREDDIT)),
                        cursor.getString(cursor.getColumnIndex(Meme.C_TITLE)),
                        cursor.getString(cursor.getColumnIndex(Meme.C_URL)),
                        cursor.getString(cursor.getColumnIndex(Meme.C_DATETIMEADDED))
                );

                memes.add(meme);
            } while (cursor.moveToNext());
        }

        db.close();

        return memes;

    }
}
