package com.noveogroup.debugdrawer;

/**
 * Created by avaytsekhovskiy on 28/11/2017.
 */
public abstract class Enabler {

    Enabler(final String name) {
        //do nothing
    }

    public static Enabler create(final String name, final Enabler.EnablerAction action) {
        return new Enabler(name) {
            @Override
            public void initialize(final boolean enabled) {
                //do nothing
            }
        };
    }

    public String getName() {
        return "";
    }

    public abstract void initialize(boolean enabled);

    public interface EnablerAction {
        void init(boolean enabled);
    }
}
