package com.noveogroup.debugdrawer;


import android.util.Pair;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by avaytsekhovskiy on 06/12/2017.
 */
final class DrawerSelectorSettings implements SelectorProvider {

    private final Map<String, String> releaseMap;
    private final Map<String, Pair<List<String>, OneTimeInitializer>> selectorMap;

    DrawerSelectorSettings(final List<Selector> selectors) {
        this.selectorMap = new LinkedHashMap<>();
        this.releaseMap = new LinkedHashMap<>();

        for (final Selector selector : selectors) {
            final String name = selector.getName();
            final List<String> values = selector.getValues();
            final String releaseValue = selector.getReleaseValue();

            releaseMap.put(name, releaseValue);
            final OneTimeInitializer initializer = new OneTimeInitializer(() -> {
            });
            selectorMap.put(name, new Pair<>(values, initializer));
            initializer.initializeIfRequired();
        }
    }

    @Override
    public Set<String> names() {
        return selectorMap.keySet();
    }

    @Override
    public String read(final String name) {
        return releaseMap.get(name);
    }

    @Override
    public List<String> values(final String name) {
        return selectorMap.get(name).first;
    }
}
