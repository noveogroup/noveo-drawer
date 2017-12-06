package com.noveogroup.debugdrawer;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GradleModule extends SupportDebugModule {

    private final BuildConfigDto helper;
    private TextView gitLabel;
    private TextView dateLabel;
    private TextView flavorLabel;
    private TextView typeLabel;
    private String undefined;

    @SuppressWarnings("PMD.AvoidThrowingNullPointerException")
    public GradleModule() {
        super();
        helper = getSettings().getBuildConfig();
        if (helper == null) {
            throw new NullPointerException("BuildConfigDto not found. " +
                    "Please add any with\n" +
                    "NoveoDebugDrawer.init(NoveoDebugDrawerConfig.builder().setBuildConfigClass())\n" +
                    "or\n" +
                    "NoveoDebugDrawer.init(NoveoDebugDrawerConfig.builder().setBuildConfigParams())");
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
}

