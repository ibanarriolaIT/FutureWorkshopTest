package com.futureworkshops.codetest.android.domain.model;

public class BreedStats {
    private int adaptability;
    private int friendliness;
    private int grooming;
    private int trainability;
    private int exercise_needs;

    public BreedStats(int adaptability, int friendliness, int grooming, int trainability, int exercise_needs) {
        this.adaptability = adaptability;
        this.friendliness = friendliness;
        this.grooming = grooming;
        this.trainability = trainability;
        this.exercise_needs = exercise_needs;
    }

    public int getAdaptability() {
        return adaptability;
    }

    public int getFriendliness() {
        return friendliness;
    }

    public int getGrooming() {
        return grooming;
    }

    public int getTrainability() {
        return trainability;
    }

    public int getExercise_needs() {
        return exercise_needs;
    }
}
