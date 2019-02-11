package com.futureworkshops.codetest.android.data.persistence;

import android.arch.persistence.room.Room;
import android.content.Context;


public class DatabaseClient {

    private Context context;
    private static DatabaseClient instance;

    private BreedDatabase breedDatabase;

    private DatabaseClient(Context context){
        this.context = context;
        breedDatabase = Room.databaseBuilder(context, BreedDatabase.class, "DataBase").build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    public BreedDatabase getBreedDatabase() {
        return breedDatabase;
    }

}
