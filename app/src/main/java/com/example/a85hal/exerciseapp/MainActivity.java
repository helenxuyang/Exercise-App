package com.example.a85hal.exerciseapp;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button workoutButton = findViewById(R.id.button_workout);
        Button newExerciseButton = findViewById(R.id.button_new_exercise);

            workoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent workoutIntent = new Intent(MainActivity.this, WorkoutActivity.class);
                    MainActivity.this.startActivity(workoutIntent);
                }
            });

            newExerciseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent exerciseListIntent = new Intent(MainActivity.this, ExerciseListActivity.class);
                    MainActivity.this.startActivity(exerciseListIntent);
                }
            });

    }
}
