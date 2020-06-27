package com.example.mygithubapps.reminder;

import android.content.Context;
import android.content.SharedPreferences;

public class ReminderService {
    private static final String GITHUB_STATUS = "GITHUB_STATUS";
    public static final String STATUS_PENGINGAT_HARIAN = "STATUS_PENGINGAT_HARIAN";

    private final SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public ReminderService(Context ctx){
        preferences =ctx.getSharedPreferences(GITHUB_STATUS, Context.MODE_PRIVATE);
        editor=preferences.edit();
    }

    public void saveBoolean(String keySP, boolean value) {
        editor.putBoolean(keySP, value);
        editor.commit();
    }

    public Boolean getStatusPengingatHarian() {
        return preferences.getBoolean(STATUS_PENGINGAT_HARIAN, false);
    }
}
