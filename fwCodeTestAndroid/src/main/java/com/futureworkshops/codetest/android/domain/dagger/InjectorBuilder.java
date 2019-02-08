package com.futureworkshops.codetest.android.domain.dagger;

import com.futureworkshops.codetest.android.presentation.breeds.list.view.BreedsListFragment;
import com.futureworkshops.codetest.android.presentation.landing.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class InjectorBuilder {

    @ContributesAndroidInjector(modules = ActivitiesModule.class)
    @PerActivity
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = ActivitiesModule.class)
    @PerFragment
    abstract BreedsListFragment contributeBreedsListFragment();
}
