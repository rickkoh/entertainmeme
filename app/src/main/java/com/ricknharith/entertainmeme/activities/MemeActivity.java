package com.ricknharith.entertainmeme.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ricknharith.entertainmeme.R;
import com.github.chrisbanes.photoview.PhotoView;

public class MemeActivity extends AppCompatActivity {

    PhotoView memePhotoView;
    ImageButton shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meme_viewer);

        // Instantiate components
        memePhotoView = (PhotoView)findViewById(R.id.memePhotoView);
        shareBtn = (ImageButton)findViewById(R.id.shareBtn);

        // Get variables
        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");

        // Set the title
        setTitle(title);

        // Load image into memePhotoView
        Glide.with(this)
                .load(url)
                .into(memePhotoView);

        // Set OnClickListener for share button
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create new intent to open share sheet
                Intent shareIntent = new Intent();
                // Set share button properties
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(getIntent().getStringExtra("url")));
                shareIntent.setType("image/jpeg");
                startActivity(Intent.createChooser(shareIntent, "Share images to.."));
            }
        });
    }
}
