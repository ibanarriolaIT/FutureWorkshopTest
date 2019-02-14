package com.futureworkshops.codetest.android.domain.usecase;

import com.futureworkshops.codetest.android.data.persistence.StatsEntity;
import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.domain.model.BreedStats;
import com.futureworkshops.codetest.android.domain.repositories.RoomRepository;

import io.reactivex.Completable;

public class RemoveFavourite {

    private final RoomRepository roomRepository;

    public RemoveFavourite(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Completable execute(Breed breed, BreedStats breedStats) {
        return roomRepository.findBreedEntity(breed.id())
                .doAfterSuccess(breedEntity -> Completable.mergeArray(roomRepository.deleteBreed(breedEntity),
                        roomRepository.deleteStats(new StatsEntity(breed.id(), breedStats))).subscribe())
                .toCompletable()
                .doOnError(throwable -> new RuntimeException("Breed was already favourite"));
    }
}
