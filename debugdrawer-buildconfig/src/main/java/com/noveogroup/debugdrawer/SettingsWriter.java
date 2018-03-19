package com.noveogroup.debugdrawer;


interface SettingsWriter<T> {
    void applyInitial();

    void change(String name, T value);
}
