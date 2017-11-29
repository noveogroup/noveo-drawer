package com.noveogroup.debugdrawer.api.provider;

import com.noveogroup.debugdrawer.domain.DeveloperSettingsManager;


class BaseProvider {

    private final DeveloperSettingsManager settings;

    BaseProvider(final DeveloperSettingsManager settings) {
        this.settings = settings;
    }

    DeveloperSettingsManager getSettings() {
        return settings;
    }
}
