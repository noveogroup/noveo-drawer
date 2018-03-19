package com.noveogroup.debugdrawer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class DrawerEnablerSettings implements EnablerProvider, SettingsWriter<Boolean> {

    private final PreferencesApi preferences;
    private final Map<String, EnablerDescriptor> descriptors;

    DrawerEnablerSettings(final PreferencesApi preferences,
                          final List<Enabler> enablers) {
        this.preferences = preferences;
        this.descriptors = new LinkedHashMap<>();

        for (final Enabler enabler : enablers) {
            final OneTimeInitializer initializer = new OneTimeInitializer(
                    () -> enabler.initialize(read(enabler.getName()))
            );
            descriptors.put(enabler.getName(), new EnablerDescriptor(enabler, initializer));
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
    public Set<String> names() {
        return descriptors.keySet();
    }

    @Override
    public Boolean read(final String name) {
        final EnablerDescriptor descriptor = descriptors.get(name);
        return preferences.getEnablerByName(name, descriptor.getInitialValue())
                .read()
                .or(descriptor.getInitialValue());
    }

    @Override
    public void change(final String name, final Boolean value) {
        final EnablerDescriptor descriptor = descriptors.get(name);
        preferences.getEnablerByName(name, descriptor.getInitialValue())
                .save(value);
        applyInitial();
    }

}
