package com.example.mygithubapps.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mygithubapps.Model.Follower;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FollowingViewModel extends ViewModel {
    private static MutableLiveData<ArrayList<Follower>> listUser = new MutableLiveData<>();

    public void setFollowing(final String username) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Follower> listItems = new ArrayList<>();
        client.addHeader("User-Agent", "request");
        String url = "https://api.github.com/users/"+username+"/following";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);

                    //JSONObject responseObject = new JSONObject(result);
                    JSONArray list = new JSONArray(result);

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject user = list.getJSONObject(i);
                        Follower follower = new Follower(user);
                        listItems.add(follower);
                    }
                    listUser.postValue(listItems);
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

    public static LiveData<ArrayList<Follower>> getFollowing() {
        return listUser;
    }
}
