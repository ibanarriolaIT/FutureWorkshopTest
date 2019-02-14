package com.futureworkshops.codetest.android.domain.usecase;

import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.domain.repositories.RoomRepository;

import io.reactivex.Completable;

public class RemoveFavourite {

    private final RoomRepository roomRepository;

    public RemoveFavourite(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Completable execute(Breed breed) {
        return roomRepository.findBreedEntity(breed.id())
                .doAfterSuccess(breedEntity -> roomRepository.deleteBreed(breedEntity).subscribe())
                .toCompletable()
                .doOnError(throwable -> new RuntimeException("Breed was already favourite"));
    }
}
