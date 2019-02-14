package com.futureworkshops.codetest.android.domain.usecase;

import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.domain.repositories.BreedRepository;

import java.util.List;

import io.reactivex.Single;

public class GetBreedsList {
    private final BreedRepository breedRepository;

    public GetBreedsList(BreedRepository breedRepository) {
        this.breedRepository = breedRepository;
    }

    public Single<List<Breed>> execute() {
        return breedRepository.getBreeds().toObservable().flatMapIterable(breedDtos -> breedDtos)
                .map(breedDto -> Breed.builder()
                        .id(breedDto.getId())
                        .description(breedDto.getDescription())
                        .name(breedDto.getName())
                        .photoUrl(breedDto.getPhotoUrl()).build()).toList();
    }

}
