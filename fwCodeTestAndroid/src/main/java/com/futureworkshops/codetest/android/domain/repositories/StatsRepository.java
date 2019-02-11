package com.futureworkshops.codetest.android.domain.repositories;

import com.futureworkshops.codetest.android.data.network.XMLRestManager;
import com.futureworkshops.codetest.android.domain.model.BreedStats;

import io.reactivex.Single;

public class StatsRepository {

    private final XMLRestManager restManager;

    public StatsRepository(XMLRestManager restManager) {
        this.restManager = restManager;
    }

    public Single<BreedStats> getBreedStats(int id) {
        return restManager.getStats(id).map(breedStatsDto -> new BreedStats(
                breedStatsDto.getAdaptability(),
                breedStatsDto.getFriendliness(),
                breedStatsDto.getGrooming(),
                breedStatsDto.getTrainability(),
                breedStatsDto.getExerciseNeeds()));
    }
}
