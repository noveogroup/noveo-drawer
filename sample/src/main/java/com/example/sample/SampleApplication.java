package com.example.sample;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.noveogroup.debugdrawer.api.NoveoDebugDrawer;
import com.noveogroup.debugdrawer.api.NoveoDrawerConfig;
import com.squareup.leakcanary.LeakCanary;

public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NoveoDebugDrawer.init(NoveoDrawerConfig.builder(this)
                .setLeakCanaryInitializer(clazz -> LeakCanary.install(this))
                .setStethoInitializer(clazz -> Stetho.initializeWithDefaults(this))
                .build());
    }
}
