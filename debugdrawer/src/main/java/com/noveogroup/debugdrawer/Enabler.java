package com.noveogroup.debugdrawer;

/**
 * Created by avaytsekhovskiy on 28/11/2017.
 */
public abstract class Enabler {

    private final String name;

    Enabler(final String name) {
        this.name = name;
    }

    public static Enabler create(final String name, final EnablerAction action) {
        return EnablerFactory.create(name, action);
    }

    public String getName() {
        return name;
    }

    public abstract void initialize(boolean enabled);

    public interface EnablerAction {
        void init(boolean enabled);
    }
}
