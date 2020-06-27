package com.example.mygithubapps.Provider;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DatabaseContract {
    public static final String AUTHORITY = "com.example.mygithubapps";
    private static final String SCHEME = "content";

    private DatabaseContract(){}

    public static final class UserFavorite implements BaseColumns {
        public static final String TABLE_NAME = "favorite_user";
        public static final String _ID = "_id";
        public static final String LOGIN = "login";
        public static final String TYPE = "type";
        public static final String AVATAR_URL = "avatar_url";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }


}
