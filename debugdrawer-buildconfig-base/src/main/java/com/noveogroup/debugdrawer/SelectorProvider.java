package com.noveogroup.debugdrawer;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface SelectorProvider extends SettingsAggregatorProvider<String> {
    Set<String> STRINGS = Collections.unmodifiableSet(new HashSet<>(0));

    SelectorProvider STUB = new SelectorProvider() {
        @Override
        public List<String> values(final String name) {
            return Collections.emptyList();
        }

        @Override
        public Set<String> names() {
            return STRINGS;
        }

        @Override
        public String read(final String name) {
            return "";
        }
    };

    List<String> values(String name);
}
