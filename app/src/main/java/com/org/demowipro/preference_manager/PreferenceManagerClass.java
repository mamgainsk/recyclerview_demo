package com.org.demowipro.preference_manager;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManagerClass {

    public static final String TITLE = "title";

    private static SharedPreferences preferenceManager(Context context) {
        return context.getSharedPreferences("my_preference", Context.MODE_PRIVATE);
    }

    public static void storeString(Context context, String key, String value) {
        preferenceManager(context).edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key) {
        return preferenceManager(context).getString(key, "");
    }
}
