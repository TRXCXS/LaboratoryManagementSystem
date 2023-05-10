package com.example.backend.utils.enumClasses.model;

import org.jetbrains.annotations.NotNull;

public enum Season {
    SPRING(2), // 春季学期-下学期
    FALL(1); // 秋季学期-上学期
    private int value;

    Season(int value) {
        this.value = value;
    }

    @org.jetbrains.annotations.Contract(pure = true)
    public static int getSeasonValue(@NotNull Season season) {
        switch (season) {
            case SPRING:
                return 2;
            case FALL:
                return 1;
            default:
                throw new IllegalArgumentException("Invalid season: " + season);
        }
    }

    public int getValue() {
        return value;
    }
}