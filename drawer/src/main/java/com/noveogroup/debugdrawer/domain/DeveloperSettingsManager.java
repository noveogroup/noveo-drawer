package com.noveogroup.debugdrawer.domain;

import android.content.Context;
import android.support.annotation.Nullable;

import com.facebook.stetho.Stetho;
import com.noveogroup.debugdrawer.api.Initializer;
import com.noveogroup.debugdrawer.data.canary.LeakCanaryProxy;
import com.noveogroup.debugdrawer.data.model.Endpoint;
import com.noveogroup.debugdrawer.data.preferences.DeveloperPreferences;
import com.squareup.leakcanary.LeakCanary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Completable;

public class DeveloperSettingsManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeveloperSettingsManager.class);

    private final DeveloperPreferences developerPreferences;

    private final LeakCanaryProxy leakCanaryProxy;

    private final AtomicBoolean stetho = new AtomicBoolean(false);
    private final AtomicBoolean leakCanary = new AtomicBoolean(false);

    private final Initializer<Stetho> stethoInitializer;
    private final Initializer<LeakCanary> leakCanaryInitializer;

    public DeveloperSettingsManager(final Context context,
                                    final Initializer<Stetho> stethoInitializer, final Initializer<LeakCanary> leakCanaryInitializer) {
        this.developerPreferences = new DeveloperPreferences(context.getApplicationContext());
        this.leakCanaryProxy = new LeakCanaryProxy();
        this.stethoInitializer = stethoInitializer;
        this.leakCanaryInitializer = leakCanaryInitializer;
        apply();
    }

    public DeveloperPreferences getDeveloperPreferences() {
        return developerPreferences;
    }

    public LeakCanaryProxy getLeakCanaryProxy() {
        return leakCanaryProxy;
    }

    public final boolean isStethoEnabled() {
        return developerPreferences.stethoEnabled.read()
                .blockingGet().orElse(false);
    }

    public final boolean isLeakCanaryEnabled() {
        return developerPreferences.leakCanaryEnabled.read()
                .blockingGet().orElse(false);
    }

    public Completable enableStetho(final boolean enabled) {
        return developerPreferences.stethoEnabled.save(enabled)
                .doOnComplete(this::apply);
    }

    public Completable enableLeakCanary(final boolean enabled) {
        return developerPreferences.leakCanaryEnabled.save(enabled)
                .doOnComplete(this::apply);
    }

    @Nullable
    public Endpoint readEndpoint() {
        return developerPreferences.backendUrl.read()
                .blockingGet().orElse(null);
    }

    public Completable changeEndpoint(final Endpoint endpoint) {
        return developerPreferences.backendUrl.save(endpoint);
    }

    private void apply() {
        if (stetho.compareAndSet(false, true) && isStethoEnabled()) {
            LOGGER.info("stetho initialized");
            stethoInitializer.initialize(Stetho.class);
        }

        if (leakCanary.compareAndSet(false, true) && isLeakCanaryEnabled()) {
            LOGGER.info("leak canary initialized");
            leakCanaryInitializer.initialize(LeakCanary.class);
        }
    }

}
