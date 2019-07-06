package com.example.entertainmeme;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TopLoader {
    private Context context;

    List<TopMemes> meme= new ArrayList<TopMemes>();

    public TopLoader(Context c){
        context= c;
        loadMemes();
    }

    private void loadMemes(){
        RequestQueue queue= Volley.newRequestQueue(context);
        String url = "https://api.imgflip.com/get_memes";
        StringRequest stringRequest= new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject j = new JSONObject(response);
                            meme.add(new TopMemes(context, j.getString("name"), j.getString("url")));
                            if (meme.size() > 99) loadMemes();
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

