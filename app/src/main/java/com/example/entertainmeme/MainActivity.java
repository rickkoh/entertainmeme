package com.example.entertainmeme;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    TextView titleTextView;
    ImageView memeImageView;

    Button previousBtn;
    Button skipBtn;
    Button likeBtn;
    Button inventoryBtn;

    Meme meme;
    MemeLoader memeLoader;

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
                meme = memeLoader.getPrevious();
                loadMeme();
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meme = memeLoader.getNext();
                loadMeme();
            }
        });

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://meme-api.herokuapp.com/gimme";

                StringRequest stringRequest = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject j = new JSONObject(response);
                                    titleTextView.setText(j.getString("title"));
                                    Glide.with(MainActivity.this).load(j.getString("url")).into(memeImageView);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e(TAG, e.toString());
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError e) {
                                Log.e(TAG, e.toString());
                            }
                        });

                queue.add(stringRequest);
            }
        });

        inventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, InventoryActivity.class);
                startActivity(i);
            }
        });

        memeLoader = new MemeLoader(MainActivity.this);

    }

    public void loadMeme() {
        try {
            titleTextView.setText(meme.getTitle());
            memeImageView.setImageBitmap(meme.getImage());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

    }
}
