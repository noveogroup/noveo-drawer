package com.noveogroup.debugdrawer;


import android.util.Pair;

import com.noveogroup.preferences.api.Preference;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by avaytsekhovskiy on 06/12/2017.
 */
final class DrawerSelectorSettings implements SelectorProvider, SettingsWriter<String> {

    private final PreferencesApi preferences;
    private final Map<String, String> releaseMap;
    private final Map<String, Pair<List<String>, OneTimeInitializer>> selectorMap;

    DrawerSelectorSettings(final PreferencesApi preferences,
                           final List<Selector> selectors) {
        this.preferences = preferences;
        this.selectorMap = new LinkedHashMap<>();
        this.releaseMap = new LinkedHashMap<>();

        for (final Selector selector : selectors) {
            final String name = selector.getName();
            final List<String> values = selector.getValues();
            final String releaseValue = selector.getReleaseValue();

            releaseMap.put(name, releaseValue);
            selectorMap.put(name, new Pair<>(values, new OneTimeInitializer(() -> {
                final Preference<String> preference = preferences.getSelectorByName(name);
                preference.read().applyAbsent(() -> preference.save(releaseValue));
            })));
        }

        applyInitial();
    }

    @Override
    public void applyInitial() {
        for (final String name : selectorMap.keySet()) {
            selectorMap.get(name).second.initializeIfRequired();
        }
    }

    @Override
    public void change(final String name, final String value) {
        preferences.getSelectorByName(name).save(value);
        applyInitial();
    }

    @Override
    public Set<String> names() {
        return selectorMap.keySet();
    }

    @Override
    public String read(final String name) {
        return preferences.getSelectorByName(name).read().or(releaseMap.get(name));
    }

    @Override
    public List<String> values(final String name) {
        return selectorMap.get(name).first;
    }
}
