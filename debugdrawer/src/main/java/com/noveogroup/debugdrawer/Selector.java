package com.noveogroup.debugdrawer;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class Selector {
    private static final int HASHCODE_CONSTANT = 31;

    private final String name;
    private final List<String> value;

    public Selector(final String name, final String... values) {
        this.name = name;
        this.value = new ArrayList<>(new LinkedHashSet<>(Arrays.asList(values)));
    }

    public String getName() {
        return name;
    }

    public List<String> getValues() {
        return value;
    }

    @SuppressWarnings({"ControlFlowStatementWithoutBraces", "SimplifiableIfStatement"})
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Selector selector = (Selector) o;

        if (name != null ? !name.equals(selector.name) : selector.name != null) {
            return false;
        }
        return value != null ? value.equals(selector.value) : selector.value == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = HASHCODE_CONSTANT * result + (value != null ? value.hashCode() : 0);
        return result;
    }

}
