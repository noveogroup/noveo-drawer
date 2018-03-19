package com.noveogroup.debugdrawer;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public final class Selector extends ConfigParam<String> {

    final List<String> values;

    Selector(
            final String name,
            final String initialValue,
            final String defaultValue,
            final List<String> values
    ) {
        super(name, initialValue, defaultValue);
        this.values = values;
    }

    public static SelectorBuilder builder(final String name) {
        return new SelectorBuilder(name);
    }

    public List<String> getValues() {
        return values;
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

        return values != null ? values.equals(selector.values) : selector.values == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = HASHCODE_CONSTANT * result + (values != null ? values.hashCode() : 0);
        return result;
    }

    public static class SelectorBuilder extends ConfigParamBuilder<String, SelectorBuilder> {

        private final List<String> values = new ArrayList<>();

        public SelectorBuilder(String name) {
            super(name);
        }

        private static String checkValue(final String checkValue, final List<String> values) {
            for (String value : values) {
                if (TextUtils.equals(checkValue, value)) {
                    return checkValue;
                }
            }
            throw new IllegalArgumentException("value not in list");
        }

        public SelectorBuilder addValue(String value) {
            values.add(value);
            return getThis();
        }

        public SelectorBuilder addValues(String... values) {
            this.values.addAll(Arrays.asList(values));
            return getThis();
        }

        @Override
        public SelectorBuilder getThis() {
            return this;
        }

        @Override
        String resolveIfNull() {
            return values.get(0);
        }

        @Override
        public Selector build() {
            super.build();
            return new Selector(
                    name,
                    checkValue(initialValue, values),
                    checkValue(releaseValue, values),
                    new ArrayList<>(new LinkedHashSet<>(values)));
        }
    }
}
