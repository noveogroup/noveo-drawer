package com.noveogroup.debugdrawer;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noveogroup.debugdrawer.buildinfo.R;

import java.lang.reflect.Field;
import java.util.List;

public class GradleModule extends SupportDebugModule {

    private final BuildConfigDto helper;

    private TextView gitLabel;
    private TextView dateLabel;
    private TextView flavorLabel;
    private TextView typeLabel;

    private String undefined;

    public GradleModule(final Class buildConfigClass) {
        this(new BuildConfigDto(buildConfigClass));
    }

    public GradleModule(final String buildSource, final String buildDate, final String buildFlavor, final String buildType) {
        this(new BuildConfigDto(buildSource, buildDate, buildFlavor, buildType));
    }

    @SuppressWarnings("PMD.AvoidThrowingNullPointerException")
    private GradleModule(final BuildConfigDto buildConfigDto) {
        super();
        helper = buildConfigDto;
        if (helper == null) {
            throw new NullPointerException("buildConfig info not found");
        }
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

    @Override
    public String getDebugInfo() {
        return "Gradle Build Info = {\n" +
                "  \"git\": " + gitLabel.getText() + ",\n" +
                "  \"date\": " + dateLabel.getText() + ",\n" +
                "  \"flavor\": " + flavorLabel.getText() + ",\n" +
                "  \"type\": " + typeLabel.getText() +
                "\n}";
    }

    private void refresh() {
        gitLabel.setText(Utils.firstNonNull(helper.getBuildSource(), undefined));
        dateLabel.setText(Utils.firstNonNull(helper.getBuildDate(), undefined));
        flavorLabel.setText(Utils.firstNonNull(helper.getBuildFlavor(), undefined));
        typeLabel.setText(Utils.firstNonNull(helper.getBuildType(), undefined));
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

