package com.example.entertainmeme.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.entertainmeme.R;
import com.example.entertainmeme.models.TopMemes;

import java.util.List;

public class Top100 extends AppCompatActivity {
    ListView topListView;
    List<TopMemes>topMemesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top100);
        topListView=(ListView)findViewById(R.id.topListView);




    }
}
