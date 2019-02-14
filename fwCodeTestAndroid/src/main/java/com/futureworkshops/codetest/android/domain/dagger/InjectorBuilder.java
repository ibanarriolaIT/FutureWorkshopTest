package com.futureworkshops.codetest.android.domain.dagger;

import com.futureworkshops.codetest.android.domain.dagger.module.ViewsModule;
import com.futureworkshops.codetest.android.presentation.breeds.details.BreedDetailsFragment;
import com.futureworkshops.codetest.android.presentation.breeds.favorite.FavouriteBreedsFragment;
import com.futureworkshops.codetest.android.presentation.breeds.list.view.BreedsListFragment;
import com.futureworkshops.codetest.android.presentation.landing.MainActivity;
import com.futureworkshops.codetest.android.presentation.login.LoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class InjectorBuilder {

    @ContributesAndroidInjector(modules = ViewsModule.class)
    @PerActivity
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = ViewsModule.class)
    @PerActivity
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector(modules = ViewsModule.class)
    @PerFragment
    abstract BreedsListFragment contributeBreedsListFragment();

    @ContributesAndroidInjector(modules = ViewsModule.class)
    @PerFragment
    abstract BreedDetailsFragment contributeBreedDetailsFragment();

    @ContributesAndroidInjector(modules = ViewsModule.class)
    @PerFragment
    abstract FavouriteBreedsFragment contributeFavoriteBreedsFragment();
}
