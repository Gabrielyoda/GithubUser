package com.example.mygithubapps.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteUser.class}, version = 2)
public abstract class FavoriteUserDB extends RoomDatabase {
    public abstract FavoriteUserDAO FavoriteUserDBDao();
}
