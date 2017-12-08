package com.noveogroup.debugdrawer;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by avaytsekhovskiy on 06/12/2017.
 */
final class OneTimeInitializer {
    private final AtomicBoolean isInitialized;
    private final Runnable action;

    OneTimeInitializer(final Runnable action) {
        this.isInitialized = new AtomicBoolean();
        this.action = action;
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    void initializeIfRequired() {
        if (isInitialized.compareAndSet(false, true)) {
            try {
                action.run();
            } catch (Exception e) {
                Utils.sneakyThrow(e);
            }
        }
    }
}
