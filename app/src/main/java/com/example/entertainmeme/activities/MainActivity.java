package com.example.entertainmeme.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.entertainmeme.helpers.MemeAdapter;
import com.example.entertainmeme.helpers.MemeLoader;
import com.example.entertainmeme.R;
import com.example.entertainmeme.helpers.MemeDbHelper;
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

    TextView titleTextView;

    CardStackView memeCardStackView;
    MemeAdapter memeAdapter;
    CardStackLayoutManager memeCardStackLayoutManager;
    int position = 0;

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

        // Access MemeLoader class
        MemeLoader.getInstance(this);
        // Register as an observer
        MemeLoader.getInstance().addObserver(this);

        // Variables
        titleTextView = (TextView)findViewById(R.id.titleTextView);

        memeCardStackView = (CardStackView)findViewById(R.id.memeCardStack);
        memeCardStackLayoutManager = new CardStackLayoutManager(this, this);
        memeCardStackLayoutManager.setDirections(Direction.HORIZONTAL);

        memeAdapter = new MemeAdapter(MemeLoader.getInstance().getMemes(), this);
        memeCardStackView.setLayoutManager(memeCardStackLayoutManager);
        memeCardStackView.setAdapter(memeAdapter);

        previousBtn = (ImageButton)findViewById(R.id.previousBtn);
        skipBtn = (ImageButton)findViewById(R.id.skipBtn);
        likeBtn = (ImageButton)findViewById(R.id.likeBtn);
        inventoryBtn = (ImageButton)findViewById(R.id.inventoryBtn);

        // Listeners
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memeCardStackView.rewind();
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memeCardStackLayoutManager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder().setDirection(Direction.Left).build());
                memeCardStackView.swipe();
            }
        });

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memeCardStackLayoutManager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder().setDirection(Direction.Right).build());
                memeCardStackView.swipe();
            }
        });

        inventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, com.example.entertainmeme.activities.InventoryActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {

        // Update data if user is not swiping the meme
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
        if (direction == Direction.Right) {
            memeDbHelper.insertMeme(MemeLoader.getInstance().getMeme(position));
            memeAdapter.notifyItemRemoved(position);
            MemeLoader.getInstance().removeMeme(position);
        } else {
            position += 1;
        }
    }

    @Override
    public void onCardRewound() {
        MemeLoader.increasePreloadedMemesCount();
        position -= 1;
    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }

    @Override
    public void onItemClickListener(int position) {
        Intent i = new Intent(this, MemeActivity.class);
        i.putExtra("title", MemeLoader.getInstance().getMeme(position).getTitle());
        i.putExtra("url", MemeLoader.getInstance().getMeme(position).getUrl());
        startActivity(i);
    }
}
