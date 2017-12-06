package com.noveogroup.debugdrawer;

import android.content.Context;

import com.noveogroup.debugdrawer.api.GradleProvider;

import java.util.List;

class DrawerSettings implements GradleProvider {

    private final BuildConfigDto buildConfig;

    private final DrawerSelectorSettings selectors;
    private final DrawerEnablerSettings enablers;

    DrawerSettings(final Context context,
                   final BuildConfigDto helper,
                   final List<Selector> properties,
                   final List<Enabler> enablers) {
        this.buildConfig = helper;

        final PreferencesApi preferences = new PreferencesApi(context.getApplicationContext());
        this.selectors = new DrawerSelectorSettings(preferences, properties);
        this.enablers = new DrawerEnablerSettings(preferences, enablers);
    }

    DrawerSelectorSettings getSelectorSettings() {
        return selectors;
    }

    DrawerEnablerSettings getEnablerSettings() {
        return enablers;
    }

    @Override
    public BuildConfigDto getBuildConfig() {
        return buildConfig;
    }

}
