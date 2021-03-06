/*
 * Copyright (c) 2018 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.codetest.android.data.network;

import android.support.annotation.NonNull;

import com.futureworkshops.codetest.android.data.network.dto.BreedDto;
import com.futureworkshops.codetest.android.data.network.dto.BreedsListDto;
import com.futureworkshops.codetest.android.data.network.rx.scheduler.SchedulersProvider;
import com.futureworkshops.codetest.android.data.network.rx.transformers.CompletableWorkerTransformer;
import com.futureworkshops.codetest.android.data.network.rx.transformers.SingleWorkerTransformer;
import com.futureworkshops.codetest.android.domain.model.Login;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Retrofit;

public class RestManager {

    private final RestApi restService;
    private final SchedulersProvider schedulersProvider;

    public RestManager(@NonNull SchedulersProvider schedulersProvider,
                       @NonNull Retrofit retrofit) {

        this.schedulersProvider = schedulersProvider;

        restService = retrofit.create(RestApi.class);
    }


    public Single<List<BreedDto>> getBreeds() {
        return restService.getBreeds()
                .map(BreedsListDto::getBreeds)
                .compose(new SingleWorkerTransformer<>(schedulersProvider));
    }


    public Completable performImportantOperation() {
        return restService.performImportantOperation()
                .compose(new CompletableWorkerTransformer(schedulersProvider));
    }

    public Single<Login> login(String user, String pass) {
        return restService.login(user, pass)
                .compose(new SingleWorkerTransformer<>(schedulersProvider));
    }

}
