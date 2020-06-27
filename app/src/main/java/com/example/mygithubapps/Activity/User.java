package com.example.mygithubapps.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mygithubapps.Database.FavoriteUser;
import com.example.mygithubapps.Database.FavoriteUserDB;
import com.example.mygithubapps.Model.Item;
import com.example.mygithubapps.R;
import com.example.mygithubapps.Widget.ImageBannerWidget;
import com.example.mygithubapps.fragment.FollowerFragment;
import com.example.mygithubapps.fragment.FollowingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.AVATAR_URL;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.CONTENT_URI;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.LOGIN;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.TYPE;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite._ID;

public class User extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_USER = "extra_user";
    public static FavoriteUserDB favoriteUserDB;
    TextView nama,type;
    ImageView phto;
    Button set_favorite,cancel_favorite;
    private int id;
    String login,typestr,avatar_url;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            Item data = getIntent().getParcelableExtra(EXTRA_USER);

            switch (item.getItemId()) {
                case R.id.navigation_follower:
                    String username = data.getLogin();
                    Bundle api = new Bundle();
                    api.putString(FollowerFragment.KEY_ACTIVITY, username);
                    fragment = new FollowerFragment();
                    fragment.setArguments(api);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.navigation_following:
                    String usernameapi = data.getLogin();
                    Bundle api2 = new Bundle();
                    api2.putString(FollowerFragment.KEY_ACTIVITY, usernameapi);
                    fragment = new FollowingFragment();
                    fragment.setArguments(api2);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        nama = findViewById(R.id.textView_nama);
        type = findViewById(R.id.textView_type);
        phto = findViewById(R.id.photo_user_detail);
        set_favorite = findViewById(R.id.btn_set_favorite);
        cancel_favorite = findViewById(R.id.btn_set_cancel);

        set_favorite.setOnClickListener(this);
        cancel_favorite.setOnClickListener(this);

        Item data = getIntent().getParcelableExtra(EXTRA_USER);

        id = data.getId();
        login = data.getLogin();
        typestr = data.getType();
        avatar_url = data.getAvatar_url();

        nama.setText(login);
        type.setText(typestr);
        Glide.with(this)
                .load(avatar_url)
                .into(phto);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState==null){
            navigation.setSelectedItemId(R.id.navigation_follower);
        }

        favoriteUserDB = Room.databaseBuilder(getApplicationContext(),
                FavoriteUserDB.class, "user_fav").allowMainThreadQueries().build();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_bahasa) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        else if (item.getItemId() == R.id.action_reminder){
            Intent mIntent = new Intent(this, Reminder.class);
            startActivity(mIntent);
        }
        else if (item.getItemId() == R.id.action_favorite){
            Intent mIntent = new Intent(this, Favorite.class);
            startActivity(mIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        FavoriteUser FavUser =new FavoriteUser();

        FavUser.setId(id);
        FavUser.setLogin(login);
        FavUser.setAvatar_url(avatar_url);
        FavUser.setType(typestr);

        ContentValues values = new ContentValues();
        values.put(_ID, id);
        values.put(LOGIN, login);
        values.put(TYPE, typestr);
        values.put(AVATAR_URL, avatar_url);

        if (favoriteUserDB.FavoriteUserDBDao().isFavorite(id)== 1){
            if(v == cancel_favorite){
                favoriteUserDB.FavoriteUserDBDao().delete(FavUser);

                ImageBannerWidget.updateWidget(this);

                Uri uri = Uri.parse(CONTENT_URI + "/" + id);
                getContentResolver().delete(uri, null, null);
                Toast.makeText(this, R.string.remove_to_favorite, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, R.string.validation, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            if(v == set_favorite){

                favoriteUserDB.FavoriteUserDBDao().addData(FavUser);
                ImageBannerWidget.updateWidget(this);
                getContentResolver().insert(CONTENT_URI, values);
                Toast.makeText(this, R.string.added_to_favorite, Toast.LENGTH_SHORT).show();
            }

        }
    }
}
