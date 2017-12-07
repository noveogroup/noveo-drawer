package com.noveogroup.debugdrawer;

import android.content.Context;

import com.noveogroup.debugdrawer.api.EnablerProvider;
import com.noveogroup.debugdrawer.api.GradleProvider;
import com.noveogroup.debugdrawer.api.SelectorProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings({"unused", "WeakerAccess"})
public final class NoveoDebugDrawer {

    static final Set<String> MODULES = new TreeSet<>();
    @SuppressWarnings("StaticFieldLeak")

    static BuildConfigDto buildConfig;
    static DrawerSelectorSettings selectors;
    static DrawerEnablerSettings enablers;

    private NoveoDebugDrawer() {
    }

    public static void init(final Context context) {
    }

    public static void enableLogging() {
    }

    public static GradleProvider getApplicationProvider() {
        return () -> new BuildConfigDto("", "", "", "");
    }

    public static EnablerProvider getInspectionProvider() {
        return firstNonNull(enablers, DrawerEnablerSettings.STUB);
    }

    public static SelectorProvider getSelectorProvider() {
        return firstNonNull(selectors, DrawerSelectorSettings.STUB);
    }

    static void initGradle(final BuildConfigDto dto) {
        buildConfig = dto;
    }

    static void initSelectors(final List<Selector> selectorList) {
        final HashMap<String, String> map = new HashMap<>();
        for (final Selector selector : selectorList) {
            map.put(selector.getName(), selector.getValues().get(0));
        }
        selectors = new DrawerSelectorSettings(map);
    }

    static void initEnablers(final List<Enabler> enablerList) {
        enablers = new DrawerEnablerSettings();
    }

    private static <T> T firstNonNull(T... objects) {
        for (final T object : objects) {
            return object;
        }
        return null;
    }

}
