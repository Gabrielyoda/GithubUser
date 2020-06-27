package com.example.mygithubapps.Widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.example.mygithubapps.Database.FavoriteUser;
import com.example.mygithubapps.R;

import static com.example.mygithubapps.Activity.User.favoriteUserDB;

import java.util.List;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
    private List<FavoriteUser> favoriteUsers;
    private final Context mContext;
    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }


    @Override
    public void onCreate() {
        favoriteUsers = favoriteUserDB.FavoriteUserDBDao().getFavoriteData();
    }

    @Override
    public void onDataSetChanged() {
        favoriteUsers = favoriteUserDB.FavoriteUserDBDao().getFavoriteData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return favoriteUsers.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);

        if (favoriteUsers.size() > 0) {
            FavoriteUser favoriteUser = favoriteUsers.get(position);

            try {
                Bitmap bitmap = Glide.with(mContext)
                        .asBitmap()
                        .load(favoriteUser.getAvatar_url())
                        .submit(512, 512)
                        .get();

                remoteViews.setImageViewBitmap(R.id.img_widget, bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Bundle extras = new Bundle();
            extras.putString(ImageBannerWidget.EXTRA_ITEM, favoriteUser.getLogin());
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            remoteViews.setOnClickFillInIntent(R.id.img_widget, fillInIntent);

            return remoteViews;
        } else {
            return null;
        }
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
