package com.example.entertainmeme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.entertainmeme.helpers.MemeLoader;
import com.example.entertainmeme.R;
import com.example.entertainmeme.helpers.MemeDbHelper;
import com.example.entertainmeme.helpers.SwipeStackAdapter;
import com.example.entertainmeme.models.Meme;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import link.fls.swipestack.SwipeStack;

public class MainActivity extends AppCompatActivity implements Observer {

    private static final String TAG = MainActivity.class.getSimpleName();

    TextView titleTextView;
    SwipeStack swipeStack;
    SwipeStackAdapter swipeStackAdapter;
    Boolean swipeLocked = true;

    Button previousBtn;
    Button skipBtn;
    Button likeBtn;
    Button inventoryBtn;
    Button topBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MemeDbHelper memeDbHelper = new MemeDbHelper(this);

        MemeLoader.getInstance(this);
        MemeLoader.getInstance().addObserver(this);

        titleTextView = (TextView)findViewById(R.id.titleTextView);
        swipeStack = (SwipeStack) findViewById(R.id.swipeStack);
        swipeStackAdapter = new SwipeStackAdapter(MemeLoader.getInstance().getMemes(), this);
        swipeStack.setAdapter(swipeStackAdapter);

        previousBtn = (Button)findViewById(R.id.previousBtn);
        skipBtn = (Button)findViewById(R.id.skipBtn);
        likeBtn = (Button)findViewById(R.id.likeBtn);
        inventoryBtn = (Button)findViewById(R.id.inventoryBtn);
        topBtn=(Button)findViewById(R.id.topBtn);

        topBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, swipeStack.getCurrentPosition() + "");
                swipeStackAdapter.notifyDataSetChanged();

            }
        });

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
                Log.d(TAG, "Swiped left");
                MemeLoader.loadMore();
            }

            @Override
            public void onViewSwipedToRight(int position) {
                Log.d(TAG, "Swiped right");
                memeDbHelper.insertMeme(swipeStackAdapter.getItem(position));
                MemeLoader.loadMore();
            }

            @Override
            public void onStackEmpty() {
            }
        });

        swipeStack.setSwipeProgressListener(new SwipeStack.SwipeProgressListener() {
            @Override
            public void onSwipeStart(int position) {
                swipeLocked = false;
            }

            @Override
            public void onSwipeProgress(int position, float progress) {
                swipeLocked = false;
            }

            @Override
            public void onSwipeEnd(int position) {
                swipeLocked = true;
            }
        });

    }

    @Override
    public void update(Observable o, Object arg) {

        if (swipeLocked) swipeStackAdapter.notifyDataSetChanged();

    }
}
