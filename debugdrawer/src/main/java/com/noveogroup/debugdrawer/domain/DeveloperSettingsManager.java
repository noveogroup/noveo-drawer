package com.noveogroup.debugdrawer.domain;

import android.content.Context;
import android.support.v4.util.Pair;

import com.noveogroup.debugdrawer.Utils;
import com.noveogroup.debugdrawer.api.provider.EnablerProvider;
import com.noveogroup.debugdrawer.api.provider.GradleProvider;
import com.noveogroup.debugdrawer.api.provider.SelectorProvider;
import com.noveogroup.debugdrawer.data.model.BuildConfigDto;
import com.noveogroup.debugdrawer.data.model.Enabler;
import com.noveogroup.debugdrawer.data.model.SelectorDto;
import com.noveogroup.debugdrawer.data.preferences.DeveloperPreferences;
import com.noveogroup.preferences.api.RxPreference;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Completable;
import io.reactivex.functions.Action;

public class DeveloperSettingsManager implements EnablerProvider, SelectorProvider, GradleProvider {

    private final BuildConfigDto buildConfig;

    private final DeveloperPreferences developerPreferences;

    private final Map<String, SingleInitializer> enablerMap = new LinkedHashMap<>();
    private final Map<String, Pair<SelectorDto, SingleInitializer>> selectorMap = new LinkedHashMap<>();

    public DeveloperSettingsManager(final Context context,
                                    final BuildConfigDto helper,
                                    final List<SelectorDto> properties,
                                    final List<Enabler> enablers) {
        this.buildConfig = helper;
        this.developerPreferences = new DeveloperPreferences(context.getApplicationContext());

        for (final SelectorDto selectorDto : properties) {
            this.selectorMap.put(selectorDto.getName(), new Pair<>(selectorDto, new SingleInitializer(() -> {
                final RxPreference<String> preference = developerPreferences.getSelectorByName(selectorDto.getName());
                if (!preference.read().blockingGet().isPresent()) {
                    final String initialValue = selectorDto.getValues().iterator().next();
                    preference.save(initialValue).subscribe();
                }
            })));
        }

        for (final Enabler enabler : enablers) {
            enablerMap.put(enabler.getName(), new SingleInitializer(
                    () -> enabler.initialize(isEnablerEnabled(enabler.getName()))));
        }

        applyEnablers();
        applySelectors();
    }

    public Set<String> getEnablers() {
        return enablerMap.keySet();
    }

    @Override
    public Set<String> getSelectors() {
        return selectorMap.keySet();
    }

    @Override
    public final boolean isEnablerEnabled(final String name) {
        return developerPreferences.getEnablerByName(name).read()
                .blockingGet().orElse(false);
    }

    @Override
    public String getSelectorValue(final String name) {
        return developerPreferences.getSelectorByName(name).read()
                .blockingGet().orElse(null);
    }

    public List<String> getSelectorValues(final String name) {
        return new ArrayList<>(selectorMap.get(name).first.getValues());
    }

    public Completable enableEnabler(final String name, final boolean enabled) {
        return developerPreferences.getEnablerByName(name).save(enabled)
                .doOnComplete(this::applyEnablers);
    }

    public Completable changeSelector(final String name, final String value) {
        return developerPreferences.getSelectorByName(name).save(value)
                .doOnComplete(this::applySelectors);
    }

    public final void applyEnablers() {
        for (final String name : enablerMap.keySet()) {
            enablerMap.get(name).initializeIfRequired();
        }
    }

    public final void applySelectors() {
        for (final String name : selectorMap.keySet()) {
            selectorMap.get(name).second.initializeIfRequired();
        }
    }

    @Override
    public BuildConfigDto getBuildConfig() {
        return buildConfig;
    }

    class SingleInitializer {
        final AtomicBoolean isInitialized;
        final Action action;

        SingleInitializer(final Action action) {
            this.isInitialized = new AtomicBoolean();
            this.action = action;
        }

        @SuppressWarnings("PMD.AvoidCatchingGenericException")
        void initializeIfRequired() {
            if (isInitialized.compareAndSet(false, true)) {
                try {
                    action.run();
                } catch (Exception e) {
                    Utils.sneakyThrow(e);
                }
            }
        }
    }

}
