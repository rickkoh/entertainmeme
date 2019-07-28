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

        // Point of this activity is to display the meme in full size

        memePhotoView = (PhotoView)findViewById(R.id.memePhotoView);
        shareBtn = (ImageButton)findViewById(R.id.shareBtn);

        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");

        setTitle(title);

        Glide.with(this)
                .load(url)
                .into(memePhotoView);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(getIntent().getStringExtra("url")));
                shareIntent.setType("image/jpeg");
                startActivity(Intent.createChooser(shareIntent, "Share images to.."));
            }
        });
    }
}
