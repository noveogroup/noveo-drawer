package com.noveogroup.debugdrawer.api;

import java.util.Set;

/**
 * Created by avaytsekhovskiy on 06/12/2017.
 */
public interface SettingsAggregatorProvider<T> {
    Set<String> names();

    T read(String name);
}
