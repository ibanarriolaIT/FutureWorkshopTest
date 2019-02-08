package com.futureworkshops.codetest.android.presentation.breeds.list.view;

import android.annotation.SuppressLint;

import com.futureworkshops.codetest.android.data.network.RestManager;
import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.presentation.common.BasePresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BreedsListPresenter extends BasePresenter {

    RestManager restManager;

    public BreedsListPresenter(RestManager restManager) {
        this.restManager = restManager;
    }

    public void getBreedListItems() {
        restManager.getBreeds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable().flatMapIterable(breedDtos -> breedDtos)
                .map(breedDto -> Breed.builder()
                        .id(breedDto.getId())
                        .description(breedDto.getDescription())
                        .name(breedDto.getName())
                        .photoUrl(breedDto.getPhotoUrl()).build()).toList()
                .subscribe(breeds -> ((View)getView()).onGetBreedListItems(breeds),
                        throwable -> ((View)getView()).onError(throwable));
    }

    public interface View {
        void onGetBreedListItems(List<Breed> breeds);

        void onError(Throwable error);
    }
}
