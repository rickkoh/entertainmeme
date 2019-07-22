package com.example.entertainmeme.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entertainmeme.R;
import com.example.entertainmeme.helpers.MemeDbHelper;
import com.example.entertainmeme.helpers.MemeLayoutAdapter;
import com.example.entertainmeme.models.Meme;

import java.net.URI;
import java.util.List;

public class InventoryActivity extends AppCompatActivity implements MemeLayoutAdapter.OnClickListener {

    MemeDbHelper memeDbHelper;

    RecyclerView memeListView;
    MemeLayoutAdapter memeLayoutAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Meme> memes;
    ImageButton backBtn;
    String ImagePath;
    String memeposition;
    Bitmap bitmap;
    URI uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        memeDbHelper = new MemeDbHelper(this);

        memes = memeDbHelper.getAllMemes();

        backBtn=(ImageButton)findViewById(R.id.backBtn);
        memeListView = (RecyclerView)findViewById(R.id.memeListView);
        layoutManager = new LinearLayoutManager(this);
        memeListView.setLayoutManager(layoutManager);

        memeLayoutAdapter = new MemeLayoutAdapter(memes, this, this);

        memeListView.setAdapter(memeLayoutAdapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InventoryActivity.this, com.example.entertainmeme.activities.MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onItemClickListener(int position) {
        Intent i = new Intent(this, MemeActivity.class);
        i.putExtra("title", memes.get(position).getTitle());
        i.putExtra("url", memes.get(position).getUrl());
        startActivity(i);
    }

    @Override
    public void onElipsisButtonClickListener(final View view, final int position) {
        PopupMenu popup = new PopupMenu(this, view);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.save_meme:
                        return true;
                    case R.id.delete_meme:
                        memes.remove(position);
                        memeLayoutAdapter.notifyDataSetChanged();
                        return true;
                    case R.id.share_meme:
                        return true;
                    default:
                        return false;
                }
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.inventorymenu, popup.getMenu());
        popup.show();
    }

}
