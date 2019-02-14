package com.futureworkshops.codetest.android.data.persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;

import com.futureworkshops.codetest.android.domain.model.Breed;


@Entity
public class BreedEntity {

    @PrimaryKey
    public long id;
    @ColumnInfo
    public String name;
    @ColumnInfo
    public String description;
    @ColumnInfo
    public String photoUrl;

    public BreedEntity(long id, String name, String description, String photoUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photoUrl = photoUrl;
    }

    public BreedEntity(Breed breed) {
        id = breed.id();
        name = breed.name();
        description = breed.description();
        photoUrl = breed.photoUrl();
    }

    public Breed toBreed() {
        return Breed.builder()
                .id(id)
                .name(name)
                .description(description)
                .photoUrl(photoUrl).build();
    }

}
