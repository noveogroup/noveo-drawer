package com.noveogroup.debugdrawer;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


final class DrawerSelectorSettings implements SelectorProvider {

    private final Map<String, SelectorDescriptor> descriptors;

    DrawerSelectorSettings(final List<Selector> selectors) {
        this.descriptors = new LinkedHashMap<>();

        for (final Selector selector : selectors) {
            final String name = selector.getName();
            final OneTimeInitializer initializer = new OneTimeInitializer(() -> {
            });
            descriptors.put(name, new SelectorDescriptor(selector, initializer));
            initializer.initializeIfRequired();
        }
    }

    @Override
    public Set<String> names() {
        return descriptors.keySet();
    }

    @Override
    public String read(final String name) {
        return descriptors.get(name).getReleaseValue();
    }

    @Override
    public List<String> values(final String name) {
        return descriptors.get(name).values;
    }

    private static class SelectorDescriptor extends ConfigParam<String> {

        final List<String> values;
        final OneTimeInitializer initializer;

        SelectorDescriptor(Selector selector, OneTimeInitializer initializer) {
            super(selector.getName(), selector.getInitialValue(), selector.getReleaseValue());
            this.values = selector.getValues();
            this.initializer = initializer;
        }
    }

}
