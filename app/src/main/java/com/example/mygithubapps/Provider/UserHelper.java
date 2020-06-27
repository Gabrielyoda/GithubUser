package com.example.mygithubapps.Provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mygithubapps.Database.FavoriteUser;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.AVATAR_URL;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.LOGIN;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.TABLE_NAME;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.TYPE;

public class UserHelper {

    private static final String DATABASE_TABLE = TABLE_NAME;
    private final Databasehelper databasehelper;
    private static UserHelper  INSTANCE;

    private SQLiteDatabase database;

    public UserHelper(Context context) {
        databasehelper = new Databasehelper(context);
    }

    public static UserHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databasehelper.getWritableDatabase();
    }

    public void close() {
        databasehelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<FavoriteUser> query() {
        ArrayList<FavoriteUser> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null, _ID + " DESC"
                , null);
        cursor.moveToFirst();
        FavoriteUser favoriteUser;
        if (cursor.getCount() > 0) {
            do {
                favoriteUser = new FavoriteUser();
                favoriteUser.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favoriteUser.setLogin(cursor.getString(cursor.getColumnIndexOrThrow(LOGIN)));
                favoriteUser.setType(cursor.getString(cursor.getColumnIndexOrThrow(TYPE)));
                favoriteUser.setAvatar_url(cursor.getString(cursor.getColumnIndexOrThrow(AVATAR_URL)));

                arrayList.add(favoriteUser);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }

}
