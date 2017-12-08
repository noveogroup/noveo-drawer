package com.noveogroup.debugdrawer;

import java.util.Arrays;

@SuppressWarnings({"PMD.AvoidThrowingNullPointerException", "WeakerAccess"})
public class SelectorModule extends ConfigDebugModule {

    @SuppressWarnings("unused")
    public SelectorModule(final DebugBuildConfiguration configuration) {
        super();
    }

    public static void init(final DebugBuildConfiguration configuration, final Selector... selectors) {
        configuration.initSelectors(Arrays.asList(selectors));
    }

    @Override
    public String getDebugInfo() {
        return "no op";
    }
}
