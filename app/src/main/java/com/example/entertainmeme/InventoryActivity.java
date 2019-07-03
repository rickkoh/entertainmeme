package com.example.entertainmeme;

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

        final MemeDbHandler memeDbHandler = new MemeDbHandler(this);

        memes = memeDbHandler.getAllMemes();

        memeListView = (ListView)findViewById(R.id.memeListView);

        final MemeAdapter memeAdapter = new MemeAdapter(this, memes);

        memeListView.setAdapter(memeAdapter);

        memeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Remove item from the list
                //memes.remove(position);
                // Update the adapter
                //memeAdapter.notifyDataSetChanged();
            }
        });
    }
}
