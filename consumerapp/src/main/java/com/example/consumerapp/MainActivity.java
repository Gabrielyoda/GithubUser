package com.example.consumerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.consumerapp.DatabaseContract.UserFavorite.CONTENT_URI;
import static com.example.consumerapp.MappingHelper.mapCursorToArrayList;

public class MainActivity extends AppCompatActivity implements LoadUser {
    private UserFavoriteAdapter adapter;
    private DataObserver myObserver;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Consumer Apps");

        recyclerView = findViewById(R.id.rv_fav);
        adapter = new UserFavoriteAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);
        new getData(this,  this).execute();
    }

    @Override
    public void postExecute(Cursor notes) {
        ArrayList<FavoriteUser> listFavorite = mapCursorToArrayList(notes);
        if (listFavorite.size() > 0) {
            adapter.setFavoriteUser(listFavorite);
        } else {
            Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
            adapter.setFavoriteUser(new ArrayList<FavoriteUser>());
        }
    }

    static class DataObserver extends ContentObserver {

        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new getData(context, (MainActivity) context).execute();
        }
    }

    private static class getData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadUser> weakCallback;


        private getData(Context context, LoadUser callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(data);
            weakCallback.get().postExecute(data);
        }

    }


}
interface LoadUser{
    void postExecute(Cursor notes);
}
