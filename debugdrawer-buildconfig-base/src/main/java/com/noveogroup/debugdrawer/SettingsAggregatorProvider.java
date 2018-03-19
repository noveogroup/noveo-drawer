package com.noveogroup.debugdrawer;

import java.util.Set;


public interface SettingsAggregatorProvider<T> {
    Set<String> names();

    T read(String name);
}
