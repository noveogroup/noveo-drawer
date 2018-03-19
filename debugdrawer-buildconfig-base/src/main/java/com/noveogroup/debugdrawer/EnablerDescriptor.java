package com.noveogroup.debugdrawer;


class EnablerDescriptor extends ConfigParam<Boolean> {
    private final OneTimeInitializer initializer;

    EnablerDescriptor(final ConfigParam<Boolean> param, final OneTimeInitializer initializer) {
        super(param.getName(), param.getInitialValue(), param.getReleaseValue());
        this.initializer = initializer;
    }

    OneTimeInitializer getInitializer() {
        return initializer;
    }
}
