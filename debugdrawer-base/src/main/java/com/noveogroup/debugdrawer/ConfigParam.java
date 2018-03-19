package com.noveogroup.debugdrawer;

@SuppressWarnings("SimplifiableIfStatement")
class ConfigParam<T> {
    static final int HASHCODE_CONSTANT = 31;

    private final String name;
    private final T initialValue;
    private final T releaseValue;


    ConfigParam(final String name, final T initialValue, final T releaseValue) {
        this.name = name;
        this.initialValue = initialValue;
        this.releaseValue = releaseValue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ConfigParam<?> that = (ConfigParam<?>) o;

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        return releaseValue != null ? releaseValue.equals(that.releaseValue) : that.releaseValue == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = HASHCODE_CONSTANT * result + (releaseValue != null ? releaseValue.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    T getReleaseValue() {
        return releaseValue;
    }

    T getInitialValue() {
        return initialValue;
    }

}
