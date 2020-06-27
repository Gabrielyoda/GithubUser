package com.example.mygithubapps.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteUserDAO {
    @Insert
    public void addData(FavoriteUser favoriteUser);

    @Query("SELECT * FROM favorite_user")
    public List<FavoriteUser> getFavoriteData();

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_user WHERE id=:id)")
    public int isFavorite(int id);

    @Delete
    public void delete(FavoriteUser favoriteUser);
}
