package com.example.mygithubapps.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Follower implements Parcelable {
    private String login,avatar_url,type;
    private int id;

    public Follower(JSONObject object) {

        try {
            int id = object.getInt("id");
            String login = object.getString("login");
            String type = object.getString("type");
            String avatar_url = object.getString("avatar_url");

            this.id = id;
            this.login = login;
            this.type = type;
            this.avatar_url = avatar_url;

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Follower() {
    }

    public Follower(Parcel in) {
        this.id = in.readInt();
        this.login = in.readString();
        this.avatar_url = in.readString();
        this.type = in.readString();
    }

    public static final Creator<Follower> CREATOR = new Creator<Follower>() {
        @Override
        public Follower createFromParcel(Parcel in) {
            return new Follower(in);
        }

        @Override
        public Follower[] newArray(int size) {
            return new Follower[size];
        }
    };



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.login);
        dest.writeString(this.avatar_url);
        dest.writeString(this.type);
    }


}
