package com.noveogroup.debugdrawer.api;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;

import com.noveogroup.debugdrawer.ColorUtils;
import com.noveogroup.debugdrawer.R;
import com.noveogroup.debugdrawer.Utils;
import com.noveogroup.debugdrawer.domain.DeveloperSettingsManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.palaima.debugdrawer.base.DebugModule;

@SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
public abstract class SupportDebugModule implements DebugModule {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected Logger getLogger() {
        return logger;
    }

    @SuppressWarnings("deprecation")
    protected CharSequence html(final String html) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html);
        } else {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        }
    }

    protected void showConfirmationDialog(final Context context, final CharSequence message, final Runnable restart, final Runnable revert) {
        Utils.log(logger, "confirmation --> ask\n======\n{}\n======\n", message);
        final Drawable icon = ContextCompat.getDrawable(context, android.R.drawable.ic_dialog_alert).mutate();
        ColorUtils.colorize(icon, ColorUtils.getThemeColor(context, R.attr.colorAccent));
        new AlertDialog.Builder(context)
                .setIcon(icon)
                .setTitle(R.string.dd_alert_restart_title)
                .setMessage(TextUtils.isEmpty(message) ? context.getString(R.string.dd_alert_restart_message) : message)
                .setNegativeButton(R.string.dd_alert_restart_cancel, (dialog, i) -> {
                    Utils.log(logger, "confirmation <-- canceled");
                    revert.run();
                    dialog.dismiss();
                })
                .setPositiveButton(R.string.dd_alert_restart_ok, (dialog, i) -> {
                    Utils.log(logger, "confirmation <-- confirmed");
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
        notifyStarted();
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

    public abstract String getDebugInfo();

    protected void notifyStarted() {
        NoveoDebugDrawer.registerModule(this);
    }

}
