package com.futureworkshops.codetest.android.presentation.breeds.favorite;

import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.domain.usecase.GetFavouriteBreeds;
import com.futureworkshops.codetest.android.presentation.common.BasePresenter;
import com.futureworkshops.codetest.android.presentation.common.BaseView;

import java.util.List;

import io.reactivex.disposables.Disposable;


public class FavouriteBreedsPresenter extends BasePresenter {

    private final GetFavouriteBreeds getFavouriteBreeds;

    public FavouriteBreedsPresenter(GetFavouriteBreeds getFavouriteBreeds) {
        this.getFavouriteBreeds = getFavouriteBreeds;
    }

    public void getFavourites() {
        Disposable disposable = getFavouriteBreeds.execute()
                .subscribe(breeds -> ((View) getView()).onGetFavourites(breeds));
        addSubscription(disposable);
    }

    public interface View extends BaseView {
        void onGetFavourites(List<Breed> breedEntity);
    }
}
