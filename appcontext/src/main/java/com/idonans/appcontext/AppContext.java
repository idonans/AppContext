package com.idonans.appcontext;

import android.annotation.SuppressLint;
import android.app.ActivityThread;
import android.app.Application;
import android.content.Context;

import androidx.annotation.Nullable;

public class AppContext {

    private AppContext() {
    }

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    public static void setContext(Context context) {
        if (sContext != null) {
            return;
        }

        if (context == null) {
            Throwable e = new IllegalArgumentException("invalid context, context is null");
            LibLog.e(e);
            return;
        }

        if (!(context instanceof Application)) {
            context = context.getApplicationContext();
        }

        if (!(context instanceof Application)) {
            Throwable e = new IllegalArgumentException("application not found from context " + context);
            LibLog.e(e);
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
            LibLog.e(e);
        }
        return sContext;
    }

    @Nullable
    private static Context getActivityThreadApplication() {
        try {
            return ActivityThread.currentApplication();
        } catch (Throwable e) {
            LibLog.e(e);
        }
        return null;
    }

}
