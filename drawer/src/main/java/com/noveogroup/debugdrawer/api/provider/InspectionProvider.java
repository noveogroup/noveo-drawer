package com.noveogroup.debugdrawer.api.provider;

import com.noveogroup.debugdrawer.data.canary.LeakCanaryProxy;
import com.noveogroup.debugdrawer.domain.DeveloperSettingsManager;

public final class InspectionProvider extends BaseProvider {

    public InspectionProvider(final DeveloperSettingsManager settings) {
        super(settings);
    }

    public boolean isLeakCanaryEnabled() {
        return getSettings().isLeakCanaryEnabled();
    }

    public boolean isStethoEnabled() {
        return getSettings().isStethoEnabled();
    }

    public LeakCanaryProxy leakCanary() {
        return getSettings().getLeakCanaryProxy();
    }
}
