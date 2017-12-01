package com.noveogroup.debugdrawer.data.model;


import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class SelectorDto {
    private static final int HASHCODE_CONSTANT = 31;

    private final String name;
    private final Set<String> value;

    public SelectorDto(final String name, final String... values) {
        this.name = name;
        this.value = new LinkedHashSet<>(Arrays.asList(values));
    }

    public String getName() {
        return name;
    }

    public Set<String> getValues() {
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

        final SelectorDto selectorDto = (SelectorDto) o;

        if (name != null ? !name.equals(selectorDto.name) : selectorDto.name != null) {
            return false;
        }
        return value != null ? value.equals(selectorDto.value) : selectorDto.value == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = HASHCODE_CONSTANT * result + (value != null ? value.hashCode() : 0);
        return result;
    }

}
