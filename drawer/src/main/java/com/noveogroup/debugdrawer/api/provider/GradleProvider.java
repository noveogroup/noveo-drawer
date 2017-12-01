package com.noveogroup.debugdrawer.api.provider;

import com.noveogroup.debugdrawer.data.model.BuildConfigDto;

/**
 * Created by avaytsekhovskiy on 30/11/2017.
 */

public interface GradleProvider {
    BuildConfigDto getBuildConfig();
}
