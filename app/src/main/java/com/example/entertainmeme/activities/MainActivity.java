package com.example.entertainmeme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.entertainmeme.helper.MemeLoader;
import com.example.entertainmeme.R;
import com.example.entertainmeme.Top100;
import com.example.entertainmeme.helper.MemeDbHelper;
import com.example.entertainmeme.helper.SwipeStackAdapter;
import com.example.entertainmeme.model.Meme;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import link.fls.swipestack.SwipeStack;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    TextView titleTextView;
    ImageView memeImageView;
    SwipeStack swipeStack;
    SwipeStackAdapter swipeStackAdapter;

    Button previousBtn;
    Button skipBtn;
    Button likeBtn;
    Button inventoryBtn;
    Button topBtn;

    Meme meme;
    MemeLoader memeLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MemeDbHelper memeDbHelper = new MemeDbHelper(this);

        titleTextView = (TextView)findViewById(R.id.titleTextView);
        memeImageView = (ImageView)findViewById(R.id.memeImageView);
        swipeStack = (SwipeStack) findViewById(R.id.swipeStack);

        previousBtn = (Button)findViewById(R.id.previousBtn);
        skipBtn = (Button)findViewById(R.id.skipBtn);
        likeBtn = (Button)findViewById(R.id.likeBtn);
        inventoryBtn = (Button)findViewById(R.id.inventoryBtn);
        topBtn=(Button)findViewById(R.id.topBtn);

        topBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Top100.class);
                startActivity(i);
            }
        });

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
                swipeStackAdapter.notifyDataSetChanged();
                //
//                memeDbHelper.insertMeme(meme);
//                meme = memeLoader.getNext();
//                loadMeme();
            }
        });

        inventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, InventoryActivity.class);
                startActivity(i);
            }
        });

        memeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (meme == null) return;
                Intent i = new Intent(MainActivity.this, MemeActivity.class);
                i.putExtra("title", meme.getTitle());
                i.putExtra("url", meme.getUrl());
                startActivity(i);
            }
        });

        swipeStack.setListener(new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {

            }

            @Override
            public void onViewSwipedToRight(int position) {

            }

            @Override
            public void onStackEmpty() {

            }
        });

        memeLoader = new MemeLoader(MainActivity.this);

        meme = memeLoader.getCurrent();


        swipeStackAdapter = new SwipeStackAdapter(memeLoader.getMemes(), MainActivity.this);

        swipeStack.setAdapter(swipeStackAdapter);


    }

    public void loadMeme() {
        try {
            titleTextView.setText(meme.getTitle());
            memeImageView.setImageBitmap(meme.getImage());
            DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            Log.e(TAG, dateFormat.format(Calendar.getInstance().getTime()));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
