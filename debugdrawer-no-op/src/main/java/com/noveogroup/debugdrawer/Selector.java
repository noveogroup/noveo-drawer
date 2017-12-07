package com.noveogroup.debugdrawer;

import java.util.Arrays;
import java.util.List;

public class Selector {

    private final String name;
    private final List<String> values;

    public Selector(final String name, final String... values) {
        this.name = name;
        this.values = Arrays.asList(values);
    }

    public String getName() {
        return name;
    }

    public List<String> getValues() {
        return values;
    }

}
