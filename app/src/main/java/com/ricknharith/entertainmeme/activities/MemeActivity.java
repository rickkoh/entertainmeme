package com.ricknharith.entertainmeme.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ricknharith.entertainmeme.R;
import com.github.chrisbanes.photoview.PhotoView;

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
