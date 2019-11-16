package com.netatmo.ylu.skinswitcher;

import android.app.Application;

import com.netatmo.ylu.core.SkinManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.Companion.init(this);
    }
}
