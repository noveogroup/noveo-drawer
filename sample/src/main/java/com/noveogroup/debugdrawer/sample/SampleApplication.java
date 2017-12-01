package com.noveogroup.debugdrawer.sample;

import android.app.Application;

import com.noveogroup.debugdrawer.api.NoveoDebugDrawer;

public class SampleApplication extends Application {

    public static int themeId;

    @Override
    public void onCreate() {
        super.onCreate();
        NoveoDebugDrawer.init(DebugDrawerHelper.createConfig(this));
        switch (DebugDrawerHelper.Theme.valueOf(NoveoDebugDrawer.getSelectorProvider().getSelectorValue(DebugDrawerHelper.SELECTOR_THEME))) {
            case LIGHT:
                themeId = R.style.AppTheme;
                break;
            case DARK:
                themeId = R.style.DarkAppTheme;
                break;
            case CUSTOM:
                themeId = R.style.CustomAppTheme;
                break;
        }
    }
}
