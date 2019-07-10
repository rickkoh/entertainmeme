package com.example.entertainmeme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.entertainmeme.helpers.MemeLoader;
import com.example.entertainmeme.R;
import com.example.entertainmeme.helpers.MemeDbHelper;
import com.example.entertainmeme.helpers.SwipeStackAdapter;

import java.util.Observable;
import java.util.Observer;

import link.fls.swipestack.SwipeStack;

public class MainActivity extends AppCompatActivity implements Observer {

    private static final String TAG = MainActivity.class.getSimpleName();

    TextView titleTextView;
    SwipeStack swipeStack;
    SwipeStackAdapter swipeStackAdapter;
    static Boolean swipeLocked = false;

    ImageButton previousBtn;
    ImageButton skipBtn;
    ImageButton likeBtn;
    ImageButton inventoryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an instance of database
        final MemeDbHelper memeDbHelper = new MemeDbHelper(this);

        // Access MemeLoader class
        MemeLoader.getInstance(this);
        // Register as an observer
        MemeLoader.getInstance().addObserver(this);

        // Variables
        titleTextView = (TextView)findViewById(R.id.titleTextView);
        swipeStack = (SwipeStack) findViewById(R.id.swipeStack);
        swipeStackAdapter = new SwipeStackAdapter(MemeLoader.getInstance().getMemes(), this);
        swipeStack.setAdapter(swipeStackAdapter);

        previousBtn = (ImageButton)findViewById(R.id.previousBtn);
        skipBtn = (ImageButton)findViewById(R.id.skipBtn);
        likeBtn = (ImageButton)findViewById(R.id.likeBtn);
        inventoryBtn = (ImageButton)findViewById(R.id.inventoryBtn);

        // Listeners
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeStack.swipeTopViewToLeft();
            }
        });

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeStack.swipeTopViewToRight();
            }
        });

        inventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, com.example.entertainmeme.activities.InventoryActivity.class);
                startActivity(i);
            }
        });

        swipeStack.setListener(new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {
                Log.d(TAG, "Swiped Left");
                MemeLoader.offloadMeme();
            }

            @Override
            public void onViewSwipedToRight(int position) {
                Log.d(TAG, "Swiped Right");
                // Save meme to database
                memeDbHelper.insertMeme(swipeStackAdapter.getItem(position));
                MemeLoader.offloadMeme();
            }

            @Override
            public void onStackEmpty() {
                swipeStackAdapter.notifyDataSetChanged();
            }
        });

        swipeStack.setSwipeProgressListener(new SwipeStack.SwipeProgressListener() {
            @Override
            public void onSwipeStart(int position) {
                swipeLocked = true;
            }

            @Override
            public void onSwipeProgress(int position, float progress) {
                swipeLocked = true;
            }

            @Override
            public void onSwipeEnd(int position) {
                swipeLocked = false;
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {

        // Update data if user is not swiping the meme
        if (!swipeLocked) swipeStackAdapter.notifyDataSetChanged();

        Log.d(TAG, MemeLoader.getInstance().getNoOfPreloadedMemes() + "");

    }
}
