package com.noveogroup.debugdrawer;

/**
 * Created by avaytsekhovskiy on 06/12/2017.
 */
interface SettingsWriter<T> {
    void applyInitial();

    void change(String name, T value);
}
