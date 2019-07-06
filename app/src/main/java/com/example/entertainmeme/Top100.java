package com.example.entertainmeme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class Top100 extends AppCompatActivity {
    ListView topListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top100);
        topListView=(ListView)findViewById(R.id.topListView);
    }
}
