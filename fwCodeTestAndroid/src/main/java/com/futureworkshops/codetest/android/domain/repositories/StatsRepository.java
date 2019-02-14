package com.futureworkshops.codetest.android.domain.repositories;

import com.futureworkshops.codetest.android.data.network.XMLRestManager;
import com.futureworkshops.codetest.android.data.network.dto.BreedStatsDto;

import io.reactivex.Single;

public class StatsRepository {

    private final XMLRestManager restManager;

    public StatsRepository(XMLRestManager restManager) {
        this.restManager = restManager;
    }

    public Single<BreedStatsDto> getBreedStats(int id) {
        return restManager.getStats(id);
    }
}
