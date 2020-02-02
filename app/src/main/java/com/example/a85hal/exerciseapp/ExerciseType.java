package com.example.a85hal.exerciseapp;

public enum ExerciseType {
    REPEATED, TIMED;

    public static ExerciseType getTypeFromString(String type) {
        if (type.equals("Repeated")) return REPEATED;
        else return TIMED;
    }
}


