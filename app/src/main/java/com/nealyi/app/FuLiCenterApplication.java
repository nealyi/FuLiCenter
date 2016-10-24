package com.nealyi.app;

import android.app.Application;
import com.nealyi.app.bean.User;

/**
 * Created by nealyi on 16/10/17.
 */
public class FuLiCenterApplication extends Application {
    public static FuLiCenterApplication application;
    private static FuLiCenterApplication instance;

    private static String username;

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FuLiCenterApplication.user = user;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        FuLiCenterApplication.username = username;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        instance = this;
    }

    public static FuLiCenterApplication getInstance() {
        if (instance == null) {
            instance = new FuLiCenterApplication();
        }
        return instance;
    }



}
