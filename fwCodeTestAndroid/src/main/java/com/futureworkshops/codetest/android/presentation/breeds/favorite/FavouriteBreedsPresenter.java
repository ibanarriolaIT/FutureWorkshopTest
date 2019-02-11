package com.futureworkshops.codetest.android.presentation.breeds.favorite;

import android.annotation.SuppressLint;
import android.content.Context;

import com.futureworkshops.codetest.android.data.persistence.BreedEntity;
import com.futureworkshops.codetest.android.data.persistence.DatabaseClient;
import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.presentation.common.BasePresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FavouriteBreedsPresenter extends BasePresenter {

    DatabaseClient databaseClient;

    public FavouriteBreedsPresenter(Context context) {
        this.databaseClient = DatabaseClient.getInstance(context);
    }


    public void getFavourites() {
        databaseClient.getBreedDatabase().breedDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable().flatMapIterable(breedEntities -> breedEntities)
                .map(breedEntity -> Breed.builder()
                        .id(breedEntity.id())
                        .description(breedEntity.description())
                        .name(breedEntity.name())
                        .photoUrl(breedEntity.photoUrl()).build()).toList()
                .subscribe(breedEntities -> ((View)getView()).onGetFavourites(breedEntities));
    }

    public interface View {
        void onGetFavourites(List<Breed> breedEntity);
    }
}
