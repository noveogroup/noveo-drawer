package com.noveogroup.debugdrawer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("checkstyle:interfaceistypecheck")
public interface EnablerProvider extends SettingsAggregatorProvider<Boolean> {

    Set<String> STRINGS = Collections.unmodifiableSet(new HashSet<>(0));

    EnablerProvider STUB = new EnablerProvider() {
        @Override
        public Set<String> names() {
            return STRINGS;
        }

        @Override
        public Boolean read(final String name) {
            return false;
        }

    };

}
