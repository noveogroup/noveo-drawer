package com.example.sample;

import android.app.Activity;

import com.noveogroup.debugdrawer.data.model.Endpoint;
import com.noveogroup.debugdrawer.module.ApplicationModule;
import com.noveogroup.debugdrawer.module.EndpointsModule;
import com.noveogroup.debugdrawer.module.InspectionToolsModule;

import java.util.Arrays;

import io.palaima.debugdrawer.DebugDrawer;

public final class DebugDrawerHelper {

    private DebugDrawerHelper() {
    }

    public static DebugDrawer makeDrawer(final Activity activity) {
        return new DebugDrawer.Builder(activity)
                .modules(new ApplicationModule(BuildConfig.class),
                        new EndpointsModule(Arrays.asList(
                                new Endpoint("stage", "http://staging.noveogroup.com"),
                                new Endpoint("prod", "http://production.noveogroup.com")
                        )),
//                        new BuildModule(activity),
//                        new DeviceModule(activity),
                        new InspectionToolsModule()
//                        new SettingsModule(activity)
                )
                .build();
    }


}
