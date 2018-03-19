package com.noveogroup.debugdrawer;


public abstract class ConfigParamBuilder<V, T extends ConfigParamBuilder<V, T>> {
    final String name;
    V initialValue;
    V releaseValue;

    @SuppressWarnings("WeakerAccess")
    public ConfigParamBuilder(final String name) {
        this.name = name;
    }

    public T setInitialValue(V initialValue) {
        this.initialValue = initialValue;
        return getThis();
    }

    public T setReleaseValue(V releaseValue) {
        this.releaseValue = releaseValue;
        return getThis();
    }

    abstract V resolveIfNull();

    abstract T getThis();

    public Object build() {
        if (initialValue == null && releaseValue == null) {
            final V value = resolveIfNull();
            initialValue = value;
            releaseValue = value;
        } else if (initialValue == null) {
            initialValue = releaseValue;
        } else {
            releaseValue = initialValue;
        }
        return null;
    }

}
