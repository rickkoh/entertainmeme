package com.example.entertainmeme.helper;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.entertainmeme.model.Meme;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MemeLoader {

    private Context context;

    private int pointer = 0;

    private List<Meme> memes = new ArrayList<Meme>();

    public MemeLoader(Context c) {
        context = c;
        loadMemes();
    }

    public Meme getPrevious() {
        if (pointer != 0 || pointer < 0) {
            pointer-=1;
        }
        return meme(pointer);
    }

    public Meme getCurrent() {
        return meme(pointer);
    }

    public Meme getNext() {
        pointer += 1;
        if (pointer >= 6) {
            pointer-=1;
            memes.remove(0);
            loadMemes();
        }
        return meme(pointer);
    }

    public List<Meme> getMemes() {
        return memes;
    }

    private Meme meme(int pointer) {
        if (pointer >= memes.size()) return null;
        return memes.get(pointer);
    }

    private void loadMemes() {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://meme-api.herokuapp.com/gimme";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject j = new JSONObject(response);
                            memes.add(new Meme(context, j.getString("postLink"), j.getString("subreddit"), j.getString("title"), j.getString("url")));
                            if (memes.size() < 10) loadMemes();
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
        queue.add(stringRequest);

    }

}
