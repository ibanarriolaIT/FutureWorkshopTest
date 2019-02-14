package com.futureworkshops.codetest.android.domain.usecase;

import com.futureworkshops.codetest.android.domain.repositories.RoomRepository;

import io.reactivex.Single;

public class CheckIsFavourite {

    private final RoomRepository roomRepository;

    public CheckIsFavourite(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Single<Boolean> execute(long id) {
        return roomRepository.findBreedEntity(id).map(breedEntity -> true)
                .onErrorReturn(throwable -> false);
    }
}
