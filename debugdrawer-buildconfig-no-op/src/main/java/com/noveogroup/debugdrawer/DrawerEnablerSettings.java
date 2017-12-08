package com.noveogroup.debugdrawer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by avaytsekhovskiy on 06/12/2017.
 */
final class DrawerEnablerSettings implements EnablerProvider {

    private final Map<String, OneTimeInitializer> enablerMap;
    private final Map<String, Boolean> releaseMap;

    DrawerEnablerSettings(final List<Enabler> enablers) {
        this.enablerMap = new LinkedHashMap<>();
        this.releaseMap = new LinkedHashMap<>();

        for (final Enabler enabler : enablers) {
            releaseMap.put(enabler.getName(), enabler.getReleaseValue());
            final OneTimeInitializer initializer = new OneTimeInitializer(
                    () -> enabler.initialize(read(enabler.getName())));
            enablerMap.put(enabler.getName(), initializer);
            initializer.initializeIfRequired();
        }
    }

    @Override
    public Set<String> names() {
        return enablerMap.keySet();
    }

    @Override
    public Boolean read(final String name) {
        return releaseMap.get(name);
    }

    @Override
    @SuppressWarnings("PMD")
    public Boolean readDefault(final String name) {
        return releaseMap.containsKey(name) ? releaseMap.get(name) : false;
    }
}
