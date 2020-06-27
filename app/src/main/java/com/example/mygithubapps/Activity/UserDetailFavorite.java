package com.example.mygithubapps.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mygithubapps.Database.FavoriteUser;
import com.example.mygithubapps.R;
import com.example.mygithubapps.Widget.ImageBannerWidget;

import static com.example.mygithubapps.Activity.User.favoriteUserDB;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.AVATAR_URL;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.CONTENT_URI;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.LOGIN;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.TYPE;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite._ID;

public class UserDetailFavorite extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_Data = "extra_user";
    TextView txtfollowing,txtfollower,txtnamadetail,txttypedetail;
    ImageView photo_detail;
    Button set_favorite,cancel_favorite;
    private int id;
    String login,type,avatar_url,followerdb,followingdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_favorite);
        txtfollower = findViewById(R.id.txt_follower);
        txtfollowing = findViewById(R.id.txt_following);
        txtnamadetail = findViewById(R.id.textView_nama_detail);
        txttypedetail = findViewById(R.id.textView_type_detail);
        photo_detail = findViewById(R.id.photo_user_detail);
        set_favorite = findViewById(R.id.btn_set_favorite);
        cancel_favorite = findViewById(R.id.btn_set_cancel);

        set_favorite.setOnClickListener(this);
        cancel_favorite.setOnClickListener(this);

        final FavoriteUser follower = getIntent().getParcelableExtra(EXTRA_Data);

        id = follower.getId();
        login = follower.getLogin();
        type = follower.getType();
        avatar_url = follower.getAvatar_url();
        followerdb = follower.getFollowers();
        followingdb = follower.getFollowing();

        txtnamadetail.setText(login);
        txttypedetail.setText(type);
        txtfollower.setText(followerdb);
        txtfollowing.setText(followingdb);
        Glide.with(this)
                .load(avatar_url)
                .into(photo_detail);
    }

    @Override
    public void onClick(View v) {
        FavoriteUser FavUser =new FavoriteUser();

        FavUser.setId(id);
        FavUser.setLogin(login);
        FavUser.setAvatar_url(avatar_url);
        FavUser.setType(type);
        FavUser.setType(followerdb);
        FavUser.setFollowing(followingdb);

        ContentValues values = new ContentValues();
        values.put(_ID, id);
        values.put(LOGIN, login);
        values.put(TYPE, type);
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
