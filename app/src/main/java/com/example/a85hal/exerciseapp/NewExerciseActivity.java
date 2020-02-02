package com.example.a85hal.exerciseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewExerciseActivity extends AppCompatActivity {

    public static final String REPEATOPTION = "Repeated";
    public static final String TIMEOPTION = "Timed";

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);

        db = new DatabaseHelper(this);

        initSpinner();
        initMinMax();
        initTypeListener();
        initSaveListener();
        initCancelListener();
    }

    private void initSpinner() {
        Spinner spinner = findViewById(R.id.spinner);
        List<String> spinnerOptions = new ArrayList<String>();
        spinnerOptions.add(REPEATOPTION);
        spinnerOptions.add(TIMEOPTION);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerOptions);
        spinner.setAdapter(adapter);
    }

    private void initTypeListener() {
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = findViewById(R.id.spinner);
                TextView minText = findViewById(R.id.text_min);
                TextView maxText = findViewById(R.id.text_max);
                if (spinner.getSelectedItem() != null && spinner.getSelectedItem().equals(REPEATOPTION)) {
                    minText.setText("Min repetitions");
                    maxText.setText("Max repetitions");
                } else {
                    minText.setText("Min # minutes");
                    maxText.setText("Max # minutes");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initMinMax() {
        NumberPicker minPicker = findViewById(R.id.picker_min);
        NumberPicker maxPicker = findViewById(R.id.picker_max);
        minPicker.setMinValue(1);
        minPicker.setMaxValue(100);
        maxPicker.setMinValue(1);
        maxPicker.setMaxValue(100);
    }

    private void initCancelListener() {
        Button cancelButton = findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newExerciseIntent = new Intent(NewExerciseActivity.this, ExerciseListActivity.class);
                NewExerciseActivity.this.startActivity(newExerciseIntent);
            }
        });
    }

    private void addExercise() {
        Spinner spinner = findViewById(R.id.spinner);
        NumberPicker minPicker = findViewById(R.id.picker_min);
        NumberPicker maxPicker = findViewById(R.id.picker_max);
        EditText nameInput = findViewById(R.id.input_name);

        String name = nameInput.getText().toString();
        int minSelection = minPicker.getValue();
        int maxSelection = maxPicker.getValue();

        if (minSelection > maxSelection) {
            Toast.makeText(NewExerciseActivity.this, "Max must be greater or equal to min", Toast.LENGTH_LONG).show();
        }
        else if (dbHasName(name)) {
            Toast.makeText(NewExerciseActivity.this, "Exercise with same name already exists", Toast.LENGTH_LONG).show();
        }
        else if (name.trim().length() == 0) {
            Toast.makeText(NewExerciseActivity.this, "Enter an exercise name", Toast.LENGTH_LONG).show();
        }
        else {
            boolean isInserted = db.insertData(name, minPicker.getValue(), maxPicker.getValue(), spinner.getSelectedItem().toString());
            if (isInserted) {
                Intent newExerciseIntent = new Intent(NewExerciseActivity.this, ExerciseListActivity.class);
                NewExerciseActivity.this.startActivity(newExerciseIntent);
            }
            else {
                Toast.makeText(NewExerciseActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean dbHasName(String name) {
        String duplicateCheck = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE NAME = '" + name + "'";
        return (db.getReadableDatabase().rawQuery(duplicateCheck, null).getCount() > 0);
    }

    private void initSaveListener() {
        Button saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExercise();
            }
        });
    }
}
