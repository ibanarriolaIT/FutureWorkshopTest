package com.futureworkshops.codetest.android.presentation.breeds.details;

import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.domain.model.BreedStats;
import com.futureworkshops.codetest.android.domain.usecase.AddFavourite;
import com.futureworkshops.codetest.android.domain.usecase.CheckIsFavourite;
import com.futureworkshops.codetest.android.domain.usecase.GetBreedStats;
import com.futureworkshops.codetest.android.domain.usecase.RemoveFavourite;
import com.futureworkshops.codetest.android.presentation.common.BasePresenter;
import com.futureworkshops.codetest.android.presentation.common.BaseView;
import com.futureworkshops.codetest.android.presentation.utils.ErrorHandler;

import io.reactivex.disposables.Disposable;

public class BreedDetailsPresenter extends BasePresenter {

    private final GetBreedStats getBreedStats;
    private final CheckIsFavourite checkIsFavourite;
    private final AddFavourite addFavourite;
    private final RemoveFavourite removeFavourite;

    public BreedDetailsPresenter(GetBreedStats getBreedStats, CheckIsFavourite checkIsFavourite, AddFavourite addFavourite, RemoveFavourite removeFavourite) {
        this.getBreedStats = getBreedStats;
        this.checkIsFavourite = checkIsFavourite;
        this.addFavourite = addFavourite;
        this.removeFavourite = removeFavourite;
    }

    public void checkIsFavourite(long id) {
        Disposable disposable = checkIsFavourite.execute(id)
                .subscribe(isFavourite -> ((View) getView()).setFavourite(isFavourite, false),
                        throwable -> ((View) getView()).onError(ErrorHandler.getErrorMessage(throwable)));
        addSubscription(disposable);
    }

    public void addToFavourites(Breed breed) {
        Disposable disposable = addFavourite.execute(breed)
                .subscribe(() -> ((View) getView()).setFavourite(true, true),
                        throwable -> ((View) getView()).onError(ErrorHandler.getErrorMessage(throwable)));
        addSubscription(disposable);
    }

    public void removeFromFavourites(Breed breed) {
        Disposable disposable = removeFavourite.execute(breed)
                .subscribe(() -> ((View) getView()).setFavourite(false, true),
                        throwable -> ((View) getView()).onError(ErrorHandler.getErrorMessage(throwable)));
        addSubscription(disposable);
    }

    public void checkBreedDetails(long id) {
        Disposable disposable = getBreedStats.execute((int) id)
                .subscribe(breedStats -> ((View) getView()).onStatsSuccess(breedStats),
                        throwable -> ((View) getView()).onError(ErrorHandler.getErrorMessage(throwable)));
        addSubscription(disposable);
    }

    public interface View extends BaseView {
        void setFavourite(boolean isFavourite, boolean informUser);

        void onStatsSuccess(BreedStats breedStats);

        void onError(String error);
    }

}
