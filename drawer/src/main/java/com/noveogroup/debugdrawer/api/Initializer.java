package com.noveogroup.debugdrawer.api;

/**
 * Created by avaytsekhovskiy on 28/11/2017.
 */

public interface Initializer<T> {
    void initialize(Class<T> type);
}
