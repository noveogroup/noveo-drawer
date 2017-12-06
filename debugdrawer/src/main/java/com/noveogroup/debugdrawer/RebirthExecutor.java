package com.noveogroup.debugdrawer;

import android.content.Context;
import android.os.Handler;

import com.jakewharton.processphoenix.ProcessPhoenix;

@SuppressWarnings("FieldCanBeLocal")
final class RebirthExecutor {

    private final Handler handler;
    private final Runnable runnable;

    RebirthExecutor(final Context context) {
        this.handler = new Handler(context.getMainLooper());
        this.runnable = () -> ProcessPhoenix.triggerRebirth(context);
    }

    void rebirth() {
        handler.removeCallbacks(runnable);
        handler.post(runnable);
    }
}
