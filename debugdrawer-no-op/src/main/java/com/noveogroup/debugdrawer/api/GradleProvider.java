package com.noveogroup.debugdrawer.api;

import com.noveogroup.debugdrawer.BuildConfigDto;

/**
 * Created by avaytsekhovskiy on 30/11/2017.
 */

public interface GradleProvider {
    BuildConfigDto getBuildConfig();
}
