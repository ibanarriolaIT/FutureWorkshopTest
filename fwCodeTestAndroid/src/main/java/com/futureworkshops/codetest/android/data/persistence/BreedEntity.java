package com.futureworkshops.codetest.android.data.persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;

import com.futureworkshops.codetest.android.domain.model.Breed;


@Entity
public class BreedEntity extends Breed {

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

    protected BreedEntity(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        photoUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(photoUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BreedEntity> CREATOR = new Creator<BreedEntity>() {
        @Override
        public BreedEntity createFromParcel(Parcel in) {
            return new BreedEntity(in);
        }

        @Override
        public BreedEntity[] newArray(int size) {
            return new BreedEntity[size];
        }
    };

    @Override
    public long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public String photoUrl() {
        return photoUrl;
    }

}
