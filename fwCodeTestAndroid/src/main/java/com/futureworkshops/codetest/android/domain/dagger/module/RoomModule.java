package com.futureworkshops.codetest.android.domain.dagger.module;

import android.content.Context;

import com.futureworkshops.codetest.android.data.network.rx.scheduler.SchedulersProvider;
import com.futureworkshops.codetest.android.data.persistence.DatabaseClient;
import com.futureworkshops.codetest.android.domain.repositories.RoomRepository;
import com.futureworkshops.codetest.android.domain.usecase.AddFavourite;
import com.futureworkshops.codetest.android.domain.usecase.CheckIsFavourite;
import com.futureworkshops.codetest.android.domain.usecase.GetFavouriteBreeds;
import com.futureworkshops.codetest.android.domain.usecase.RemoveFavourite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    @Provides
    @Singleton
    DatabaseClient providesDatabaseClient(Context context) {
        return new DatabaseClient(context);
    }

    @Provides
    @Singleton
    RoomRepository providesRoomRepository(DatabaseClient databaseClient, SchedulersProvider schedulersProvider) {
        return new RoomRepository(databaseClient, schedulersProvider);
    }

    @Provides
    @Singleton
    CheckIsFavourite providesCheckIsFavourite(RoomRepository roomRepository) {
        return new CheckIsFavourite(roomRepository);
    }

    @Provides
    @Singleton
    AddFavourite providesAddFavourite(RoomRepository roomRepository) {
        return new AddFavourite(roomRepository);
    }

    @Provides
    @Singleton
    RemoveFavourite providesRemoveFavourite(RoomRepository roomRepository) {
        return new RemoveFavourite(roomRepository);
    }

    @Provides
    @Singleton
    GetFavouriteBreeds providesGetFavouriteBreeds(RoomRepository roomRepository) {
        return new GetFavouriteBreeds(roomRepository);
    }
}
