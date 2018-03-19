package com.noveogroup.debugdrawer;


@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public abstract class ConfigParamBuilder<V, T extends ConfigParamBuilder<V, T>> {
    final String name;
    V initialValue;
    V releaseValue;

    @SuppressWarnings("WeakerAccess")
    public ConfigParamBuilder(final String name) {
        this.name = name;
    }

    /**
     * @param initialValue Value you want in DEBUG build after FIRST installation
     * @return your builder to continue.
     */
    public T initialValue(V initialValue) {
        this.initialValue = initialValue;
        return getThis();
    }

    /**
     * @param initialValue Value you want in RELEASE build at any circumstances
     * @return your builder to continue.
     */
    public T releaseValue(V releaseValue) {
        this.releaseValue = releaseValue;
        return getThis();
    }

    abstract protected V resolveIfNull();

    abstract protected T getThis();

    public Object build() {
        initialValue = initialValue == null ? resolveIfNull() : initialValue;
        releaseValue = releaseValue == null ? resolveIfNull() : releaseValue;
        return null;
    }

}
