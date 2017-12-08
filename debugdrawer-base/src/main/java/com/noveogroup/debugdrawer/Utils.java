package com.noveogroup.debugdrawer;

import org.slf4j.Logger;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings("PMD.AvoidThrowingNullPointerException")
final class Utils {

    private static final Set<String> MODULES = new TreeSet<>();

    private static boolean debug;

    private Utils() {
        //empty utility constructor
    }

    static void registerModule(final SupportDebugModule debugModule) {
        final String name = debugModule.getClass().getName();
        if (Utils.debug && !MODULES.contains(name)) {
            MODULES.add(name);
            Utils.log(debugModule.logger, debugModule.getDebugInfo());
        }
    }

    static void enableDebug() {
        Utils.debug = true;
    }

    @SafeVarargs
    static <T> T firstNonNull(final T... objects) {
        for (final T object : objects) {
            if (object != null) {
                return object;
            }
        }
        throw new NullPointerException("None of set non null");
    }

    static boolean isEmpty(final Iterable<?> iterable) {
        if (iterable instanceof Collection) {
            return ((Collection) iterable).isEmpty();
        }
        return !iterable.iterator().hasNext();
    }

    static void log(final Logger logger, final String message, final Object... args) {
        if (debug) {
            logger.debug(message, args);
        }
    }

    @SuppressWarnings("unchecked")
    static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }

}
