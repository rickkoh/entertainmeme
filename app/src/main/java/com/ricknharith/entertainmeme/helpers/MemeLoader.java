package com.ricknharith.entertainmeme.helpers;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ricknharith.entertainmeme.models.Meme;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

// This class is a singleton class
// It is responsible for handling the loading of memes
// It can be observed
// It notifies observers when meme(s) are loaded
public class MemeLoader extends Observable {

    // Can only create one instance of MemeLoader
    private static MemeLoader memeLoader = null;

    private static RequestQueue requestQueue;

    // List of memes loaded from the API
    private static List<Meme> memes = new ArrayList<Meme>();
    private static int noOfPreloadedMemes = 0;

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

    //
    public static void decreasePreloadedMemesCount() {
        if (noOfPreloadedMemes > 0) noOfPreloadedMemes-=1;
        memeLoader.loadMemes();
    }

    //
    public static void increasePreloadedMemesCount() {
        noOfPreloadedMemes+=1;
    }

    public Meme getMeme(int position) {
        return memes.get(position);
    }

    // Return memes
    public List<Meme> getMemes() {
        return memes;
    }

    public void removeMeme(int position) {
        memes.remove(position);
    }

    public static int getNoOfPreloadedMemes() {
        return noOfPreloadedMemes;
    }

    // Load memes from API
    private void loadMemes() {
        if (noOfPreloadedMemes >= 10) return;
        String url = "https://meme-api.herokuapp.com/gimme";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (noOfPreloadedMemes <= 10) {
                            try {
                                JSONObject j = new JSONObject(response);
                                memes.add(new Meme(j.getString("postLink"), j.getString("subreddit"), j.getString("title"), j.getString("url")));
                                setChanged();
                                notifyObservers();
                                noOfPreloadedMemes +=1;
                                if (noOfPreloadedMemes < 10) loadMemes();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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


}
