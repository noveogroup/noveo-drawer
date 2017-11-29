package com.noveogroup.debugdrawer.api.provider;

import android.support.annotation.Nullable;

import com.noveogroup.debugdrawer.data.model.Endpoint;
import com.noveogroup.debugdrawer.domain.DeveloperSettingsManager;

public final class EndpointProvider extends BaseProvider {

    public EndpointProvider(final DeveloperSettingsManager settings) {
        super(settings);
    }

    @Nullable
    public Endpoint getEndpoint() {
        return getSettings().readEndpoint();
    }

}
