package com.ricknharith.entertainmeme.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ricknharith.entertainmeme.models.Meme;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.ricknharith.entertainmeme.models.Meme.TABLE_NAME;

public class MemeDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "MemeDbHelper";
    private static final String dbName = "MemeDb";
    private static final int version = 1;

    public MemeDbHelper(Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create database if database is not yet created
        db.execSQL(Meme.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Commented the next line out because we want to keep the same database when app updates
        // Currently there are no changes to the database
        // db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Saves the meme into the database
    public void insertMeme(Meme meme) {
        // Open database
        SQLiteDatabase db = this.getWritableDatabase();

        // Add query values
        ContentValues values = new ContentValues();
        values.put(Meme.C_POSTLINK, meme.getPostLink());
        values.put(Meme.C_SUBREDDIT, meme.getSubreddit());
        values.put(Meme.C_TITLE, meme.getTitle());
        values.put(Meme.C_URL, meme.getUrl());
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        values.put(Meme.C_DATETIMEADDED, dateFormat.format(Calendar.getInstance().getTime()));

        // Insert meme into database
        try {
            db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.d(null, e.toString());
            values = new ContentValues();
            values.put(Meme.C_DATETIMEADDED, Calendar.getInstance().getTime().toString());
        }

        // Update meme timestamp
        try {
            db.update(TABLE_NAME, values, Meme.C_POSTLINK + "=?", new String[]{meme.getPostLink()});
        } catch (Exception ee) {

        }

        // Close database
        db.close();
    }

    // Deletes the specified meme
    public boolean deleteMeme(String pl,String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "postLink=? and title=?", new String[]{pl, title}) > 0;
    }

    // Return all the memes in the database
    public List<Meme> getAllMemes() {
        // Create memes list
        List<Meme> memes = new ArrayList<Meme>();

        // Set query
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + Meme.C_DATETIMEADDED + " DESC";

        // Open database
        SQLiteDatabase db = this.getWritableDatabase();

        // Run query
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Iterate through results and populate memes
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

        // Close database
        db.close();

        // Return memes
        return memes;

    }
}
