package com.futureworkshops.codetest.android.domain.usecase;

import com.futureworkshops.codetest.android.domain.model.BreedStats;
import com.futureworkshops.codetest.android.domain.repositories.StatsRepository;

import io.reactivex.Single;

public class GetBreedStats {

    private final StatsRepository statsRepository;

    public GetBreedStats(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    public Single<BreedStats> execute(int id) {
        return statsRepository.getBreedStats(id).map(breedStatsDto -> new BreedStats(
                breedStatsDto.getAdaptability(),
                breedStatsDto.getFriendliness(),
                breedStatsDto.getGrooming(),
                breedStatsDto.getTrainability(),
                breedStatsDto.getExerciseNeeds()));
    }
}
