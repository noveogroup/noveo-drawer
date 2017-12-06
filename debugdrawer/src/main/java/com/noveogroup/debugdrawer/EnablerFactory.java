package com.noveogroup.debugdrawer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("WeakerAccess")
final class EnablerFactory {

    static final Logger LOGGER = LoggerFactory.getLogger(Enabler.class);

    private EnablerFactory() {
    }

    static Enabler create(final String name, final Enabler.EnablerAction action) {
        return new Enabler(name) {
            @Override
            public void initialize(final boolean enabled) {
                Utils.log(LOGGER, "{} {}", enabled ? "enable" : "disable", getName());
                action.init(enabled);
            }
        };
    }

}
