package com.example.entertainmeme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView titleTextView;
    ImageView memeImageView;

    Button previousBtn;
    Button skipBtn;
    Button likeBtn;
    Button inventoryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleTextView = (TextView)findViewById(R.id.titleTextView);
        memeImageView = (ImageView)findViewById(R.id.memeImageView);

        previousBtn = (Button)findViewById(R.id.previousBtn);
        skipBtn = (Button)findViewById(R.id.skipBtn);
        likeBtn = (Button)findViewById(R.id.likeBtn);
        inventoryBtn = (Button)findViewById(R.id.inventoryBtn);

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAPI();
            }
        });

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAPI();
            }
        });

        inventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, InventoryActivity.class);
                startActivity(i);
            }
        });

        fetchAPI();

    }

    public void fetchAPI() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://meme-api.herokuapp.com/gimme";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json;
                        try {
                            json = new JSONObject(response);
                            titleTextView.setText(json.getString("title"));
                            Glide.with(MainActivity.this)
                                    .load(json.getString("url"))
                                    .into(memeImageView);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                titleTextView.setText("That didn't work!");
            }
        });

        queue.add(stringRequest);

    }
}
