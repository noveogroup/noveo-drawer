package com.noveogroup.debugdrawer.module;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.noveogroup.debugdrawer.R;
import com.noveogroup.debugdrawer.api.SupportDebugModule;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class EnablerModule extends SupportDebugModule {

    private final Map<String, EnablerViewHolder> holders = new LinkedHashMap<>();

    @SuppressWarnings("PMD.AvoidThrowingNullPointerException")
    public EnablerModule() {
        super();
        final Set<String> inspections = getSettings().getSelectors();
        if (inspections.isEmpty()) {
            throw new NullPointerException("Enablers not found. " +
                    "Please add any with NoveoDebugDrawer.init(NoveoDebugDrawerConfig.builder().addEnablers())");
        }

        getSettings().applyEnablers();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup parent) {
        final Context context = inflater.getContext();
        final GridLayout grid = (GridLayout) inflater.inflate(R.layout.dd_debug_drawer_module_enabler, parent, false);
        grid.setClickable(false);
        grid.setEnabled(false);

        for (final String name : getSettings().getEnablers()) {
            final EnablerViewHolder holder = new EnablerViewHolder((ViewGroup) inflater.inflate(R.layout.dd_item_enabler_include, grid, true));
            holder.nameView.setText(name);
            holder.switchView.setChecked(getSettings().isEnablerEnabled(name));
            holder.switchView.setOnCheckedChangeListener((button, enabled) -> {
                if (getSettings().isEnablerEnabled(name) != enabled) {
                    showConfirmationDialog(
                            context,
                            html(context.getString(R.string.dd_alert_restart_enabler_message, enabled ? "enable" : "disable", name)),
                            () -> getSettings().enableEnabler(name, enabled).subscribe(this::rebirth),
                            () -> holder.switchView.setChecked(!enabled));
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
                    .append(getSettings().isEnablerEnabled(name) ? "enabled" : "disabled")
                    .append(",\n");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("\n}");
        return stringBuilder.toString();
    }

    private void refresh() {
        for (final String name : holders.keySet()) {
            holders.get(name).switchView.setChecked(getSettings().isEnablerEnabled(name));
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
