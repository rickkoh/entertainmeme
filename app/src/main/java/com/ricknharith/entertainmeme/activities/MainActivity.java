package com.ricknharith.entertainmeme.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ricknharith.entertainmeme.helpers.MemeAdapter;
import com.ricknharith.entertainmeme.helpers.MemeLoader;
import com.ricknharith.entertainmeme.R;
import com.ricknharith.entertainmeme.helpers.MemeDbHelper;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer, CardStackListener, MemeAdapter.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    MemeDbHelper memeDbHelper;

    CardStackView memeCardStackView;
    MemeAdapter memeAdapter;
    CardStackLayoutManager memeCardStackLayoutManager;
    int position = 0;

    TextView titleTextView;
    ImageButton previousBtn;
    ImageButton skipBtn;
    ImageButton likeBtn;
    ImageButton inventoryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an instance of database
        memeDbHelper = new MemeDbHelper(this);

        // Get MemeLoader class
        MemeLoader.getInstance(this);
        // Register observer
        MemeLoader.getInstance().addObserver(this);

        // Instantiate card stack view
        memeCardStackView = (CardStackView)findViewById(R.id.memeCardStack);
        memeCardStackLayoutManager = new CardStackLayoutManager(this, this);
        memeCardStackLayoutManager.setDirections(Direction.HORIZONTAL);

        // Set card stack view properties
        memeAdapter = new MemeAdapter(MemeLoader.getInstance().getMemes(), this);
        memeCardStackView.setLayoutManager(memeCardStackLayoutManager);
        memeCardStackView.setAdapter(memeAdapter);

        // Instantiate components
        titleTextView = (TextView)findViewById(R.id.titleTextView);
        previousBtn = (ImageButton)findViewById(R.id.previousBtn);
        skipBtn = (ImageButton)findViewById(R.id.skipBtn);
        likeBtn = (ImageButton)findViewById(R.id.likeBtn);
        inventoryBtn = (ImageButton)findViewById(R.id.inventoryBtn);

        // Set OnClickListener for previous button
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rewind the cardstack
                memeCardStackView.rewind();
            }
        });

        // Set OnClickListener for skip button
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set swipe direction to left
                memeCardStackLayoutManager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder().setDirection(Direction.Left).build());
                // Swipe card
                memeCardStackView.swipe();
            }
        });

        // Set OnClickListener for like button
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set swipe direction to right
                memeCardStackLayoutManager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder().setDirection(Direction.Right).build());
                // Swipe card
                memeCardStackView.swipe();
            }
        });

        // Set OnClickListener for inventory button
        inventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create new intent to launch inventory activity
                Intent i = new Intent(MainActivity.this, com.ricknharith.entertainmeme.activities.InventoryActivity.class);
                // Launch intent
                startActivity(i);
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {

        // Update meme data if user is not swiping the meme
        memeAdapter.notifyItemInserted(memeAdapter.getItemCount());
        memeCardStackLayoutManager.setTopPosition(position);

        Log.d(TAG, MemeLoader.getInstance().getNoOfPreloadedMemes() + "");

    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {
    }

    @Override
    public void onCardSwiped(Direction direction) {
        MemeLoader.decreasePreloadedMemesCount();
        // Detect the swiped direction of the meme
        if (direction == Direction.Right) {
            // If card was swiped right
            // Save meme to database
            memeDbHelper.insertMeme(MemeLoader.getInstance().getMeme(position));
            // Remove item from the stack
            memeAdapter.notifyItemRemoved(position);
            MemeLoader.getInstance().removeMeme(position);
        } else {
            // Increase position by 1
            position += 1;
        }
    }

    @Override
    public void onCardRewound() {
        // Increase the preloaded memes count
        MemeLoader.increasePreloadedMemesCount();
        // Decrease position by 1
        position -= 1;
    }

    @Override
    public void onCardCanceled() { }

    @Override
    public void onCardAppeared(View view, int position) { }

    @Override
    public void onCardDisappeared(View view, int position) { }

    @Override
    public void onCardClickListener(int position) {
        // Create new intent to launch meme activity
        Intent i = new Intent(this, MemeActivity.class);
        // Add data to parse
        i.putExtra("title", MemeLoader.getInstance().getMeme(position).getTitle());
        i.putExtra("url", MemeLoader.getInstance().getMeme(position).getUrl());
        // Launch intent
        startActivity(i);
    }
}
