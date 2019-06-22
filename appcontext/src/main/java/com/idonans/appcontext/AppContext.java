package com.idonans.appcontext;

import android.app.ActivityThread;
import android.app.Application;
import android.content.Context;

import androidx.annotation.Nullable;

public class AppContext {

    private AppContext() {
    }

    private static Context sContext;

    public static void setContext(Context context) {
        if (sContext != null) {
            return;
        }

        if (context == null) {
            Throwable e = new IllegalArgumentException("invalid context, context is null");
            e.printStackTrace();
            return;
        }

        if (!(context instanceof Application)) {
            context = context.getApplicationContext();
        }

        if (!(context instanceof Application)) {
            Throwable e = new IllegalArgumentException("application not found from context " + context);
            e.printStackTrace();
            return;
        }

        sContext = context;
    }

    public static Context getContext() {
        if (sContext == null) {
            sContext = getActivityThreadApplication();
        }
        if (sContext == null) {
            Throwable e = new IllegalAccessError("AppContext not set. first call AppContext#setContext on Application#onCreate");
            e.printStackTrace();
        }
        return sContext;
    }

    @Nullable
    private static Context getActivityThreadApplication() {
        try {
            return ActivityThread.currentApplication();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

}
