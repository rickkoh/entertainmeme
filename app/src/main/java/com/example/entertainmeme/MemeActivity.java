package com.example.entertainmeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.github.chrisbanes.photoview.PhotoView;

import org.json.JSONObject;

public class MemeActivity extends AppCompatActivity {

    PhotoView memePhotoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meme_viewer);

        // Point of this activity is to display the meme in full size

        memePhotoView = (PhotoView)findViewById(R.id.memePhotoView);

        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");

        setTitle(title);

        Glide.with(this)
                .load(url)
                .into(memePhotoView);

    }
}
