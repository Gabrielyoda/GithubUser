package com.example.mygithubapps.Provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Databasehelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbuserfav";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_USER = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.UserFavorite.TABLE_NAME,
            DatabaseContract.UserFavorite._ID,
            DatabaseContract.UserFavorite.LOGIN,
            DatabaseContract.UserFavorite.TYPE,
            DatabaseContract.UserFavorite.AVATAR_URL
    );


    public Databasehelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.UserFavorite.TABLE_NAME);
        onCreate(db);
    }
}
