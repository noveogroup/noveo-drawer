package com.noveogroup.debugdrawer;

import android.content.Context;

import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
public final class DebugBuildConfiguration {

    private static DebugBuildConfiguration instance;

    private final PreferencesApi preferences;
    private final RebirthExecutor rebirthExecutor;

    private DrawerSelectorSettings selectors;
    private DrawerEnablerSettings enablers;

    private DebugBuildConfiguration(final Context context) {
        final Context applicationContext = context.getApplicationContext();
        preferences = new PreferencesApi(applicationContext);
        rebirthExecutor = new RebirthExecutor(applicationContext);
    }

    public static DebugBuildConfiguration init(final Context context) {
        synchronized (DebugBuildConfiguration.class) {
            if (instance == null) {
                instance = new DebugBuildConfiguration(context);
            }
        }
        return instance;
    }

    public static void release() {
        instance = null;
    }

    public void enableDebug() {
        Utils.enableDebug();
    }

    PreferencesApi getPreferences() {
        return preferences;
    }

    RebirthExecutor getRebirthExecutor() {
        return rebirthExecutor;
    }

    /**
     * @return value provider for all Enablers in DebugDrawer.
     */
    public EnablerProvider getEnablerProvider() {
        return Utils.firstNonNull(enablers, DrawerEnablerSettings.STUB);
    }

    /**
     * @return value provider for all Selectors in DebugDrawer.
     */
    public SelectorProvider getSelectorProvider() {
        return Utils.firstNonNull(selectors, DrawerSelectorSettings.STUB);
    }

    SettingsWriter<Boolean> getEnablerWriter() {
        return enablers;
    }

    SettingsWriter<String> getSelectorWriter() {
        return selectors;
    }

    void initSelectors(final List<Selector> selectorList) {
        selectors = new DrawerSelectorSettings(preferences, selectorList);
    }

    void initEnablers(final List<Enabler> enablerList) {
        enablers = new DrawerEnablerSettings(preferences, enablerList);
    }

}
