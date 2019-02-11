package com.futureworkshops.codetest.android.domain.dagger;

import android.content.Context;

import com.futureworkshops.codetest.android.data.network.RestManager;
import com.futureworkshops.codetest.android.domain.repositories.BreedRepository;
import com.futureworkshops.codetest.android.domain.repositories.ImportantOperationRepository;
import com.futureworkshops.codetest.android.domain.repositories.LoginRepository;
import com.futureworkshops.codetest.android.domain.repositories.StatsRepository;
import com.futureworkshops.codetest.android.presentation.breeds.details.BreedDetailsPresenter;
import com.futureworkshops.codetest.android.presentation.breeds.favorite.FavouriteBreedsPresenter;
import com.futureworkshops.codetest.android.presentation.breeds.list.view.BreedsListPresenter;
import com.futureworkshops.codetest.android.presentation.landing.MainPresenter;
import com.futureworkshops.codetest.android.presentation.login.LoginPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivitiesModule {
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
    BreedsListPresenter providesBreedsListPresenter(BreedRepository breedRepository) {
        return new BreedsListPresenter(breedRepository);
    }

    @Provides

    @PerFragment
    BreedDetailsPresenter providesBreedDetailsPresenter(Context context, StatsRepository statsRepository) {
        return new BreedDetailsPresenter(context, statsRepository);
    }

    @Provides
    @PerFragment
    FavouriteBreedsPresenter providesFavouriteBreedsPresenter(Context context) {
        return new FavouriteBreedsPresenter(context);
    }


}
