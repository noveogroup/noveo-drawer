package com.noveogroup.debugdrawer;


public abstract class Enabler extends ConfigParam<Boolean> {

    @SuppressWarnings("WeakerAccess")
    Enabler(final String name, final Boolean initialValue, final Boolean releaseValue) {
        super(name, initialValue, releaseValue);
    }

    public static EnablerBuilder builder(final String name, final EnablerAction action) {
        return new EnablerBuilder(name, action);
    }

    public abstract void initialize(boolean enabled);

    public interface EnablerAction {
        void init(boolean enabled);
    }

    public static class EnablerBuilder extends ConfigParamBuilder<Boolean, EnablerBuilder> {

        final EnablerAction action;

        @SuppressWarnings("WeakerAccess")
        public EnablerBuilder(String name, EnablerAction action) {
            super(name);
            this.action = action;
        }

        @Override
        public EnablerBuilder getThis() {
            return this;
        }

        @Override
        public Boolean resolveIfNull() {
            return false;
        }

        @Override
        public Enabler build() {
            super.build();
            return new Enabler(name, initialValue, releaseValue) {
                @Override
                public void initialize(final boolean enabled) {
                    if (action != null) {
                        action.init(enabled);
                    }
                }
            };
        }

    }
}
