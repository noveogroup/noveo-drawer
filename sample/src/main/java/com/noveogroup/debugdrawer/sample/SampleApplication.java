package com.noveogroup.debugdrawer.sample;

import android.app.Application;

public class SampleApplication extends Application {

    public static ApplicationScopeDI appScope;

    @Override
    public void onCreate() {
        super.onCreate();
        appScope = new ApplicationScopeDI(this);
    }
}
