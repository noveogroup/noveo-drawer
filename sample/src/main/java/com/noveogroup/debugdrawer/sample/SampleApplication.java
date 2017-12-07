package com.noveogroup.debugdrawer.sample;

import android.app.Application;

import com.noveogroup.debugdrawer.NoveoDebugDrawer;

import static com.noveogroup.debugdrawer.sample.DebugDrawerHelper.Theme;

public class SampleApplication extends Application {

    public static int themeId;

    @Override
    public void onCreate() {
        super.onCreate();
        DebugDrawerHelper.initDebugDrawer(this);

        themeId = Theme.getSelectedTheme(NoveoDebugDrawer.getSelectorProvider());
    }
}
