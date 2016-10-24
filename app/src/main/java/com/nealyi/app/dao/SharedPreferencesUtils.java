package com.nealyi.app.dao;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by nealyi on 16/10/24.
 */
public class SharedPreferencesUtils {
    private static final String SHARE_NAME = "saveUserInfo";
    private static SharedPreferencesUtils instance;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    public static final String SHARE_KEY_USER_NAME = "share_key_user_name";

    public SharedPreferencesUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static SharedPreferencesUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesUtils(context);
        }
        return instance;
    }

    public void saveUser(String username) {
        mEditor.putString(SHARE_KEY_USER_NAME, username);
        mEditor.commit();
    }

    public String getUser() {
        return mSharedPreferences.getString(SHARE_KEY_USER_NAME, null);
    }

    public void removeUser() {
        mEditor.remove(SHARE_KEY_USER_NAME);
        mEditor.commit();
    }
}
