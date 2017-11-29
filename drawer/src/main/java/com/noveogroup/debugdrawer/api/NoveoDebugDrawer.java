package com.noveogroup.debugdrawer.api;

import com.noveogroup.debugdrawer.Utils;
import com.noveogroup.debugdrawer.api.provider.EndpointProvider;
import com.noveogroup.debugdrawer.api.provider.InspectionProvider;

public final class NoveoDebugDrawer {

    private static InspectionProvider inspectionProvider;
    private static EndpointProvider endpointProvider;

    @SuppressWarnings("StaticFieldLeak")
    static NoveoDrawerConfig config;
    private static boolean debug;

    private NoveoDebugDrawer() {
    }

    /**
     * if true - library will use @Slf4j {@link org.slf4j.Logger} to write logs.
     *
     * @return true if enabled. false otherwise.
     */
    @SuppressWarnings("unused")
    public static boolean isDebug() {
        return debug;
    }

    /**
     * Enabled logging with @Slf4j {@link org.slf4j.Logger}
     *
     * @param debug boolean to enable.
     */
    public static void setDebug(final boolean debug) {
        NoveoDebugDrawer.debug = debug;
    }

    public static void init(final NoveoDrawerConfig configuration) {
        if (config == null) {
            config = configuration;
            return;
        }
        Utils.sneakyThrow(new RuntimeException("NoveoDebugDrawer can't be initialized twice"));
    }

    public static InspectionProvider getInspectionProvider() {
        synchronized (NoveoDebugDrawer.class) {
            if (inspectionProvider == null) {
                inspectionProvider = new InspectionProvider(config.getSettings());
            }
        }
        return inspectionProvider;
    }

    public static EndpointProvider getEndpointProvider() {
        synchronized (NoveoDebugDrawer.class) {
            if (endpointProvider == null) {
                endpointProvider = new EndpointProvider(config.getSettings());
            }
        }
        return endpointProvider;
    }

}
