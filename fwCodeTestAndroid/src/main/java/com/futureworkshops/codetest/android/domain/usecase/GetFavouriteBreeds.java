package com.futureworkshops.codetest.android.domain.usecase;

import com.futureworkshops.codetest.android.data.persistence.BreedEntity;
import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.domain.repositories.RoomRepository;

import java.util.List;

import io.reactivex.Single;

public class GetFavouriteBreeds {

    private final RoomRepository roomRepository;

    public GetFavouriteBreeds(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Single<List<Breed>> execute() {
        return roomRepository.getStoredBreeds()
                .toObservable()
                .flatMapIterable(breedEntities -> breedEntities)
                .map(BreedEntity::toBreed).toList();
    }
}
