package com.futureworkshops.codetest.android.domain.repositories;

import com.futureworkshops.codetest.android.data.network.RestManager;

import io.reactivex.Completable;

public class ImportantOperationRepository {
    private final RestManager restManager;

    public ImportantOperationRepository(RestManager restManager) {
        this.restManager = restManager;
    }

    public Completable performImportantOperation() {
        return restManager.performImportantOperation();
    }
}
