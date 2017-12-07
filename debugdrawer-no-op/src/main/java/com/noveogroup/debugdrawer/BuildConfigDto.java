package com.noveogroup.debugdrawer;

/**
 * Created by avaytsekhovskiy on 30/11/2017.
 */

@SuppressWarnings("WeakerAccess")
public final class BuildConfigDto {

    BuildConfigDto(final Class buildConfigClass) {
    }

    BuildConfigDto(final String buildSource, final String buildDate, final String buildFlavor, final String buildType) {
        //do nothing
    }

    public String getBuildSource() {
        return "";
    }

    public String getBuildDate() {
        return "";
    }

    public String getBuildFlavor() {
        return "";
    }

    public String getBuildType() {
        return "";
    }

}
