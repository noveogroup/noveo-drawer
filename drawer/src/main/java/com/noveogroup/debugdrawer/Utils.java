package com.noveogroup.debugdrawer;

import org.slf4j.Logger;

import java.util.Collection;

@SuppressWarnings("PMD.AvoidThrowingNullPointerException")
public final class Utils {

    private static boolean debug;

    private Utils() {
        //empty utility constructor
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(final boolean debug) {
        Utils.debug = debug;
    }

    @SafeVarargs
    public static <T> T firstNonNull(final T... objects) {
        for (final T object : objects) {
            if (object != null) {
                return object;
            }
        }
        throw new NullPointerException("None of set non null");
    }

    public static boolean isEmpty(final Iterable<?> iterable) {
        if (iterable instanceof Collection) {
            return ((Collection) iterable).isEmpty();
        }
        return !iterable.iterator().hasNext();
    }

    public static void log(final Logger logger, final String message, final Object... args) {
        if (debug) {
            logger.debug(message, args);
        }
    }

    @SuppressWarnings("unchecked")
    public static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }

}
