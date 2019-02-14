package com.futureworkshops.codetest.android.domain.usecase;

import com.futureworkshops.codetest.android.data.persistence.StatsEntity;
import com.futureworkshops.codetest.android.domain.model.BreedStats;
import com.futureworkshops.codetest.android.domain.repositories.RoomRepository;

import io.reactivex.Single;

public class GetFavouriteStats {
    private final RoomRepository roomRepository;

    public GetFavouriteStats(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Single<BreedStats> execute(long id) {
        return roomRepository.findStatsEntity(id).map(StatsEntity::toBreedStats);
    }
}
