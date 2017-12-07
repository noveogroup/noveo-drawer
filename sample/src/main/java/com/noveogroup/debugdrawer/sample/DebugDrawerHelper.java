package com.noveogroup.debugdrawer.sample;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.StyleRes;

import com.facebook.stetho.Stetho;
import com.noveogroup.debugdrawer.Enabler;
import com.noveogroup.debugdrawer.EnablerModule;
import com.noveogroup.debugdrawer.GradleModule;
import com.noveogroup.debugdrawer.NoveoDebugDrawer;
import com.noveogroup.debugdrawer.Selector;
import com.noveogroup.debugdrawer.SelectorModule;
import com.noveogroup.debugdrawer.api.SelectorProvider;
import com.squareup.leakcanary.LeakCanary;

import io.palaima.debugdrawer.DebugDrawer;
import io.palaima.debugdrawer.commons.SettingsModule;

@SuppressWarnings("WeakerAccess")
public final class DebugDrawerHelper {

    public static final String ENABLER_STETHO = "Stetho";
    public static final String ENABLER_LEAK = "LeakCanary";

    public static final String SELECTOR_ENDPOINT = "Endpoint";
    public static final String SELECTOR_THEME = "App Theme";

    private DebugDrawerHelper() {
    }

    public static DebugDrawer makeDrawer(final Activity activity) {
        return new DebugDrawer.Builder(activity)
                .modules(new GradleModule(BuildConfig.class),
                        new SelectorModule(),
//                        new BuildModule(activity),
//                        new DeviceModule(activity),
                        new EnablerModule(),
                        new SettingsModule(activity)
                )
                .build();
    }

    static void initDebugDrawer(final Application application) {
        NoveoDebugDrawer.enableLogging();
        NoveoDebugDrawer.init(application);
        initEnablerModule(application);
        initSelectorModule();
    }

    static void initSelectorModule() {
        final Selector endpointSelector = new Selector(SELECTOR_ENDPOINT,
                "http://production.noveogroup.com",
                "http://staging.noveogroup.com",
                "http://test.noveogroup.com",
                "http://mock.noveogroup.com");
        final Selector themeSelector = new Selector(SELECTOR_THEME,
                Theme.LIGHT.name(),
                Theme.DARK.name(),
                Theme.CUSTOM.name());

        SelectorModule.init(
                endpointSelector, themeSelector);
    }

    static void initEnablerModule(final Application application) {
        final Enabler stethoEnabler = Enabler.create(ENABLER_STETHO, enabled -> {
            if (enabled) {
                Stetho.initializeWithDefaults(application);
            }
        });
        final Enabler leakEnabler = Enabler.create(ENABLER_LEAK, enabled -> {
            if (enabled) {
                LeakCanary.install(application);
            }
        });
        final Enabler what = Enabler.create("What", enabled -> {
        });
        final Enabler ever = Enabler.create("Ever", enabled -> {
        });
        final Enabler you = Enabler.create("You", enabled -> {
        });
        final Enabler want = Enabler.create("Want", enabled -> {
        });

        EnablerModule.init(
                stethoEnabler, leakEnabler,
                what, ever, you, want);
    }

    public enum Theme {
        LIGHT(R.style.AppTheme),
        DARK(R.style.DarkAppTheme),
        CUSTOM(R.style.CustomAppTheme);

        @StyleRes
        public final int themeId;

        Theme(@StyleRes final int themeId) {
            this.themeId = themeId;
        }

        @StyleRes
        public static int getSelectedTheme(final SelectorProvider provider) {
            return Theme.valueOf(provider
                    .read(SELECTOR_THEME))
                    .themeId;
        }
    }

}
