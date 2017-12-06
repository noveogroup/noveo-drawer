package com.noveogroup.debugdrawer.sample;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.StyleRes;

import com.facebook.stetho.Stetho;
import com.noveogroup.debugdrawer.Enabler;
import com.noveogroup.debugdrawer.EnablerModule;
import com.noveogroup.debugdrawer.GradleModule;
import com.noveogroup.debugdrawer.NoveoDebugDrawer;
import com.noveogroup.debugdrawer.NoveoDebugDrawerConfig;
import com.noveogroup.debugdrawer.Selector;
import com.noveogroup.debugdrawer.SelectorModule;
import com.noveogroup.debugdrawer.api.SelectorProvider;
import com.squareup.leakcanary.LeakCanary;

import io.palaima.debugdrawer.DebugDrawer;

@SuppressWarnings("WeakerAccess")
public final class DebugDrawerHelper {

    public static final String ENABLER_STETHO = "Stetho";
    public static final String ENABLER_LEAK = "LeakCanary";

    public static final String SELECTOR_ENDPOINT = "Endpoint";
    public static final String SELECTOR_THEME = "App Theme";

    private DebugDrawerHelper() {
    }

    public static NoveoDebugDrawerConfig createConfig(final Application application) {
        return NoveoDebugDrawerConfig.builder(application.getApplicationContext())
                .setBuildConfigClass(BuildConfig.class)
                .allowDebug()
                .addSelectors(
                        new Selector(SELECTOR_ENDPOINT,
                                "http://staging.noveogroup.com",
                                "http://production.noveogroup.com",
                                "http://test.noveogroup.com",
                                "http://mock.noveogroup.com"),
                        new Selector(SELECTOR_THEME,
                                Theme.LIGHT.name(),
                                Theme.DARK.name(),
                                Theme.CUSTOM.name())
                )
                .addEnablers(
                        Enabler.create(ENABLER_STETHO, enabled -> {
                            if (enabled) {
                                Stetho.initializeWithDefaults(application.getApplicationContext());
                            }
                        }),
                        Enabler.create(ENABLER_LEAK, enabled -> {
                            if (enabled) {
                                LeakCanary.install(application);
                            }
                        }),
                        Enabler.create("What", enabled -> {
                        }),
                        Enabler.create("Ever", enabled -> {
                        }),
                        Enabler.create("You", enabled -> {
                        }),
                        Enabler.create("Want", enabled -> {
                        }))
                .build();
    }

    public static DebugDrawer makeDrawer(final Activity activity) {
        return new DebugDrawer.Builder(activity)
                .modules(new GradleModule(),
                        new SelectorModule(),
//                        new BuildModule(activity),
//                        new DeviceModule(activity),
                        new EnablerModule()
//                        new SettingsModule(activity)
                )
                .build();
    }

    @StyleRes
    public static int getSelectedTheme() {
        final String name = getSelector().read(SELECTOR_THEME);
        return Theme.valueOf(name).themeId;
    }

    private static SelectorProvider getSelector() {
        return NoveoDebugDrawer.getSelectorProvider();
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
    }

}
