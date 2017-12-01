package com.noveogroup.debugdrawer.domain;

import android.content.Context;
import android.os.Handler;

import com.jakewharton.processphoenix.ProcessPhoenix;

@SuppressWarnings("FieldCanBeLocal")
public class RebirthExecutor {

    private final Handler handler;
    private final Runnable runnable;

    public RebirthExecutor(final Context context) {
        this.handler = new Handler(context.getMainLooper());
        this.runnable = () -> ProcessPhoenix.triggerRebirth(context);
    }

    public void rebirth() {
        handler.removeCallbacks(runnable);
        handler.post(runnable);
    }
}
