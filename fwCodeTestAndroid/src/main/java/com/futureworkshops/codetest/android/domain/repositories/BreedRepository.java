package com.futureworkshops.codetest.android.domain.repositories;

import com.futureworkshops.codetest.android.data.network.RestManager;
import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.presentation.utils.ErrorHandler;

import java.util.List;

import io.reactivex.Single;

public class BreedRepository {

    private final RestManager restManager;

    public BreedRepository(RestManager restManager) {
        this.restManager = restManager;
    }

    public Single<List<Breed>> getBreeds() {
        return restManager.getBreeds()
                .toObservable().flatMapIterable(breedDtos -> breedDtos)
                .map(breedDto -> Breed.builder()
                        .id(breedDto.getId())
                        .description(breedDto.getDescription())
                        .name(breedDto.getName())
                        .photoUrl(breedDto.getPhotoUrl()).build()).toList()
                .doOnError(ErrorHandler::getErrorMessage);
    }

}
