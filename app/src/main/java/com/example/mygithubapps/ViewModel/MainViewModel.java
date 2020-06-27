package com.example.mygithubapps.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mygithubapps.Model.Item;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Item>> listUser = new MutableLiveData<>();

    public void setUser(final String username) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Item> listItems = new ArrayList<>();
        client.addHeader("User-Agent", "request");
        String url = "https://api.github.com/search/users?q=" + username;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);

                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("items");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject user = list.getJSONObject(i);
                        Item items = new Item();
                        items.setId(user.getInt("id"));
                        items.setLogin(user.getString("login"));
                        items.setType(user.getString("type"));
                        items.setAvatar_url(user.getString("avatar_url"));

                        listItems.add(items);
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

    public LiveData<ArrayList<Item>> getUser() {
        return listUser;
    }
}
