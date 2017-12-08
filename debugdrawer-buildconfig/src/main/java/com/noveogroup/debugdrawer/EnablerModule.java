package com.noveogroup.debugdrawer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.noveogroup.debugdrawer.buildconfig.R;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class EnablerModule extends ConfigDebugModule {

    private final Map<String, EnablerViewHolder> holders;
    private final EnablerProvider provider;
    private final SettingsWriter<Boolean> writer;

    @SuppressWarnings("PMD.AvoidThrowingNullPointerException")
    public EnablerModule(final DebugBuildConfiguration configuration) {
        super(configuration);
        this.holders = new LinkedHashMap<>();

        provider = configuration.getEnablerProvider();
        writer = configuration.getEnablerWriter();

        if (Utils.isEmpty(provider.names())) {
            throw new NullPointerException("Enablers not found");
        }

        writer.applyInitial();
    }

    public static void init(final DebugBuildConfiguration configuration, final Enabler... enablers) {
        configuration.initEnablers(Arrays.asList(enablers));
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup parent) {
        final Context context = inflater.getContext();
        final GridLayout grid = (GridLayout) inflater.inflate(R.layout.dd_debug_drawer_module_enabler, parent, false);
        grid.setClickable(false);
        grid.setEnabled(false);

        for (final String name : provider.names()) {
            final EnablerViewHolder holder = new EnablerViewHolder((ViewGroup) inflater.inflate(R.layout.dd_item_enabler_include, grid, true));
            holder.nameView.setText(name);
            holder.switchView.setChecked(provider.read(name));
            holder.switchView.setOnCheckedChangeListener((it, newValue) -> {
                final Boolean previousValue = provider.read(name);
                if (previousValue != newValue) {
                    showConfirmationDialog(
                            context,
                            html(context.getString(R.string.dd_alert_restart_enabler_message, newValue ? "enable" : "disable", name)),
                            () -> {
                                writer.change(name, newValue);
                                rebirth();
                            },
                            () -> holder.switchView.setChecked(!newValue));
                }
            });
            holders.put(name, holder);
        }

        refresh();
        return grid;
    }

    @Override
    public void onOpened() {
        refresh();
    }

    @Override
    public String getDebugInfo() {
        final StringBuilder stringBuilder = new StringBuilder(32);
        stringBuilder.append("Inspections = {\n");
        for (final String name : holders.keySet()) {
            stringBuilder.append("  \"").append(name).append("\": ")
                    .append(provider.read(name) ? "[+]" : "[-]")
                    .append(",\n");
        }
        stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "\n}");
        return stringBuilder.toString();
    }

    private void refresh() {
        for (final String name : holders.keySet()) {
            holders.get(name).switchView.setChecked(provider.read(name));
        }
    }

    static class EnablerViewHolder {
        TextView nameView;
        Switch switchView;

        EnablerViewHolder(final ViewGroup view) {
            nameView = (TextView) view.getChildAt(view.getChildCount() - 2);
            switchView = (Switch) view.getChildAt(view.getChildCount() - 1);
        }
    }

}
