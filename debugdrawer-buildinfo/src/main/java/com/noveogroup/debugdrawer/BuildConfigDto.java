package com.noveogroup.debugdrawer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public final class BuildConfigDto {

    private final String buildSource;
    private final String buildDate;
    private final String buildFlavor;
    private final String buildType;

    BuildConfigDto(final Class buildConfigClass) {
        final List<Field> fields = Arrays.asList(buildConfigClass.getDeclaredFields());
        buildSource = findByName(fields, "BUILD_SOURCE");
        buildDate = findByName(fields, "BUILD_DATE");
        buildFlavor = findByName(fields, "FLAVOR");
        buildType = findByName(fields, "BUILD_TYPE");
    }

    BuildConfigDto(final String buildSource, final String buildDate, final String buildFlavor, final String buildType) {
        this.buildSource = buildSource;
        this.buildDate = buildDate;
        this.buildFlavor = buildFlavor;
        this.buildType = buildType;
    }

    public String getBuildSource() {
        return buildSource;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public String getBuildFlavor() {
        return buildFlavor;
    }

    public String getBuildType() {
        return buildType;
    }

    @SuppressWarnings("PMD")
    private String findByName(final List<Field> fields, final String name) {
        try {
            for (final Field field : fields) {
                if (field.getName().equals(name)) {
                    return (String) field.get(null);
                }
            }
        } catch (final IllegalAccessException error) {
            throw new IllegalArgumentException("Can't get field " + name, error);
        }
        throw new IllegalArgumentException("Can't find field " + name);
    }


}
