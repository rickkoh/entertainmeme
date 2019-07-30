package com.ricknharith.entertainmeme.helpers;
import android.content.Context;
import android.util.Log;

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
// It notifies observers when memes are loaded
public class MemeLoader extends Observable {

    // Create only one instance of MemeLoader
    private static MemeLoader memeLoader = null;

    // Create only one instance of API request queue
    private static RequestQueue requestQueue;

    // Memes are loaded from the API
    private static List<Meme> memes = new ArrayList<Meme>();
    private static int noOfPreloadedMemes = 0;
    private static int thresholdNoOfMemes = 10;

    // Instantiate MemeLoader
    private MemeLoader(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        loadMemes();
    }

    // Get instance of MemeLoader
    public static MemeLoader getInstance(Context context) {
        // Creates an instance of MemeLoader if it was not yet created
        if (memeLoader == null) memeLoader = new MemeLoader(context);
        return memeLoader;
    }

    // Get instance of MemeLoader
    public static MemeLoader getInstance() {
        // If the instance is not yet created, throw an error
        if (memeLoader == null)
            throw new IllegalStateException(MemeLoader.class.getSimpleName() + " is not initialized, call getInstance(...) first");
        // Return the instance
        return memeLoader;
    }

    // Is usually called when a meme is swiped
    // Tells MemeLoader to start loading more memes
    public static void decreasePreloadedMemesCount() {
        if (noOfPreloadedMemes > 0) noOfPreloadedMemes-=1;
        memeLoader.loadMemes();
    }

    // Is usually called when a meme is rewounded
    // Prevent MemeLoader from loading more memes since number of preloaded memes will be more than threshold
    public static void increasePreloadedMemesCount() {
        noOfPreloadedMemes+=1;
    }

    // Get a meme based on a specified position
    public Meme getMeme(int position) {
        return memes.get(position);
    }

    // Return memes
    public List<Meme> getMemes() {
        return memes;
    }

    // Remove a meme from the list of memes
    public void removeMeme(int position) {
        memes.remove(position);
    }

    // Get the number of preloaded memes
    public static int getNoOfPreloadedMemes() {
        return noOfPreloadedMemes;
    }

    // Load the memes from the API
    private void loadMemes() {
        // Return if the number of preloaded memes is more than the threshold
        if (noOfPreloadedMemes >= thresholdNoOfMemes) return;

        // Set API url
        String url = "https://meme-api.herokuapp.com/gimme";

        // Create new API request
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (noOfPreloadedMemes <= thresholdNoOfMemes) {
                            try {
                                // Convert response to JSON
                                JSONObject j = new JSONObject(response);
                                // Create meme object based on JSON
                                memes.add(new Meme(j.getString("postLink"), j.getString("subreddit"), j.getString("title"), j.getString("url")));
                                // Notify observers
                                setChanged();
                                notifyObservers();
                                // Increase number of preloaded memes count
                                noOfPreloadedMemes +=1;
                                // Return if the number of preloaded memes is more than the threshold
                                if (noOfPreloadedMemes < thresholdNoOfMemes) loadMemes();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(null, error.toString());
                    }
                });

        // Add request to the API queue
        requestQueue.add(stringRequest);
        // API queue will run request when all the requests ahead have been completed
    }


}
