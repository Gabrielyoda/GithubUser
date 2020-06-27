package com.example.mygithubapps.Database;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_user")
public class FavoriteUser implements Parcelable {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "login")
    private String login;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "avatar_url")
    private String avatar_url;

    @ColumnInfo(name = "followers")
    private String followers;

    @ColumnInfo(name = "following")
    private String following;

    public FavoriteUser(Parcel in) {
        id = in.readInt();
        login = in.readString();
        type = in.readString();
        avatar_url = in.readString();
        followers = in.readString();
        following = in.readString();
    }

    public static final Creator<FavoriteUser> CREATOR = new Creator<FavoriteUser>() {
        @Override
        public FavoriteUser createFromParcel(Parcel in) {
            return new FavoriteUser(in);
        }

        @Override
        public FavoriteUser[] newArray(int size) {
            return new FavoriteUser[size];
        }
    };

    public FavoriteUser() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(login);
        dest.writeString(type);
        dest.writeString(avatar_url);
        dest.writeString(followers);
        dest.writeString(following);
    }
}
