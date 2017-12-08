package com.noveogroup.debugdrawer.sample;

import android.app.Application;

public class SampleApplication extends Application {

    public static ApplicationScopeDI injector;

    @Override
    public void onCreate() {
        super.onCreate();
        injector = new ApplicationScopeDI(this);
    }
}
