package com.noveogroup.debugdrawer;


/**
 * Enabler describes an switch UI item in DebugDrawer.
 * <p>
 * Enabler is a set of properties:
 * <p>
 * name - key in shared preferences
 * initialValue - value that checker will use after first install
 * releaseValue - value that checker will always use in no-op / release build configuration
 * action - callback that will be invoked on initialization (during Application.onCreate())
 * <p>
 * Note: by default initialValue & releaseValue is "false"
 */
@SuppressWarnings("PMD.UselessOverridingMethod")
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

        /**
         * Note: false by default.
         *
         * @param value you want in DEBUG build after FIRST installation
         * @return your builder to continue.
         */
        @Override
        public EnablerBuilder initialValue(final Boolean initialValue) {
            return super.initialValue(initialValue);
        }

        /**
         * Note: false by default.
         *
         * @param value you want in RELEASE build at any circumstances
         * @return your builder to continue.
         */
        @Override
        public EnablerBuilder releaseValue(final Boolean releaseValue) {
            return super.releaseValue(releaseValue);
        }

        @Override
        protected EnablerBuilder getThis() {
            return this;
        }

        @Override
        protected Boolean resolveIfNull() {
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
