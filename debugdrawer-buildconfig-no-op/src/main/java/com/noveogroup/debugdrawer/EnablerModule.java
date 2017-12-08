package com.noveogroup.debugdrawer;

import java.util.Arrays;

public class EnablerModule extends ConfigDebugModule {

    @SuppressWarnings({"unused"})
    public EnablerModule(final DebugBuildConfiguration configuration) {
        super();
    }

    public static void init(final DebugBuildConfiguration configuration, final Enabler... enablers) {
        configuration.initEnablers(Arrays.asList(enablers));
    }

    @Override
    public String getDebugInfo() {
        return "no op";
    }
}
