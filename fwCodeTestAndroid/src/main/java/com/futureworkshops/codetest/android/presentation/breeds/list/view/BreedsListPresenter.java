package com.futureworkshops.codetest.android.presentation.breeds.list.view;

import com.futureworkshops.codetest.android.domain.model.Breed;
import com.futureworkshops.codetest.android.domain.usecase.GetBreedsList;
import com.futureworkshops.codetest.android.presentation.common.BasePresenter;
import com.futureworkshops.codetest.android.presentation.common.BaseView;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class BreedsListPresenter extends BasePresenter {

    private final GetBreedsList getBreedsList;

    public BreedsListPresenter(GetBreedsList getBreedsList) {
        this.getBreedsList = getBreedsList;
    }

    public void getBreedListItems() {
        Disposable disposable = getBreedsList.execute()
                .subscribe(breeds -> ((View) getView()).onGetBreedListItems(breeds),
                        throwable -> ((View) getView()).onError(throwable));
        addSubscription(disposable);
    }

    public interface View extends BaseView {
        void onGetBreedListItems(List<Breed> breeds);

        void onError(Throwable error);
    }
}
