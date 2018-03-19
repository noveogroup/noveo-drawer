package com.noveogroup.debugdrawer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


final class DrawerEnablerSettings implements EnablerProvider {

    private final Map<String, EnablerDescriptor> descriptors;

    DrawerEnablerSettings(final List<Enabler> enablers) {
        this.descriptors = new LinkedHashMap<>();

        for (final Enabler enabler : enablers) {
            final OneTimeInitializer initializer = new OneTimeInitializer(
                    () -> enabler.initialize(read(enabler.getName()))
            );
            descriptors.put(enabler.getName(), new EnablerDescriptor(enabler, initializer));
        }
    }

    @Override
    public Set<String> names() {
        return descriptors.keySet();
    }

    @Override
    public Boolean read(final String name) {
        return descriptors.get(name).getReleaseValue();
    }

    private static class EnablerDescriptor extends ConfigParam<Boolean> {
        final OneTimeInitializer initializer;

        EnablerDescriptor(final ConfigParam<Boolean> param, final OneTimeInitializer initializer) {
            super(param.getName(), param.getInitialValue(), param.getReleaseValue());
            this.initializer = initializer;
        }
    }

}
