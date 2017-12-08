package com.noveogroup.debugdrawer;

import android.content.Context;

import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
public final class DebugBuildConfiguration {

    private static DebugBuildConfiguration instance;

    private DrawerSelectorSettings selectors;
    private DrawerEnablerSettings enablers;

    private DebugBuildConfiguration(final Context context) {
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

    public EnablerProvider getEnablerProvider() {
        return Utils.firstNonNull(enablers, DrawerEnablerSettings.STUB);
    }

    public SelectorProvider getSelectorProvider() {
        return Utils.firstNonNull(selectors, DrawerSelectorSettings.STUB);
    }

    void initSelectors(final List<Selector> selectorList) {
        selectors = new DrawerSelectorSettings(selectorList);
    }

    void initEnablers(final List<Enabler> enablerList) {
        enablers = new DrawerEnablerSettings(enablerList);
    }

}
