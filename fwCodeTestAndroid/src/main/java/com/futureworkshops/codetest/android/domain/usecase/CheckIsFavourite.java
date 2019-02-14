package com.futureworkshops.codetest.android.domain.usecase;

import android.annotation.SuppressLint;

import com.futureworkshops.codetest.android.domain.repositories.RoomRepository;

import io.reactivex.Single;

public class CheckIsFavourite {

    private final RoomRepository roomRepository;

    public CheckIsFavourite(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @SuppressLint("CheckResult")
    public Single<Boolean> execute(long id) {
        return roomRepository.findBreedEntity(id).map(breedEntity -> true)
                .onErrorReturn(throwable -> false);
    }
}
