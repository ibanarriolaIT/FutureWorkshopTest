package com.futureworkshops.codetest.android.domain.usecase;

import com.futureworkshops.codetest.android.data.persistence.BreedEntity;
import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.domain.repositories.RoomRepository;

import io.reactivex.Completable;

public class AddFavourite {

    private final RoomRepository roomRepository;

    public AddFavourite(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Completable execute(Breed breed) {
        return roomRepository.findBreedEntity((int) breed.id())
                .doOnError(throwable -> roomRepository.insertBreed(new BreedEntity(breed)).subscribe())
                .doAfterSuccess(breedEntity -> new RuntimeException("Breed was already favourite"))
                .toCompletable().onErrorComplete();
    }
}
