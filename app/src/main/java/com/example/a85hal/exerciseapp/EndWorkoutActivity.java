package com.example.a85hal.exerciseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.HashMap;

public class EndWorkoutActivity extends AppCompatActivity {

    HashMap<Exercise, Integer> stats;
    StatsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_workout);

        stats = (HashMap<Exercise, Integer>) getIntent().getSerializableExtra("stats");
        RecyclerView list = findViewById(R.id.stats_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StatsListAdapter(this, stats);
        list.setAdapter(adapter);
    }
}
