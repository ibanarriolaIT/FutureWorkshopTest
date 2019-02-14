package com.futureworkshops.codetest.android.domain.dagger.module;

import com.futureworkshops.codetest.android.domain.dagger.PerActivity;
import com.futureworkshops.codetest.android.domain.dagger.PerFragment;
import com.futureworkshops.codetest.android.domain.repositories.ImportantOperationRepository;
import com.futureworkshops.codetest.android.domain.repositories.LoginRepository;
import com.futureworkshops.codetest.android.domain.usecase.AddFavourite;
import com.futureworkshops.codetest.android.domain.usecase.CheckIsFavourite;
import com.futureworkshops.codetest.android.domain.usecase.GetBreedStats;
import com.futureworkshops.codetest.android.domain.usecase.GetBreedsList;
import com.futureworkshops.codetest.android.domain.usecase.GetFavouriteBreeds;
import com.futureworkshops.codetest.android.domain.usecase.RemoveFavourite;
import com.futureworkshops.codetest.android.presentation.breeds.details.BreedDetailsPresenter;
import com.futureworkshops.codetest.android.presentation.breeds.favorite.FavouriteBreedsPresenter;
import com.futureworkshops.codetest.android.presentation.breeds.list.view.BreedsListPresenter;
import com.futureworkshops.codetest.android.presentation.landing.MainPresenter;
import com.futureworkshops.codetest.android.presentation.login.LoginPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewsModule {
    @Provides
    @PerActivity
    MainPresenter providesMainPresenter(ImportantOperationRepository importantOperationRepository) {
        return new MainPresenter(importantOperationRepository);
    }

    @Provides
    @PerActivity
    LoginPresenter providesLoginPresenter(LoginRepository loginRepository) {
        return new LoginPresenter(loginRepository);
    }

    @Provides
    @PerFragment
    BreedsListPresenter providesBreedsListPresenter(GetBreedsList getBreedsList) {
        return new BreedsListPresenter(getBreedsList);
    }

    @Provides

    @PerFragment
    BreedDetailsPresenter providesBreedDetailsPresenter(GetBreedStats getBreedStats, CheckIsFavourite checkIsFavourite, AddFavourite addFavourite, RemoveFavourite removeFavourite) {
        return new BreedDetailsPresenter(getBreedStats, checkIsFavourite, addFavourite, removeFavourite);
    }

    @Provides
    @PerFragment
    FavouriteBreedsPresenter providesFavouriteBreedsPresenter(GetFavouriteBreeds getFavouriteBreeds) {
        return new FavouriteBreedsPresenter(getFavouriteBreeds);
    }


}
