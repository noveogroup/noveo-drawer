package com.noveogroup.debugdrawer;

/**
 * Created by avaytsekhovskiy on 28/11/2017.
 */
public abstract class Enabler extends ConfigParam<Boolean> {

    public Enabler(final String name, final Boolean releaseValue) {
        super(name, releaseValue);
    }

    public static Enabler create(final String name, final Enabler.EnablerAction action) {
        return new Enabler(name, false) {
            @Override
            public void initialize(final boolean enabled) {
                action.init(enabled);
            }
        };
    }

    public abstract void initialize(boolean enabled);

    public interface EnablerAction {
        void init(boolean enabled);
    }
}
