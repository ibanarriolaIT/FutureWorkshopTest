package com.futureworkshops.codetest.android.presentation.breeds.details;

import android.annotation.SuppressLint;
import android.content.Context;

import com.futureworkshops.codetest.android.data.persistence.BreedEntity;
import com.futureworkshops.codetest.android.data.persistence.DatabaseClient;
import com.futureworkshops.codetest.android.domain.model.BreedStats;
import com.futureworkshops.codetest.android.domain.repositories.BreedRepository;
import com.futureworkshops.codetest.android.domain.repositories.StatsRepository;
import com.futureworkshops.codetest.android.presentation.common.BasePresenter;
import com.futureworkshops.codetest.android.presentation.utils.ErrorHandler;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BreedDetailsPresenter extends BasePresenter {

    DatabaseClient databaseClient;
    StatsRepository statsRepository;

    public BreedDetailsPresenter(Context context, StatsRepository statsRepository) {
        this.databaseClient = DatabaseClient.getInstance(context);
        this.statsRepository = statsRepository;
    }

    @SuppressLint("CheckResult")
    public void checkIsFavourite(long id) {
        databaseClient.getBreedDatabase().breedDao().findBreed(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(breedEntity -> ((View) getView()).setFavourite(true),
                        throwable -> ((View) getView()).setFavourite(false));
    }
    @SuppressLint("CheckResult")
    public void addToFavourites(BreedEntity breedEntity) {
        Completable.fromAction(() -> databaseClient.getBreedDatabase().breedDao().insert(breedEntity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> ((View) getView()).setFavourite(true));
    }
    @SuppressLint("CheckResult")
    public void removeFromFavourites(BreedEntity breedEntity) {
        Completable.fromAction(() -> databaseClient.getBreedDatabase().breedDao().delete(breedEntity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> ((View) getView()).setFavourite(false));
    }

    @SuppressLint("CheckResult")
    public void checkBreedDetails(int id) {
        statsRepository.getBreedStats(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(breedStats -> ((View)getView()).onStatsSuccess(breedStats),
                        throwable -> ((View)getView()).onStatsError(ErrorHandler.getErrorMessage(throwable)));
    }

    public interface View {
        void setFavourite(boolean isFavourite);
        void onStatsSuccess(BreedStats breedStats);
        void onStatsError(String error);
    }

}
