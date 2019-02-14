package com.futureworkshops.codetest.android.data.persistence;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface BreedDao {

    @Query("SELECT * FROM breedentity")
    Single<List<BreedEntity>> getAll();

    @Query("SELECT * FROM breedentity WHERE id = :id")
    Single<BreedEntity> findBreed(long id);

    @Insert
    void insert(BreedEntity breedEntity);

    @Delete
    void delete(BreedEntity breedEntity);

    @Query("SELECT * FROM statsentity WHERE id = :id")
    Single<StatsEntity> findStats(long id);

    @Insert
    void insert(StatsEntity statsEntity);

    @Delete
    void delete(StatsEntity statsEntity);
}
