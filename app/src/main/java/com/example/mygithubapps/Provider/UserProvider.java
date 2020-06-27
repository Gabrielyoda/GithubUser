package com.example.mygithubapps.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import com.example.mygithubapps.Activity.Favorite;

import static com.example.mygithubapps.Provider.DatabaseContract.AUTHORITY;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.CONTENT_URI;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.TABLE_NAME;

public class UserProvider extends ContentProvider {
    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private UserHelper userHelper;

    public UserProvider() {
    }

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, NOTE);

        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", NOTE_ID);
    }

    @Override
    public boolean onCreate() {
        userHelper = UserHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        userHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                cursor = userHelper.queryProvider();
                break;
            case NOTE_ID:
                cursor = userHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
            return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        userHelper.open();
        long added;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                added = userHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new Favorite.DataObserver(new Handler(), getContext()));
        return Uri.parse(CONTENT_URI + "/" + added);
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        userHelper.open();
        int updated;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                updated = userHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new Favorite.DataObserver(new Handler(), getContext()));
        return updated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        userHelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                deleted = userHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new Favorite.DataObserver(new Handler(), getContext()));
        return deleted;
    }

}
