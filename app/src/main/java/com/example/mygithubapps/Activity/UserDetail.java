package com.example.mygithubapps.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mygithubapps.Database.FavoriteUser;
import com.example.mygithubapps.Model.Follower;
import com.example.mygithubapps.R;
import com.example.mygithubapps.Widget.ImageBannerWidget;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.example.mygithubapps.Activity.User.favoriteUserDB;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.AVATAR_URL;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.CONTENT_URI;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.LOGIN;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite.TYPE;
import static com.example.mygithubapps.Provider.DatabaseContract.UserFavorite._ID;

public class UserDetail extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_Data = "extra_user";
    TextView txtfollowing,txtfollower,txtnamadetail,txttypedetail;
    ImageView photo_detail;
    Button  set_favorite,cancel_favorite;
    private int id;
    private ProgressBar progressBar;
    String login,type,avatar_url,followerdb,followingdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        txtfollower = findViewById(R.id.txt_follower);
        txtfollowing = findViewById(R.id.txt_following);
        txtnamadetail = findViewById(R.id.textView_nama_detail);
        txttypedetail = findViewById(R.id.textView_type_detail);
        photo_detail = findViewById(R.id.photo_user_detail);
        set_favorite = findViewById(R.id.btn_set_favorite);
        cancel_favorite = findViewById(R.id.btn_set_cancel);
        progressBar = findViewById(R.id.progressBar);

        set_favorite.setOnClickListener(this);
        cancel_favorite.setOnClickListener(this);
        showLoading(true);


        final Follower follower = getIntent().getParcelableExtra(EXTRA_Data);

        id = follower.getId();
        login = follower.getLogin();
        type = follower.getType();
        avatar_url = follower.getAvatar_url();

        txtnamadetail.setText(login);
        txttypedetail.setText(type);
        Glide.with(this)
                .load(avatar_url)
                .into(photo_detail);


        String username = follower.getLogin();
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("User-Agent", "request");
        String url = "https://api.github.com/users/" + username;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);

                    JSONObject responseObject = new JSONObject(result);

                        txtfollower.setText(responseObject.getString("followers"));
                        txtfollowing.setText(responseObject.getString("following"));
                        followerdb = responseObject.getString("followers");
                        followingdb = responseObject.getString("following");
                        showLoading(false);

                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
                Log.d("Failed", ""+statusCode);
                Log.d("Error", ""+error);
            }
        });

    }


    @Override
    public void onClick(View v) {
        FavoriteUser FavUser =new FavoriteUser();

        FavUser.setId(id);
        FavUser.setLogin(login);
        FavUser.setAvatar_url(avatar_url);
        FavUser.setType(type);
        FavUser.setFollowers(followerdb);
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

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
