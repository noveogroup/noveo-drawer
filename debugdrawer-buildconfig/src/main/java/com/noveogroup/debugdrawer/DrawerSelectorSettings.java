package com.noveogroup.debugdrawer;


import com.noveogroup.preferences.api.Preference;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


final class DrawerSelectorSettings implements SelectorProvider, SettingsWriter<String> {

    private final PreferencesApi preferences;
    private final Map<String, SelectorDescriptor> descriptors;

    DrawerSelectorSettings(final PreferencesApi preferences,
                           final List<Selector> selectors) {
        this.preferences = preferences;
        this.descriptors = new LinkedHashMap<>();

        for (final Selector selector : selectors) {
            final String name = selector.getName();
            final OneTimeInitializer initializer = new OneTimeInitializer(() -> {
                final Preference<String> preference = preferences.getSelectorByName(name);
                preference.read().applyAbsent(() -> preference.save(selector.getInitialValue()));
            });
            descriptors.put(name, new SelectorDescriptor(selector, initializer));
        }

        applyInitial();
    }

    @Override
    public void applyInitial() {
        for (final String name : descriptors.keySet()) {
            descriptors.get(name).getInitializer().initializeIfRequired();
        }
    }

    @Override
    public void change(final String name, final String value) {
        preferences.getSelectorByName(name).save(value);
        applyInitial();
    }

    @Override
    public Set<String> names() {
        return descriptors.keySet();
    }

    @Override
    public String read(final String name) {
        return preferences.getSelectorByName(name).read().or(descriptors.get(name).getInitialValue());
    }

    @Override
    public List<String> values(final String name) {
        return descriptors.get(name).getValues();
    }

}
