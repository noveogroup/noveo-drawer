package com.noveogroup.debugdrawer;

import android.content.Context;

import com.noveogroup.debugdrawer.api.EnablerProvider;
import com.noveogroup.debugdrawer.api.GradleProvider;
import com.noveogroup.debugdrawer.api.SelectorProvider;
import com.noveogroup.preferences.NoveoPreferences;

import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings({"unused", "WeakerAccess"})
public final class NoveoDebugDrawer {

    private static final Set<String> MODULES = new TreeSet<>();
    @SuppressWarnings("StaticFieldLeak")
    public static NoveoDebugDrawerConfig config;

    private NoveoDebugDrawer() {
    }

    public static void init(final NoveoDebugDrawerConfig config) {
        if (NoveoDebugDrawer.config == null) {
            NoveoDebugDrawer.config = config;
            Utils.setDebug(config.isDebugEnabled());
            NoveoPreferences.setDebug(config.isDebugEnabled());
            return;
        }
        Utils.sneakyThrow(new RuntimeException("NoveoDebugDrawer can't be initialized twice"));
    }

    public static void init(final Context context) {
        init(NoveoDebugDrawerConfig.builder(context).build());
    }

    public static GradleProvider getApplicationProvider() {
        return config.getSettings();
    }

    public static EnablerProvider getInspectionProvider() {
        return config.getSettings().getEnablerSettings();
    }

    public static SelectorProvider getSelectorProvider() {
        return config.getSettings().getSelectorSettings();
    }

    static void registerModule(final SupportDebugModule debugModule) {
        final String name = debugModule.getClass().getName();
        if (Utils.isDebug() && !MODULES.contains(name)) {
            MODULES.add(name);
            Utils.log(debugModule.logger, debugModule.getDebugInfo());
        }
    }

}
