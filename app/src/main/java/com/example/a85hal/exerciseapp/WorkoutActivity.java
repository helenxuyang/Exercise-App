package com.example.a85hal.exerciseapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class WorkoutActivity extends AppCompatActivity {

    private int bossHP = 200;
    private int playerHP = 50;

    private ExerciseTask currentTask;
    private ArrayList<Exercise> exerciseList;
    private HashMap<String, Integer> stats;
    private Iterator iterator;
    DatabaseHelper db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        db = new DatabaseHelper(this);
        cursor = db.getAllData();

        exerciseList = getExercises();
        iterator = exerciseList.iterator();
        stats = new HashMap<>();

        updateHPs();

        Button doneButton = findViewById(R.id.button_done);
        Button skipButton = findViewById(R.id.button_skip);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bossHP -= currentTask.getAttackPoints();
                updateStats(currentTask);
                updateTask();
                updateHPs();
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerHP -= currentTask.getAttackPoints();
                updateTask();
                updateHPs();
            }
        });

        updateTask();

        if (bossHP <= 0 || playerHP <= 0) {
            endWorkout();
        }
        Button finishButton = findViewById(R.id.button_finish);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               endWorkout();
            }
        });
    }

    private ArrayList<Exercise> getExercises() {
        ArrayList<Exercise> list = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME));
            int min = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_MIN));
            int max = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_MAX));
            String typeString = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TYPE));
            ExerciseType type = ExerciseType.getTypeFromString(typeString);
            Exercise ex = new Exercise(name, min, max, type);
            list.add(ex);
            cursor.moveToNext();
        }
        return list;
    }

    private void endWorkout() {
        Intent endIntent = new Intent(WorkoutActivity.this, EndWorkoutActivity.class);
        endIntent.putExtra("stats", stats);
        WorkoutActivity.this.startActivity(endIntent);
    }

    private void updateTask() {
        int index = new Random().nextInt(exerciseList.size());
        Exercise ex = exerciseList.get(index);
        currentTask = new ExerciseTask(ex);
        TextView exerciseText = findViewById(R.id.text_exercise);
        exerciseText.setText(currentTask.getTaskText());
    }

    private void updateStats(ExerciseTask task) {
        Exercise ex = task.getExercise();
        String name = ex.getName();
        int num = task.getNum();
       if (stats.containsKey(name)) {
           stats.put(name, stats.get(name) + num);
       }
       else {
           stats.put(name, num);
       }
    }

    private void updateHPs() {
        TextView bossHPText = findViewById(R.id.text_boss_hp);
        bossHPText.setText("Boss HP: " + bossHP);
        TextView playerHPText = findViewById(R.id.text_player_hp);
        playerHPText.setText("Player HP: " + playerHP);
    }
}
