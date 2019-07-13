package com.example.entertainmeme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entertainmeme.R;
import com.example.entertainmeme.helpers.MemeDbHelper;
import com.example.entertainmeme.helpers.MemeLayoutAdapter;
import com.example.entertainmeme.models.Meme;

import java.util.List;

public class InventoryActivity extends AppCompatActivity implements MemeLayoutAdapter.OnClickListener {

    MemeDbHelper memeDbHelper;

    RecyclerView memeListView;
    MemeLayoutAdapter memeLayoutAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Meme> memes;
    ImageButton backBtn;

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
    public void onButtonClickListener(int position) {
        memes.remove(position);
        memeLayoutAdapter.notifyDataSetChanged();
    }
}
