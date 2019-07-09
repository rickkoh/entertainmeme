package com.example.entertainmeme.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.entertainmeme.R;

public class MemeCardActivity extends AppCompatActivity {

    ImageView memeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meme_card);

        memeImageView = (ImageView)findViewById(R.id.memeImageView);

    }
}
