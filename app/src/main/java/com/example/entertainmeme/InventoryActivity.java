package com.example.entertainmeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class InventoryActivity extends AppCompatActivity {

    ListView memeListView;
    List<Meme> memes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        final MemeDbHelper memeDbHelper = new MemeDbHelper(this);

        memes = memeDbHelper.getAllMemes();

        memeListView = (ListView)findViewById(R.id.memeListView);

        final MemeAdapter memeAdapter = new MemeAdapter(this, memes);

        memeListView.setAdapter(memeAdapter);

        memeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(InventoryActivity.this, MemeActivity.class);
                i.putExtra("title", memes.get(position).getTitle());
                i.putExtra("url", memes.get(position).getUrl());
                startActivity(i);

                // Remove item from the list
                //memes.remove(position);
                // Update the adapter
                //memeAdapter.notifyDataSetChanged();
            }
        });
    }
}
