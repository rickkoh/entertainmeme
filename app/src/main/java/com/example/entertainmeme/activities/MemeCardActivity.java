package com.example.entertainmeme.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.entertainmeme.R;

public class MemeCardActivity extends AppCompatActivity {

    ImageView memeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meme_card);

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
