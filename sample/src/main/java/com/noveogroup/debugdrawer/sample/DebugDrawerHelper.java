package com.noveogroup.debugdrawer.sample;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.StyleRes;

import com.facebook.stetho.Stetho;
import com.noveogroup.debugdrawer.DebugBuildConfiguration;
import com.noveogroup.debugdrawer.Enabler;
import com.noveogroup.debugdrawer.EnablerModule;
import com.noveogroup.debugdrawer.GradleModule;
import com.noveogroup.debugdrawer.Selector;
import com.noveogroup.debugdrawer.SelectorModule;
import com.noveogroup.debugdrawer.SelectorProvider;
import com.squareup.leakcanary.LeakCanary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.palaima.debugdrawer.DebugDrawer;
import io.palaima.debugdrawer.commons.SettingsModule;

@SuppressWarnings("WeakerAccess")
public final class DebugDrawerHelper {

    public static final String ENABLER_STETHO = "Stetho";
    public static final String ENABLER_LEAK = "LeakCanary";
    public static final String SELECTOR_ENDPOINT = "Endpoint";
    public static final String SELECTOR_THEME = "App Theme";

    private static final Logger LOGGER = LoggerFactory.getLogger(DebugDrawerHelper.class);

    public final DebugBuildConfiguration configuration;

    DebugDrawerHelper(final Application application) {
        configuration = DebugBuildConfiguration.init(application);
        configuration.enableDebug();
        initEnablerModule(configuration, application);
        initSelectorModule(configuration);
    }

    public DebugDrawer makeDrawer(final Activity activity) {
        return new DebugDrawer.Builder(activity)
                .modules(new GradleModule(BuildConfig.class),
                        new SelectorModule(configuration),
//                        new BuildModule(activity),
//                        new DeviceModule(activity),
                        new EnablerModule(configuration),
                        new SettingsModule(activity)
                )
                .build();
    }

    void initSelectorModule(final DebugBuildConfiguration configuration) {
        final Selector endpointSelector = Selector.builder(SELECTOR_ENDPOINT)
                .addValues(
                        "http://staging.noveogroup.com",
                        "http://production.noveogroup.com",
                        "http://test.noveogroup.com",
                        "http://mock.noveogroup.com"
                )
                .setInitialValue("http://test.noveogroup.com")//initial value for debug
                .setReleaseValue("http://mock.noveogroup.com")//the only value for release
                .build();
        final Selector themeSelector = Selector.builder(SELECTOR_THEME)
                .addValues(
                        Theme.LIGHT.name(),
                        Theme.DARK.name(),
                        Theme.CUSTOM.name())
                .setInitialValue(Theme.CUSTOM.name())
                .setReleaseValue(Theme.DARK.name())
                .build();

        SelectorModule.init(configuration,
                endpointSelector, themeSelector);
    }

    void initEnablerModule(final DebugBuildConfiguration configuration, final Application application) {
        final Enabler stethoEnabler = Enabler.builder(ENABLER_STETHO,
                enabled -> {
                    LOGGER.info("init stetho {}", enabled);
                    if (enabled) {
                        Stetho.initializeWithDefaults(application);
                    }
                })
                .setInitialValue(true)
                .setReleaseValue(false)
                .build();

        final Enabler leakEnabler = Enabler.builder(
                ENABLER_LEAK,
                enabled -> {
                    LOGGER.info("init leak canary {}", enabled);
                    if (enabled) {
                        LeakCanary.install(application);
                    }
                })
                //initial and release - both true.
                //.setInitialValue(false)
                //.setReleaseValue(false)
                .build();

        final Enabler what = Enabler.builder(
                "What",
                enabled -> LOGGER.info("init what {}", enabled))
                .setInitialValue(true) //initial and release - both true.
                .build();

        final Enabler ever = Enabler.builder(
                "Ever",
                enabled -> LOGGER.info("init ever {}", enabled))
                .setInitialValue(true)
                .setReleaseValue(false)
                .build();

        final Enabler you = Enabler.builder(
                "You",
                enabled -> LOGGER.info("init you {}", enabled))
                .setInitialValue(true)
                .setReleaseValue(false)
                .build();

        final Enabler want = Enabler.builder(
                "Want",
                enabled -> LOGGER.info("init want {}", enabled))
                .setInitialValue(false) //initial and release - both false.
                .build();

        EnablerModule.init(configuration,
                stethoEnabler, leakEnabler,
                what, ever, you, want);
    }

    public int getTheme() {
        return DebugDrawerHelper.Theme.getSelectedTheme(configuration.getSelectorProvider());
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
            final String themeName = provider.read(SELECTOR_THEME);
            return Theme.valueOf(themeName).themeId;
        }
    }

}
