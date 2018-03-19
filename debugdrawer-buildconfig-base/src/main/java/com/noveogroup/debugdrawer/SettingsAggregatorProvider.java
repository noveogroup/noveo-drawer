package com.noveogroup.debugdrawer;

import java.util.Set;


public interface SettingsAggregatorProvider<T> {

    /**
     * get all keys for this kind of settings.
     *
     * @return String set. Basically you shouldn't rely on its order. However for now LinkedHashSet used to keep order.
     */
    Set<String> names();

    /**
     * read setting value by its key. Key should be presented in names() set.
     * @param name property key that you can find in names() set.
     * @return value of property.
     */
    T read(String name);
}
