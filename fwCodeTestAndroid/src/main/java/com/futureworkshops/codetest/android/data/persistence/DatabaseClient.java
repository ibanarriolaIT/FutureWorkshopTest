package com.futureworkshops.codetest.android.data.persistence;

import android.arch.persistence.room.Room;
import android.content.Context;


public class DatabaseClient {

    private BreedDatabase breedDatabase;

    public DatabaseClient(Context context) {
        breedDatabase = Room.databaseBuilder(context, BreedDatabase.class, "DataBase").build();
    }

    public BreedDatabase getBreedDatabase() {
        return breedDatabase;
    }

}
