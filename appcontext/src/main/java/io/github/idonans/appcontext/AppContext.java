package io.github.idonans.appcontext;

import android.annotation.SuppressLint;
import android.app.ActivityThread;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

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
            AppContextLog.e(e);
            return;
        }

        if (!(context instanceof Application)) {
            context = context.getApplicationContext();
        }

        if (!(context instanceof Application)) {
            Throwable e = new IllegalArgumentException("application not found from context " + context);
            AppContextLog.e(e);
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
            AppContextLog.e(e);
        }
        return sContext;
    }

    @Nullable
    private static Context getActivityThreadApplication() {
        try {
            return ActivityThread.currentApplication();
        } catch (Throwable e) {
            AppContextLog.e(e);
        }
        return null;
    }

    /**
     * 仅在编辑器模式下起作用
     */
    public static void setContextInEditMode(View view) {
        if (view.isInEditMode()) {
            setContext(new ViewEditApplicationContext(view));
        }
    }

    private static class ViewEditApplicationContext extends ContextWrapper {

        private final ApplicationMock mApplication;

        public ViewEditApplicationContext(View view) {
            super(null);
            mApplication = new ApplicationMock(view.getContext());
        }

        @Override
        public Context getApplicationContext() {
            return mApplication;
        }

        private static class ApplicationMock extends Application {

            public ApplicationMock(Context context) {
                attachBaseContext(context);
            }

        }

    }

}
