package com.futureworkshops.codetest.android.data.network;

import com.futureworkshops.codetest.android.data.network.dto.BreedStatsDto;
import com.futureworkshops.codetest.android.data.network.rx.scheduler.SchedulersProvider;
import com.futureworkshops.codetest.android.data.network.rx.transformers.SingleWorkerTransformer;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class XMLRestManager {

    private final RestApi restService;
    private final SchedulersProvider schedulersProvider;

    public XMLRestManager(SchedulersProvider schedulersProvider, Retrofit retrofit) {
        restService = retrofit.create(RestApi.class);
        this.schedulersProvider = schedulersProvider;
    }

    public Single<BreedStatsDto> getStats(long id) {
        return restService.getStats(id)
                .compose(new SingleWorkerTransformer<>(schedulersProvider));
    }
}
