package com.noveogroup.debugdrawer;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class Selector extends ConfigParam<String> {

    private final List<String> value;

    public Selector(final String name, final String... values) {
        super(name, values[0]);
        this.value = new ArrayList<>(new LinkedHashSet<>(Arrays.asList(values)));
    }

    public List<String> getValues() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final Selector selector = (Selector) o;

        return value != null ? value.equals(selector.value) : selector.value == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = HASHCODE_CONSTANT * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
