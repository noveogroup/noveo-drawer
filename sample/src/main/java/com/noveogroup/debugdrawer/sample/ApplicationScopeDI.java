package com.noveogroup.debugdrawer.sample;

import android.app.Application;

import com.noveogroup.debugdrawer.DebugBuildConfiguration;

public final class ApplicationScopeDI {

    public final DebugDrawerHelper drawerHelper;
    public final DebugBuildConfiguration configuration;

    public ApplicationScopeDI(final Application application) {
        drawerHelper = new DebugDrawerHelper(application);
        configuration = drawerHelper.configuration;
    }

}
