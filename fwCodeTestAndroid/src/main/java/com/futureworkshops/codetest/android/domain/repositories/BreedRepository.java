package com.futureworkshops.codetest.android.domain.repositories;

import com.futureworkshops.codetest.android.data.network.RestManager;
import com.futureworkshops.codetest.android.data.network.dto.BreedDto;

import java.util.List;

import io.reactivex.Single;

public class BreedRepository {

    private final RestManager restManager;

    public BreedRepository(RestManager restManager) {
        this.restManager = restManager;
    }

    public Single<List<BreedDto>> getBreeds() {
        return restManager.getBreeds();
    }

}
