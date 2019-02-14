package com.futureworkshops.codetest.android.domain.repositories;

import android.annotation.SuppressLint;

import com.futureworkshops.codetest.android.data.network.rx.scheduler.SchedulersProvider;
import com.futureworkshops.codetest.android.data.network.rx.transformers.CompletableWorkerTransformer;
import com.futureworkshops.codetest.android.data.network.rx.transformers.SingleWorkerTransformer;
import com.futureworkshops.codetest.android.data.persistence.BreedDao;
import com.futureworkshops.codetest.android.data.persistence.BreedEntity;
import com.futureworkshops.codetest.android.data.persistence.DatabaseClient;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class RoomRepository {

    private final BreedDao roomDatabase;
    private final SchedulersProvider schedulersProvider;

    public RoomRepository(DatabaseClient databaseClient, SchedulersProvider schedulersProvider) {
        this.roomDatabase = databaseClient.getBreedDatabase().breedDao();
        this.schedulersProvider = schedulersProvider;
    }

    public Single<List<BreedEntity>> getStoredBreeds() {
        return roomDatabase.getAll()
                .compose(new SingleWorkerTransformer<>(schedulersProvider));
    }

    public Single<BreedEntity> findBreedEntity(long id) {
        return roomDatabase.findBreed(id)
                .compose(new SingleWorkerTransformer<>(schedulersProvider));
    }

    public Completable insertBreed(BreedEntity breedEntity) {
        return Completable.fromAction(() -> roomDatabase.insert(breedEntity))
                .compose(new CompletableWorkerTransformer(schedulersProvider));
    }

    public Completable deleteBreed(BreedEntity breedEntity) {
        return Completable.fromAction(() -> roomDatabase.delete(breedEntity))
                .compose(new CompletableWorkerTransformer(schedulersProvider));
    }
}
