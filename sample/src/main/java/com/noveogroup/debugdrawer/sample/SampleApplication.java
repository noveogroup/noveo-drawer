package com.noveogroup.debugdrawer.sample;

import android.app.Application;

import com.noveogroup.debugdrawer.NoveoDebugDrawer;

public class SampleApplication extends Application {

    public static int themeId;

    @Override
    public void onCreate() {
        super.onCreate();
        NoveoDebugDrawer.init(DebugDrawerHelper.createConfig(this));
        themeId = DebugDrawerHelper.getSelectedTheme();
    }
}
