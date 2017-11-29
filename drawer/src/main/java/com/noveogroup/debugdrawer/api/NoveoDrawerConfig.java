package com.noveogroup.debugdrawer.api;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.noveogroup.debugdrawer.domain.DeveloperSettingsManager;
import com.noveogroup.debugdrawer.domain.RebirthExecutor;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by avaytsekhovskiy on 28/11/2017.
 */
public final class NoveoDrawerConfig {

    private final RebirthExecutor rebirthExecutor;
    private final DeveloperSettingsManager settings;

    NoveoDrawerConfig(final Application application,
                              final Initializer<Stetho> stethoInitializer, final Initializer<LeakCanary> leakCanaryInitializer) {
        this.rebirthExecutor = new RebirthExecutor(application);
        this.settings = new DeveloperSettingsManager(application, stethoInitializer, leakCanaryInitializer);
    }

    public static Builder builder(final Application application) {
        return new Builder(application);
    }

    RebirthExecutor getRebirthExecutor() {
        return rebirthExecutor;
    }

    DeveloperSettingsManager getSettings() {
        return settings;
    }

    public static class Builder {

        private final Application application;

        private Initializer<Stetho> stethoInitializer;
        private Initializer<LeakCanary> leakCanaryInitializer;

        public Builder(final Application application) {
            this.application = application;
            this.stethoInitializer = stethoClass -> Stetho.initializeWithDefaults(application);
            this.leakCanaryInitializer = canaryClass -> LeakCanary.install(application);
        }

        public Builder setStethoInitializer(final Initializer<Stetho> stethoInitializer) {
            this.stethoInitializer = stethoInitializer;
            return this;
        }

        public Builder setLeakCanaryInitializer(final Initializer<LeakCanary> leakCanaryInitializer) {
            this.leakCanaryInitializer = leakCanaryInitializer;
            return this;
        }

        public NoveoDrawerConfig build() {
            return new NoveoDrawerConfig(application, stethoInitializer, leakCanaryInitializer);
        }
    }
}
