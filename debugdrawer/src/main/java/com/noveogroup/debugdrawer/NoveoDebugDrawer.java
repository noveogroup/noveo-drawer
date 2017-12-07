package com.noveogroup.debugdrawer;

import android.content.Context;

import com.noveogroup.debugdrawer.api.EnablerProvider;
import com.noveogroup.debugdrawer.api.GradleProvider;
import com.noveogroup.debugdrawer.api.SelectorProvider;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings({"unused", "WeakerAccess"})
public final class NoveoDebugDrawer {

    static final Set<String> MODULES = new TreeSet<>();
    @SuppressWarnings("StaticFieldLeak")

    static PreferencesApi preferences;
    static RebirthExecutor rebirthExecutor;

    static BuildConfigDto buildConfig;
    static DrawerSelectorSettings selectors;
    static DrawerEnablerSettings enablers;

    private NoveoDebugDrawer() {
    }

    public static void init(final Context context) {
        if (preferences != null) {
            Utils.sneakyThrow(new RuntimeException("NoveoDebugDrawer can't be initialized twice"));
        }

        final Context applicationContext = context.getApplicationContext();
        preferences = new PreferencesApi(applicationContext);
        rebirthExecutor = new RebirthExecutor(applicationContext);
    }

    public static void enableLogging() {
        Utils.enableDebug();
    }

    public static GradleProvider getApplicationProvider() {
        return () -> Utils.firstNonNull(buildConfig, new BuildConfigDto("", "", "", ""));
    }

    public static EnablerProvider getInspectionProvider() {
        return Utils.firstNonNull(enablers, DrawerEnablerSettings.STUB);
    }

    public static SelectorProvider getSelectorProvider() {
        return Utils.firstNonNull(selectors, DrawerSelectorSettings.STUB);
    }

    static void initGradle(final BuildConfigDto dto) {
        buildConfig = dto;
    }

    static void initSelectors(final List<Selector> selectorList) {
        selectors = new DrawerSelectorSettings(preferences, selectorList);
    }

    static void initEnablers(final List<Enabler> enablerList) {
        enablers = new DrawerEnablerSettings(preferences, enablerList);
    }

    static void registerModule(final SupportDebugModule debugModule) {
        final String name = debugModule.getClass().getName();
        if (Utils.debug && !MODULES.contains(name)) {
            MODULES.add(name);
            Utils.log(debugModule.logger, debugModule.getDebugInfo());
        }
    }

}