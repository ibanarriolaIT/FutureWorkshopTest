package com.futureworkshops.codetest.android.domain.dagger;

import com.futureworkshops.codetest.android.data.network.RestManager;
import com.futureworkshops.codetest.android.presentation.breeds.list.view.BreedsListPresenter;
import com.futureworkshops.codetest.android.presentation.landing.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivitiesModule {
    @Provides
    @PerActivity
    MainPresenter providesMainPresenter(RestManager restManager) {
        return new MainPresenter(restManager);
    }

    @Provides
    @PerFragment
    BreedsListPresenter providesBreedsListPresenter(RestManager restManager) {
        return new BreedsListPresenter(restManager);
    }
}
