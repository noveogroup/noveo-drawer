package com.noveogroup.debugdrawer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public interface EnablerProvider extends SettingsAggregatorProvider<Boolean> {
    EnablerProvider STUB = new EnablerProvider() {
        @Override
        public Set<String> names() {
            return Collections.unmodifiableSet(new HashSet<>(0));
        }

        @Override
        public Boolean read(final String name) {
            return false;
        }

        @Override
        public Boolean readDefault(final String name) {
            return false;
        }
    };

    Boolean readDefault(String name);
}
