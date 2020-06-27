package com.example.consumerapp;

import android.database.Cursor;

import java.util.ArrayList;


import static android.provider.BaseColumns._ID;
import static com.example.consumerapp.DatabaseContract.UserFavorite.AVATAR_URL;
import static com.example.consumerapp.DatabaseContract.UserFavorite.LOGIN;
import static com.example.consumerapp.DatabaseContract.UserFavorite.TYPE;

public class MappingHelper {

    public static ArrayList<FavoriteUser> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<FavoriteUser> favoriteUsers = new ArrayList<>();
        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(_ID));
            String login = notesCursor.getString(notesCursor.getColumnIndexOrThrow(LOGIN));
            String type = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TYPE));
            String avatar_url = notesCursor.getString(notesCursor.getColumnIndexOrThrow(AVATAR_URL));
            favoriteUsers.add(new FavoriteUser(id, login, type, avatar_url));
        }
        return favoriteUsers;
    }
}
