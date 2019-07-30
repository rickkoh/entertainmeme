package com.ricknharith.entertainmeme.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ricknharith.entertainmeme.R;
import com.ricknharith.entertainmeme.helpers.MemeDbHelper;
import com.ricknharith.entertainmeme.helpers.MemeLayoutAdapter;
import com.ricknharith.entertainmeme.models.Meme;

import java.util.List;

public class InventoryActivity extends AppCompatActivity implements MemeLayoutAdapter.OnClickListener {

    // Instantiate db object
    MemeDbHelper memeDbHelper;

    // Instantiate controls
    RecyclerView memeListView;
    MemeLayoutAdapter memeLayoutAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Meme> memes;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        // Create db object
        memeDbHelper = new MemeDbHelper(this);

        // Get all the memes from the database
        memes = memeDbHelper.getAllMemes();

        // Instantiate components
        backBtn = (ImageButton)findViewById(R.id.backBtn);
        memeListView = (RecyclerView)findViewById(R.id.memeListView);

        // Set recyclerview properties
        layoutManager = new LinearLayoutManager(this);
        memeListView.setLayoutManager(layoutManager);
        memeLayoutAdapter = new MemeLayoutAdapter(memes, this, this);
        memeListView.setAdapter(memeLayoutAdapter);

        // Set OnClickListener for back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // End activity
                // And return
                finish();
            }
        });
    }

    @Override
    public void onCardClickListener(int position) {
        // Create new intent to launch meme activity
        Intent i = new Intent(this, MemeActivity.class);
        // Add data to parse
        i.putExtra("title", memes.get(position).getTitle());
        i.putExtra("url", memes.get(position).getUrl());
        // Launch intent
        startActivity(i);
    }

    @Override
    public void onElipsisButtonClickListener(final View view, final int position) {
        // Create a popup menu
        PopupMenu popup = new PopupMenu(this, view);

        // Set OnClickListener for menu item
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Detect which menu item is clicked
                switch (item.getItemId()) {
                    case R.id.delete_meme:
                        // If the delete menu item is clicked
                        // Delete the meme from the database
                        memeDbHelper.deleteMeme(memes.get(position).getPostLink(),memes.get(position).getTitle());
                        // Remove meme from list
                        memes.remove(position);
                        // Update meme removed from list
                        memeLayoutAdapter.notifyItemRemoved(position);
                        return true;
                    case R.id.share_meme:
                        // If the share menu item is clicked
                        // Create new intent to open share sheet
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse(memes.get(position).getUrl()));
                        shareIntent.setType("image/jpeg");
                        startActivity(Intent.createChooser(shareIntent, "Share images to.."));
                        return true;
                    default:
                        return false;
                }
            }
        });

        // Inflate the pop up menu with a view
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.inventorymenu, popup.getMenu());
        // Show the pop up menu
        popup.show();
    }

}
