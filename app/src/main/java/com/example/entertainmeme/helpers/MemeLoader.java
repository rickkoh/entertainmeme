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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Queue;
import java.util.Stack;

// This class is a singleton class
// It is responsible for handling the loading of memes
// It can be observed
// It notifies observers when meme(s) are loaded
public class MemeLoader extends Observable {

    // Can only create one instance of MemeLoader
    private static MemeLoader memeLoader = null;

    public RequestQueue requestQueue;

    // Implements FIFO data structure
    private static Queue<Meme> preloadedMemes = new LinkedList<Meme>();
    // Implements LIFO data structure
    private static Stack<Meme> previousMemes = new Stack<Meme>();

    private static int arraySize = 10;
    private static Meme[] memes = new Meme[arraySize];

    // Instantiate MemeLoader
    private MemeLoader(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        loadMemes();
    }

    // Get the instance of MemeLoader
    public static MemeLoader getInstance(Context context) {
        if (memeLoader == null) memeLoader = new MemeLoader(context);
        return memeLoader;
    }

    public static MemeLoader getInstance() {
        if (memeLoader == null) throw new IllegalStateException(MemeLoader.class.getSimpleName() + " is not initialized, call getInstance(...) first");
        return memeLoader;
    }

    // Get previous meme
    public Meme getPrevious() {
        // Retrieve head from stack
        // Remove head from stack
        // Return head from stack
        return previousMemes.pop();
    }

    // Get next meme
    public Meme getNext() {
        // Retrieve head from queue
        // Add head to stack
        // Remove head from queue
        previousMemes.push(preloadedMemes.poll());
        Log.d(null, preloadedMemes.size() + " Get Next()");
        loadMemes();
        // Return next in line meme
        return getMeme();
    }

    // Get current meme
    public Meme getMeme() {
        return preloadedMemes.peek();
    }

    public List<Meme> getMemes() {
        return new ArrayList<Meme>(preloadedMemes);
    }

    private void loadMemes() {
        String url = "https://meme-api.herokuapp.com/gimme";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject j = new JSONObject(response);
                            preloadedMemes.add(new Meme(j.getString("postLink"), j.getString("subreddit"), j.getString("title"), j.getString("url")));
                            setChanged();
                            notifyObservers();
                            if (preloadedMemes.size() < 10) loadMemes();
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

}
