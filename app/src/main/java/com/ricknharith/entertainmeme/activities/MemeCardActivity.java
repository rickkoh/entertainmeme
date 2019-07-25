package com.ricknharith.entertainmeme.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.ricknharith.entertainmeme.R;

public class MemeCardActivity extends AppCompatActivity {

    ImageView memeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meme_swipe_card);

        memeImageView = (ImageView)findViewById(R.id.memeImageView);

        memeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MemeCardActivity.this, MemeActivity.class);
                startActivity(i);
            }
        });

    }
}
