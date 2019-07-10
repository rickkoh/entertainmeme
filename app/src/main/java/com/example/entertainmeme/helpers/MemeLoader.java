package com.example.entertainmeme.helpers;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.entertainmeme.models.Meme;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Queue;
import java.util.Stack;

import static android.content.ContentValues.TAG;

// This class is a singleton class
// It is responsible for handling the loading of memes
// It can be observed
// It notifies observers when meme(s) are loaded
public class MemeLoader extends Observable {

    // Can only create one instance of MemeLoader
    private static MemeLoader memeLoader = null;

    public RequestQueue requestQueue;

    // Implements LIFO data structure
    private static Stack<Meme> previousMemes = new Stack<Meme>();

    private static List<Meme> memes = new ArrayList<Meme>();
    private static int noOfMemesInQueue = 0;

    // Instantiate MemeLoader
    private MemeLoader(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        loadMemes();
    }

    // Get instance of MemeLoader
    public static MemeLoader getInstance(Context context) {
        if (memeLoader == null) memeLoader = new MemeLoader(context);
        return memeLoader;
    }

    // Get instance of MemeLoader
    public static MemeLoader getInstance() {
        if (memeLoader == null)
            throw new IllegalStateException(MemeLoader.class.getSimpleName() + " is not initialized, call getInstance(...) first");
        return memeLoader;
    }

    public static void loadMore() {
        noOfMemesInQueue-=1;
        memeLoader.loadMemes();
    }

    public static int getNoOfMemesInQueue() {
        return noOfMemesInQueue;
    }

    private void loadMemes() {
        String url = "https://meme-api.herokuapp.com/gimme";
        if (noOfMemesInQueue >= 10) return;
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject j = new JSONObject(response);
                            memes.add(new Meme(j.getString("postLink"), j.getString("subreddit"), j.getString("title"), j.getString("url")));
                            setChanged();
                            notifyObservers();
                            noOfMemesInQueue+=1;
                            if (noOfMemesInQueue < 10) loadMemes();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue.add(stringRequest);
    }
    
    public List<Meme> getMemes() {
        return memes;
    }

}
