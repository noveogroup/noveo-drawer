package com.noveogroup.debugdrawer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by avaytsekhovskiy on 06/12/2017.
 */
final class DrawerEnablerSettings implements EnablerProvider, SettingsWriter<Boolean> {

    private final PreferencesApi preferences;
    private final Map<String, OneTimeInitializer> enablerMap;
    private final Map<String, Boolean> releaseMap;

    DrawerEnablerSettings(final PreferencesApi preferences,
                          final List<Enabler> enablers) {
        this.preferences = preferences;
        this.enablerMap = new LinkedHashMap<>();
        this.releaseMap = new LinkedHashMap<>();

        for (final Enabler enabler : enablers) {
            releaseMap.put(enabler.getName(), enabler.getReleaseValue());
            enablerMap.put(enabler.getName(), new OneTimeInitializer(
                    () -> enabler.initialize(read(enabler.getName()))));
        }

        applyInitial();
    }

    @Override
    public void applyInitial() {
        for (final String name : enablerMap.keySet()) {
            enablerMap.get(name).initializeIfRequired();
        }
    }

    @Override
    public Set<String> names() {
        return enablerMap.keySet();
    }

    @Override
    public Boolean read(final String name) {
        return preferences.getEnablerByName(name, releaseMap.get(name)).read().or(false);
    }

    @Override
    public void change(final String name, final Boolean value) {
        preferences.getEnablerByName(name, releaseMap.get(name)).save(value);
        applyInitial();
    }

    @Override
    @SuppressWarnings("PMD")
    public Boolean readDefault(final String name) {
        return releaseMap.containsKey(name) ? releaseMap.get(name) : false;
    }
}
