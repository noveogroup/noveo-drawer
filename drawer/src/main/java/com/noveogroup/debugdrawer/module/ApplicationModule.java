package com.noveogroup.debugdrawer.module;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noveogroup.debugdrawer.R;
import com.noveogroup.debugdrawer.Utils;
import com.noveogroup.debugdrawer.api.SupportDebugModule;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ApplicationModule extends SupportDebugModule {

    private final String buildSource;
    private final String buildDate;
    private final String buildFlavor;
    private final String buildType;
    private TextView gitLabel;
    private TextView dateLabel;
    private TextView flavorLabel;
    private TextView typeLabel;
    private String undefined;

    public ApplicationModule(final Class buildConfigClass) {
        super();
        final List<Field> fields = Arrays.asList(buildConfigClass.getDeclaredFields());
        this.buildSource = findByName(fields, "BUILD_SOURCE");
        this.buildDate = findByName(fields, "BUILD_DATE");
        this.buildFlavor = findByName(fields, "FLAVOR");
        this.buildType = findByName(fields, "BUILD_TYPE");
    }
    public ApplicationModule(final String buildSource, final String buildDate, final String buildFlavor, final String buildType) {
        super();
        this.buildSource = buildSource;
        this.buildDate = buildDate;
        this.buildFlavor = buildFlavor;
        this.buildType = buildType;
    }

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

    @NonNull
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup parent) {
        final View view = inflater.inflate(R.layout.dd_debug_drawer_module_application, parent, false);
        view.setClickable(false);
        view.setEnabled(false);

        gitLabel = view.findViewById(R.id.dd_debug_application_build_source);
        dateLabel = view.findViewById(R.id.dd_debug_application_build_date);
        flavorLabel = view.findViewById(R.id.dd_debug_application_build_flavor);
        typeLabel = view.findViewById(R.id.dd_debug_application_build_type);

        undefined = inflater.getContext().getString(R.string.dd_undefined);

        refresh();

        return view;
    }

    @Override
    public void onOpened() {
        refresh();
    }

    private void refresh() {
        gitLabel.setText(Utils.firstNonNull(buildSource, undefined));
        dateLabel.setText(Utils.firstNonNull(buildDate, undefined));
        flavorLabel.setText(Utils.firstNonNull(buildFlavor, undefined));
        typeLabel.setText(Utils.firstNonNull(buildType, undefined));
    }
}

