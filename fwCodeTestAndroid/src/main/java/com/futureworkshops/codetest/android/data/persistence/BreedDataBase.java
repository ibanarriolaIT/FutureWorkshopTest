package com.futureworkshops.codetest.android.data.persistence;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {BreedEntity.class}, version = 1, exportSchema = false)
public abstract class BreedDatabase extends RoomDatabase {
    public abstract BreedDao breedDao();
}
