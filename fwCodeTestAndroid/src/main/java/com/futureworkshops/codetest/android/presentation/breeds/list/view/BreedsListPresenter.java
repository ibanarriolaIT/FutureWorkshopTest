package com.futureworkshops.codetest.android.presentation.breeds.list.view;

import android.annotation.SuppressLint;

import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.domain.repositories.BreedRepository;
import com.futureworkshops.codetest.android.presentation.common.BasePresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BreedsListPresenter extends BasePresenter {

    BreedRepository breedRepository;

    public BreedsListPresenter(BreedRepository breedRepository) {
        this.breedRepository = breedRepository;
    }

    @SuppressLint("CheckResult")
    public void getBreedListItems() {
        breedRepository.getBreeds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(breeds -> ((View)getView()).onGetBreedListItems(breeds),
                        throwable -> ((View)getView()).onError(throwable));
    }

    public interface View {
        void onGetBreedListItems(List<Breed> breeds);

        void onError(Throwable error);
    }
}
