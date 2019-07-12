package com.example.entertainmeme.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entertainmeme.R;
import com.example.entertainmeme.helpers.MemeAdapter;
import com.example.entertainmeme.helpers.MemeDbHelper;
import com.example.entertainmeme.models.Meme;

import java.util.List;

public class InventoryActivity extends AppCompatActivity implements MemeAdapter.OnClickListener {

    RecyclerView memeListView;
    RecyclerView.LayoutManager layoutManager;
    List<Meme> memes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        final MemeDbHelper memeDbHelper = new MemeDbHelper(this);

        memes = memeDbHelper.getAllMemes();

        memeListView = (RecyclerView)findViewById(R.id.memeListView);
        layoutManager = new LinearLayoutManager(this);
        memeListView.setLayoutManager(layoutManager);

        final MemeAdapter memeAdapter = new MemeAdapter(memes, this, this, "meme_row_card");

        memeListView.setAdapter(memeAdapter);
    }

    @Override
    public void onItemClickListener(int position) {
        Intent i = new Intent(this, MemeActivity.class);
        i.putExtra("title", memes.get(position).getTitle());
        i.putExtra("url", memes.get(position).getUrl());
        startActivity(i);
    }
}
