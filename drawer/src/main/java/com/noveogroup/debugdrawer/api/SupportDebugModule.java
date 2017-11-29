package com.noveogroup.debugdrawer.api;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.noveogroup.debugdrawer.R;
import com.noveogroup.debugdrawer.domain.DeveloperSettingsManager;

import io.palaima.debugdrawer.base.DebugModule;

@SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
public abstract class SupportDebugModule implements DebugModule {

    protected void showConfirmationDialog(final Context context, final String message, final Runnable restart, final Runnable revert) {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.dd_alert_restart_title)
                .setMessage(TextUtils.isEmpty(message) ? context.getString(R.string.dd_alert_restart_message) : message)
                .setNegativeButton(R.string.dd_alert_restart_cancel, (dialog, i) -> {
                    revert.run();
                    dialog.dismiss();
                })
                .setPositiveButton(R.string.dd_alert_restart_ok, (dialog, i) -> {
                    restart.run();
                    rebirth();        //stops execution
                    dialog.dismiss(); //unreachable
                })
                .setCancelable(false)
                .show();
    }

    @Override
    public void onOpened() {
        //do nothing
    }

    @Override
    public void onClosed() {
        //do nothing
    }

    @Override
    public void onResume() {
        //do nothing
    }

    @Override
    public void onPause() {
        //do nothing
    }

    @Override
    public void onStart() {
        //do nothing
    }

    @Override
    public void onStop() {
        //do nothing
    }

    @SuppressWarnings("MethodMayBeStatic")
    protected DeveloperSettingsManager getSettings() {
        return NoveoDebugDrawer.config.getSettings();
    }

    @SuppressWarnings("MethodMayBeStatic")
    protected void rebirth() {
        NoveoDebugDrawer.config.getRebirthExecutor().rebirth();
    }

}
