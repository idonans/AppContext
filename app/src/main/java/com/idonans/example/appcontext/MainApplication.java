package com.idonans.example.appcontext;

import android.app.Application;

import com.idonans.appcontext.AppContext;

import timber.log.Timber;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.setContext(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

}
