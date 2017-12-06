package com.noveogroup.debugdrawer;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by avaytsekhovskiy on 28/11/2017.
 */
public final class NoveoDebugDrawerConfig {

    private final RebirthExecutor rebirthExecutor;
    private final DrawerSettings settings;
    private final boolean debug;

    NoveoDebugDrawerConfig(final RebirthExecutor rebirthExecutor,
                           final DrawerSettings settings,
                           final boolean debug) {
        this.rebirthExecutor = rebirthExecutor;
        this.settings = settings;
        this.debug = debug;
    }

    public static Builder builder(final Context context) {
        return new Builder(context);
    }

    RebirthExecutor getRebirthExecutor() {
        return rebirthExecutor;
    }

    DrawerSettings getSettings() {
        return settings;
    }

    boolean isDebugEnabled() {
        return debug;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public static class Builder {
        final Context context;
        final List<Enabler> enablers;
        final List<Selector> properties;
        boolean debug;
        BuildConfigDto configHelper;

        public Builder(final Context context) {
            this.context = context.getApplicationContext();
            this.enablers = new ArrayList<>();
            this.properties = new ArrayList<>();
        }

        public Builder setBuildConfigClass(final Class buildConfigClass) {
            this.configHelper = new BuildConfigDto(buildConfigClass);
            return this;
        }

        @SuppressWarnings("PMD.UseObjectForClearerAPI")
        public Builder setBuildConfigParams(final String source, final String date, final String flavor, final String type) {
            this.configHelper = new BuildConfigDto(source, date, flavor, type);
            return this;
        }

        public Builder allowDebug() {
            this.debug = true;
            return this;
        }

        public Builder addEnabler(final Enabler enabler) {
            this.enablers.add(enabler);
            return this;
        }

        public Builder addEnablers(final Enabler... enablers) {
            this.enablers.addAll(Arrays.asList(enablers));
            return this;
        }

        public Builder addEnablers(final List<Enabler> enablers) {
            this.enablers.addAll(enablers);
            return this;
        }

        public Builder addSelector(final Selector selector) {
            this.properties.add(selector);
            return this;
        }

        public Builder addSelectors(final Selector... selectors) {
            this.properties.addAll(Arrays.asList(selectors));
            return this;
        }

        public Builder addSelectors(final List<Selector> selectors) {
            this.properties.addAll(selectors);
            return this;
        }

        public NoveoDebugDrawerConfig build() {
            final RebirthExecutor rebirthExecutor = new RebirthExecutor(context);
            final DrawerSettings settings = new DrawerSettings(context, configHelper, properties, enablers);
            return new NoveoDebugDrawerConfig(rebirthExecutor, settings, debug);
        }

    }
}
