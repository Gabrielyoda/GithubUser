package com.example.mygithubapps.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.example.mygithubapps.Adapter.UserFavoriteAdapter;
import com.example.mygithubapps.Database.FavoriteUser;
import com.example.mygithubapps.R;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.example.mygithubapps.Activity.User.favoriteUserDB;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.CONTENT_URI;

public class Favorite extends AppCompatActivity {
    private UserFavoriteAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout SwipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle(R.string.favorite);

        recyclerView = findViewById(R.id.rv_fav);
        SwipeRefresh = findViewById(R.id.swipe_refresh);
        SwipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getFav();

        SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SwipeRefresh.setRefreshing(false);

                        getFav();

                    }
                },1000);
            }
        });
    }

    private void getFav(){

        List<FavoriteUser> Favorite = favoriteUserDB.FavoriteUserDBDao().getFavoriteData();

        adapter = new UserFavoriteAdapter(Favorite, this);
        recyclerView.setAdapter(adapter);
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadUserAsync(context, (LoadUserFavCallback) context).execute();
        }
    }

    private static class LoadUserAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;

        private LoadUserAsync(Context context, LoadUserFavCallback callback) {
            weakContext = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor favoriteMovies) {
        }
    }

    public interface LoadUserFavCallback {
        void preExecute();
        void postExecute(ArrayList<Favorite> favorites);
    }
}
