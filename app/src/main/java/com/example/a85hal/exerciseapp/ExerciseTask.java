package com.example.a85hal.exerciseapp;

import java.util.Random;

public class ExerciseTask {

    private Exercise exercise;
    private int num;
    private int attackPoints;
    private String taskText;

    public ExerciseTask(Exercise ex) {
        exercise = ex;
        Random r = new Random();
        num = r.nextInt((ex.getMax() - ex.getMin()) + 1) + ex.getMin();
        ExerciseType type = ex.getType();
        String name = ex.getName();

        if (type == ExerciseType.REPEATED) {
            taskText = name + " x " + num;
            attackPoints = num;
        }
        else if (type == ExerciseType.TIMED) {
            taskText = name + " for " + num + " min";
            attackPoints = num * 2;
        }
    }

    public String getTaskText() {
        return taskText;
    }

    public int getNum() {
        return num;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public Exercise getExercise() {
        return exercise;
    }
}
