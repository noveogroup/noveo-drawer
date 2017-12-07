package com.noveogroup.debugdrawer;


import com.noveogroup.debugdrawer.api.SelectorProvider;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by avaytsekhovskiy on 06/12/2017.
 */
final class DrawerSelectorSettings implements SelectorProvider {

    private final Map<String, String> value;

    DrawerSelectorSettings(final Map<String, String> value) {
        this.value = value;
    }

    @Override
    public Set<String> names() {
        return new HashSet<>();
    }

    @Override
    public String read(final String name) {
        return value.get(name);
    }

    @Override
    public List<String> values(final String name) {
        return Collections.emptyList();
    }
}
