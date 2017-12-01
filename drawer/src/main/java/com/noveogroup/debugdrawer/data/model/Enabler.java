package com.noveogroup.debugdrawer.data.model;

import com.noveogroup.debugdrawer.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by avaytsekhovskiy on 28/11/2017.
 */
public abstract class Enabler {

    public static final Logger LOGGER = LoggerFactory.getLogger(Enabler.class);

    private final String name;

    private Enabler(final String name) {
        this.name = name;
    }

    public static Enabler create(final String name, final EnablerAction action) {
        return new Enabler(name) {
            @Override
            public void initialize(final boolean enabled) {
                Utils.log(LOGGER, "{} {}", enabled ? "enable" : "disable", getName());
                action.init(enabled);
            }
        };
    }

    public String getName() {
        return name;
    }

    public abstract void initialize(boolean enabled);

    public interface EnablerAction {
        void init(boolean enabled);
    }
}
