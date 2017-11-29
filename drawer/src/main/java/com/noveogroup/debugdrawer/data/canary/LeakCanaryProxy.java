package com.noveogroup.debugdrawer.data.canary;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class LeakCanaryProxy {

    private RefWatcher watcher;

    public void init(final Application application) {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        watcher = LeakCanary.install(application);
    }

    public void watch(final Object object) {
        if (watcher != null) {
            watcher.watch(object);
        }
    }
}
