package com.example.entertainmeme.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.entertainmeme.R;
import com.example.entertainmeme.helpers.MemeAdapter;
import com.example.entertainmeme.helpers.MemeDbHelper;
import com.example.entertainmeme.models.Meme;

import java.util.List;

public class InventoryActivity extends AppCompatActivity {

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

        final MemeAdapter memeAdapter = new MemeAdapter(this, memes);

        memeListView.setAdapter(memeAdapter);
    }
}
