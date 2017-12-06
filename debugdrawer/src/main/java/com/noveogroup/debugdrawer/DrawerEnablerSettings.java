package com.noveogroup.debugdrawer;

import com.noveogroup.debugdrawer.api.EnablerProvider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by avaytsekhovskiy on 06/12/2017.
 */
final class DrawerEnablerSettings implements EnablerProvider, SettingsWriter<Boolean> {

    private final PreferencesApi preferences;
    private final Map<String, SingleInitializer> enablerMap;

    DrawerEnablerSettings(final PreferencesApi preferences,
                          final List<Enabler> enablers) {
        this.preferences = preferences;
        this.enablerMap = new LinkedHashMap<>();


        for (final Enabler enabler : enablers) {
            enablerMap.put(enabler.getName(), new SingleInitializer(
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
        return preferences.getEnablerByName(name).read().or(false);
    }

    @Override
    public void change(final String name, final Boolean value) {
        preferences.getEnablerByName(name).save(value);
        applyInitial();
    }
}
