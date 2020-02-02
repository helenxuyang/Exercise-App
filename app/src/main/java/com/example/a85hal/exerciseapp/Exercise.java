package com.example.a85hal.exerciseapp;

import java.io.Serializable;

public class Exercise implements Serializable {

    protected String name;
    protected int min;
    protected int max;
    protected ExerciseType type;

    public Exercise(String name, int min, int max, ExerciseType type) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public ExerciseType getType() {
        return type;
    }
}
