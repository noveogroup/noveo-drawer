package com.noveogroup.debugdrawer;

import java.util.List;


class SelectorDescriptor extends ConfigParam<String> {

    private final List<String> values;
    private final OneTimeInitializer initializer;

    SelectorDescriptor(Selector selector, OneTimeInitializer initializer) {
        super(selector.getName(), selector.getInitialValue(), selector.getReleaseValue());
        this.values = selector.getValues();
        this.initializer = initializer;
    }

    List<String> getValues() {
        return values;
    }

    OneTimeInitializer getInitializer() {
        return initializer;
    }
}
