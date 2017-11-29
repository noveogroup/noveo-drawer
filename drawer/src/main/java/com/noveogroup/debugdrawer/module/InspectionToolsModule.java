package com.noveogroup.debugdrawer.module;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.noveogroup.debugdrawer.R;
import com.noveogroup.debugdrawer.api.SupportDebugModule;

public class InspectionToolsModule extends SupportDebugModule {

    private Switch stethoSwitch;
    private Switch leakCanarySwitch;

    @NonNull
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup parent) {
        final Context context = inflater.getContext();
        final View view = inflater.inflate(R.layout.dd_debug_drawer_module_tools, parent, false);
        view.setClickable(false);
        view.setEnabled(false);

        stethoSwitch = view.findViewById(R.id.dd_debug_tools_switch_stetho);
        stethoSwitch.setChecked(getSettings().isStethoEnabled());
        stethoSwitch.setOnCheckedChangeListener((button, enabled) -> {
            if (getSettings().isStethoEnabled() != enabled) {
                showConfirmationDialog(
                        context,
                        context.getString(R.string.dd_alert_restart_property_message, enabled ? "enable" : "disable", "Stetho"),
                        () -> getSettings().enableStetho(enabled).subscribe(this::rebirth),
                        () -> stethoSwitch.setChecked(!enabled));
            }
        });

        leakCanarySwitch = view.findViewById(R.id.dd_debug_tools_switch_leak_canary);
        leakCanarySwitch.setChecked(getSettings().isLeakCanaryEnabled());
        leakCanarySwitch.setOnCheckedChangeListener((button, enabled) -> {
            if (getSettings().isLeakCanaryEnabled() != enabled) {
                showConfirmationDialog(
                        context,
                        context.getString(R.string.dd_alert_restart_property_message, enabled ? "enable" : "disable", "Leak Canary"),
                        () -> getSettings().enableLeakCanary(enabled).subscribe(this::rebirth),
                        () -> leakCanarySwitch.setChecked(!enabled));
            }
        });

        refresh();
        return view;
    }

    @Override
    public void onOpened() {
        refresh();
    }

    private void refresh() {
        stethoSwitch.setChecked(getSettings().isStethoEnabled());
        leakCanarySwitch.setChecked(getSettings().isLeakCanaryEnabled());
    }

}
