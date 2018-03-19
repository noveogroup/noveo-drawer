package com.noveogroup.debugdrawer;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Selector describes an spinner UI item in DebugDrawer.
 * <p>
 * Spinner is a set of properties:
 * <p>
 * name - key in shared preferences
 * values - string spinner values
 * initialValue - value that spinner will use after first install
 * releaseValue - value that spinner will always use in no-op / release build configuration
 * <p>
 * Note: by default initialValue & releaseValue is the very first value ( == values.get(0) )
 * So you will get an IndexOutOfBoundException if there are no values provided before .build()
 */
@SuppressWarnings({"WeakerAccess", "PMD.UselessOverridingMethod"})
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
            throw new IllegalArgumentException("initial or release value not in list");
        }

        /**
         * Note: by default the very first selector item will be used (values.get(0))
         *
         * @param value you want in DEBUG build after FIRST installation
         * @return your builder to continue.
         */
        @Override
        public SelectorBuilder initialValue(String initialValue) {
            return super.initialValue(initialValue);
        }

        /**
         * Note: by default the very first selector item will be used (values.get(0))
         *
         * @param value you want in RELEASE build at any circumstances
         * @return your builder to continue.
         */
        @Override
        public SelectorBuilder releaseValue(String releaseValue) {
            return super.releaseValue(releaseValue);
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
        protected SelectorBuilder getThis() {
            return this;
        }

        @Override
        protected String resolveIfNull() {
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
